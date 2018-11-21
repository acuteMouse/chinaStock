package stock.base.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import stock.base.dao.StockCodeDao;
import stock.base.entity.StockCode;
import stock.base.entity.StockInfo;
import stock.base.service.StockService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author cgl
 * @date 2018/11/13 22:24
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class StockServiceImplTest {

    private final static String url = "http://quote.eastmoney.com/stocklist.html";
    @Autowired
    StockCodeDao stockCodeDao;

    @Autowired
    StockService stockService;

    @Test
    public void getAllCode() {
        Map<String, String> code = stockService.getAllCode(url);
        List<StockCode> codeList = new ArrayList<>(code.size());
        code.forEach((k, v) -> {
            StockCode stockCode = new StockCode(k, v);

            if (k.startsWith("6")) {
                stockCode.setCity("sh");
            }
            if (k.startsWith("0")) {
                stockCode.setCity("sz");
            }
            codeList.add(stockCode);
        });

        Assert.assertTrue(codeList.size() > 0);
        stockCodeDao.saveAll(codeList);
        log.info("save success:" + codeList.size());
    }

    @Test
    public void getPrice() {
        StockCode stockCode = new StockCode();
        stockCode.setCode("000971");
        stockCode.setCity("sz");
        StockInfo result = stockService.getPrice(stockCode);

        System.out.println(result);
    }

    @Test
    public void getCode() {
        String startTime = "2018-11-16 00:00:00";
        String endTime = "2018-11-17 00:00:00";
        List<StockInfo> stockInfos = stockService.getCodeBySwing(10.0, 10.0,startTime, endTime, 100000L);
        log.info(stockInfos.size() + "");
    }

    @Test
    public void  getWeek(){
        Calendar calendar=Calendar.getInstance();
        int week=calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println(week);
    }
}