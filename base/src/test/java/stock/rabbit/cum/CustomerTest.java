package stock.rabbit.cum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import stock.base.entity.StockInfo;
import stock.rabbit.produce.Produccer;

/**
 * @author cgl
 * @date 2018/11/14 21:51
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerTest {

    @Autowired
    Produccer produccer;

    @Test
    public void send() {
        for (int i = 0; i < 100; i++) {
            StockInfo stockCode = new StockInfo();
            stockCode.setCode("21342342342");
            produccer.send(stockCode);
        }
    }
}