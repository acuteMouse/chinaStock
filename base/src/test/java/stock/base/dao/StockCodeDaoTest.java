package stock.base.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import stock.base.entity.StockCode;
import stock.base.util.DateUtil;

/**
 * @author cgl
 * @date 2018/11/12 23:46
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class StockCodeDaoTest {

    @Autowired
    private StockCodeDao stockCodeDao;

    @Autowired
    Batch batch;

    @Test
    public void getByCode() {
        StockCode code = new StockCode();
        code.setCity("sh");
        code.setCode("0");
        code.setName("京东方A");
        code.setCreateTime(DateUtil.getNowTimestamp());
        StockCode stockCode = stockCodeDao.save(code);
        Assert.assertNotNull(stockCode.getId());


    }
}