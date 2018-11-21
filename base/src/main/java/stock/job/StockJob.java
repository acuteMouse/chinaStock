package stock.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import stock.base.dao.StockCodeDao;
import stock.base.entity.StockCode;
import stock.base.entity.StockInfo;
import stock.rabbit.produce.Produccer;
import stock.base.redis.service.RedisService;
import stock.base.service.StockService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static stock.base.constant.StockConstant.codeKey;

/**
 * @author cgl
 * @date 2018/11/14 23:44
 */
@Service
@Slf4j
public class StockJob {

    @Autowired
    StockCodeDao stockCodeDao;

    @Autowired
    RedisService redisService;

    @Autowired
    StockService stockService;

    @Autowired
    Produccer produccer;

    /**
     * 每2分钟
     */
    public void getStockInfo() {
        getInfo();
    }

    private void getInfo() {
        String codeStrs = redisService.get(codeKey);
        Iterable<StockCode> codeList = JSONArray.parseArray(codeStrs, StockCode.class);
        if (codeList == null) {
            codeList = stockCodeDao.findAll();
            redisService.save(codeKey, JSONArray.toJSONString(codeList));
        }
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (StockCode stockCode : codeList) {
            log.info(JSON.toJSONString(stockCode));
            executorService.submit(() -> {
                StockInfo info = stockService.getPrice(stockCode);
                produccer.send(info);
            });
        }
    }

    @Scheduled(cron = "* * 9,10,11 * * *")
    public void  test(){
        getInfo();
    }

    @Scheduled(cron = "* * 13,14,15,16 * * *")
    public void  test2(){
        getInfo();
    }
}
