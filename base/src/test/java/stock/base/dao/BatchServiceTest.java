package stock.base.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import stock.base.entity.StockInfo;
import stock.base.util.DateUtil;

import java.util.ArrayList;

/**
 * @author cgl
 * @date 2018/11/14 22:52
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BatchServiceTest {


    @Autowired
    Batch batchService;

    @Test
    public void batchSave() {
        ArrayList<StockInfo> stockInfos = new ArrayList<>(100);
        for (int i = 0; i < 10; i++) {
            StockInfo stockInfo = new StockInfo();
            stockInfo.setCode("111111");
            stockInfo.setMaxPrice(1231.2);
            stockInfo.setMinPrice(1231.2);
            stockInfo.setNowPrice(1231.2);
            stockInfo.setCount(1232L);
            stockInfo.setTotal(1231231.0);
            stockInfo.setOpenPrice(312.2);
            stockInfo.setQueryTime(DateUtil.getNowTimestamp());
            stockInfos.add(stockInfo);
        }
        batchService.batchInsert(stockInfos);
    }
}