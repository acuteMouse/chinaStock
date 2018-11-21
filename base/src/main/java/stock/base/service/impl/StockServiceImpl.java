package stock.base.service.impl;

import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stock.base.dao.StockCodeDao;
import stock.base.dao.StockInfoDao;
import stock.base.entity.StockCode;
import stock.base.entity.StockInfo;
import stock.base.redis.service.RedisService;
import stock.base.service.StockService;
import stock.base.util.DateUtil;
import stock.base.util.RegUtil;
import stock.base.util.RequestUtil;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static stock.base.constant.StockConstant.codeKey;

/**
 * @author Jimy
 */
@SuppressWarnings("ALL")
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class StockServiceImpl implements StockService {

    @SuppressWarnings("AlibabaConstantFieldShouldBeUpperCase")
    private final static String url = "http://quote.eastmoney.com/stocklist.html";
    private final static String dataUurl = "http://hq.sinajs.cn/list=";

    @Autowired
    StockInfoDao stockInfoDao;

    @Autowired
    RedisService redisService;

    @Autowired
    StockCodeDao stockCodeDao;

    @Override
    public Map<String, String> getAllCode(String url) {
        String result = RequestUtil.get(url);
        System.out.println(result);
        List<String> stockList = RegUtil.getMatchers(">(\\S*\\d{6})", result);
        Map<String, String> findResult = new HashMap<>();
        stockList.forEach(e -> {
            String x = e;
            if (x.contains("(")) {
                String[] strArr = x.split("\\(");
                String code = strArr[1].replace(")", "");
                String name = strArr[0].replace(">", "");
                if (code.startsWith("0") || code.startsWith("6") && String.valueOf(code).length() == 6) {
                    findResult.put(code, name);
                }
            }
        });
        return findResult;
    }

    @Override
    public StockInfo getPrice(StockCode stockCode) {
        Timestamp queryTime = new Timestamp(System.currentTimeMillis());
        String codePrice = RequestUtil.get(dataUurl + stockCode.getCity() + stockCode.getCode());
        String[] codeInfoArry = codePrice.split(",");
        Double openPrice = Double.valueOf(codeInfoArry[1]);
        Double lastOff = Double.valueOf(codeInfoArry[2]);
        Double nowPrice = Double.valueOf(codeInfoArry[3]);
        Double maxPrice = Double.valueOf(codeInfoArry[4]);
        Double minPrice = Double.valueOf(codeInfoArry[5]);
        Long count = Long.valueOf(codeInfoArry[8]);
        Double total = Double.valueOf(codeInfoArry[9]);
        try {
            queryTime = DateUtil.parseDateTime(codeInfoArry[30] + " " + codeInfoArry[31]);
        } catch (ParseException e) {
            log.info("查询时间转换错误");
        }
        StockInfo stockInfo = new StockInfo(stockCode.getName(), stockCode.getCode(), openPrice
                , nowPrice, maxPrice, minPrice, count, total, queryTime);
        stockInfo.setCreatTime(queryTime);
        stockInfo.setLastOff(lastOff);
        //计算振幅
        stockInfo.setSwing(calSwing(stockInfo));
        //计算涨幅
        stockInfo.setIncrease(calIncrase(stockInfo));
        return stockInfo;
    }


    @Override
    public List<StockInfo> getCodeBySwing(Double swing, Double increase, String startTime, String endTime, Long total) {
        long exeStartTime = System.currentTimeMillis();
        //1、所有的股票代码从redis中取
        String codeStrs = redisService.get(codeKey);
        Iterable<StockCode> codeList = JSONArray.parseArray(codeStrs, StockCode.class);
        if (codeList == null) {
            codeList = stockCodeDao.findAll();
            redisService.save(codeKey, JSONArray.toJSONString(codeList));
        }
        //查询指定的时间内所有股票数据
        List<StockInfo> stockInfos = stockInfoDao.getByQueryTime(startTime, endTime);
        Map<String, List<StockInfo>> stockListMap = stockInfos.stream().collect(Collectors.groupingBy(StockInfo::getCode));
        List<StockInfo> result = new ArrayList<>(stockListMap.size());
        for (String key : stockListMap.keySet()) {
            List<StockInfo> v = stockListMap.get(key);
            if (v == null || v.size() < 1) {
                continue;
            }
            //取每个股票的最后一条数据来判断是否符合下面的条件
            StockInfo stockInfo = v.parallelStream().max(Comparator.comparing(StockInfo::getQueryTime)).get();
            //振幅在查询范围,涨幅、成交额内
            if (belowSwing(swing, stockInfo) && belowIncrease(increase, stockInfo)) {
                //有金额限制必须要判断金额
                if (total != null) {
                    if (belowMoney(total, stockInfo)) {
                        result.add(stockInfo);
                    }
                } else {
                    result.add(stockInfo);
                }
            }
        }
        long exeEndTime = System.currentTimeMillis();
        log.info("执行耗时：" + (exeEndTime - exeStartTime) + "ms");
        return result;
    }


    /**
     * 判断股票价格是否在指定的振幅之内
     *
     * @param targetSwing
     * @param stockInfo
     * @return
     */
    static boolean belowSwing(Double targetSwing, StockInfo stockInfo) {
        if (stockInfo.getMaxPrice() <= 0 || stockInfo.getMinPrice() <= 0) {
            return false;
        }
        return stockInfo.getSwing() <= targetSwing;
    }

    /**
     * 计算振幅
     *
     * @param stockInfo
     * @return
     */
    static Double calSwing(StockInfo stockInfo) {
        if (stockInfo.getMaxPrice() <= 0 || stockInfo.getMinPrice() <= 0) {
            return 0.0;
        }
        return new BigDecimal(stockInfo.getMaxPrice()).subtract(new BigDecimal(stockInfo.getMinPrice()))
                .divide(new BigDecimal(stockInfo.getLastOff()), 4, BigDecimal.ROUND_UP)
                .multiply(new BigDecimal("100")).doubleValue();
    }

    /**
     * 涨幅之内
     *
     * @param targetIncrease
     * @param stockInfo
     * @return
     */
    static boolean belowIncrease(Double targetIncrease, StockInfo stockInfo) {
        if (stockInfo.getMaxPrice() <= 0 || stockInfo.getMinPrice() <= 0) {
            return false;
        }
        Double swing = stockInfo.getIncrease();
        return swing <= targetIncrease && swing > -targetIncrease;
    }

    /**
     * 计算涨幅
     *
     * @param stockInfo
     * @return
     */
    static Double calIncrase(StockInfo stockInfo) {
        if (stockInfo.getMaxPrice() <= 0 || stockInfo.getMinPrice() <= 0) {
            return 0.0;
        }
        Double increase = new BigDecimal(stockInfo.getNowPrice()).subtract(new BigDecimal(stockInfo.getLastOff()))
                .divide(new BigDecimal(stockInfo.getLastOff()), 4, BigDecimal.ROUND_UP)
                .multiply(new BigDecimal("100")).doubleValue();
        return increase;
    }

    /**
     * 成交额小于指定total值
     *
     * @param total
     * @param stockInfo
     * @return
     */
    static boolean belowMoney(Long total, StockInfo stockInfo) {
        return stockInfo.getTotal() < total;
    }

}
