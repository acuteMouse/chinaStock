package stock.base.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import stock.base.entity.StockInfo;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author cgl
 * @date 2018/11/17 21:45
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class StockInfoDaoTest {
    @Autowired
    StockInfoDao stockInfoDao;

    @Test
    public void getByQueryTime() {
        long start = System.currentTimeMillis();
        List<StockInfo> stockInfos = stockInfoDao.getByQueryTime("2018-11-16 00:00:00", "2018-11-17 00:00:00");
        Map<String, List<StockInfo>> stockListMap = stockInfos.stream().collect(Collectors.groupingBy(StockInfo::getCode));
        stockListMap.forEach((k, v) -> {
            StockInfo stockInfo = v.stream().sorted(Comparator.comparing(StockInfo::getQueryTime)).findFirst().get();
            System.out.println(stockInfo);
        });
        long end = System.currentTimeMillis();
        log.info("耗时：" + (end - start) + "ms");
    }
}