package stock.base.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import stock.base.entity.StockInfo;

import java.util.List;

/**
 * @author cgl
 * @date 2018/11/16 22:52
 */
@Repository
@Slf4j
public class StockInfoDao {
    @Autowired
    JdbcTemplate template;

    public List<StockInfo> queryNavie(String sql) {
        return template.query(sql, (rs, rowNum) -> {
            StockInfo stockInfo = new StockInfo();
            stockInfo.setCode(rs.getString("code"));
            stockInfo.setOpenPrice(rs.getDouble("open_price"));
            stockInfo.setId(rs.getLong("id"));
            stockInfo.setCount(rs.getLong("count"));
            stockInfo.setNowPrice(rs.getDouble("now_price"));
            stockInfo.setTotal(rs.getDouble("total"));
            stockInfo.setMinPrice(rs.getDouble("min_Price"));
            stockInfo.setMaxPrice(rs.getDouble("max_Price"));
            stockInfo.setName(rs.getString("name"));
            stockInfo.setId(rs.getLong("id"));
            stockInfo.setQueryTime(rs.getTimestamp("query_time"));
            stockInfo.setLastOff(rs.getDouble("last_off"));
            stockInfo.setSwing(rs.getDouble("swing"));
            stockInfo.setIncrease(rs.getDouble("increase"));
            return stockInfo;
        });
    }

    public List<StockInfo> fingByCodeAngQueryTime(String startTime, String endTime, String code) {
        String sql = " SELECT * from stock_info where  1=1 and CODE='" + code + "' and query_time<'" + endTime + "'" +
                " and query_time >'" + startTime + "';";
        log.info(sql);
        return queryNavie(sql);
    }

    public StockInfo fingByCode(String code) {
        String sql = " SELECT * from stock_info where  1=1 and CODE='" + code + "' ORDER BY query_time DESC LIMIT 1;";
        log.info(sql);
        List<StockInfo> stockInfos = queryNavie(sql);
        if (stockInfos != null && stockInfos.size() > 0) {
            return stockInfos.get(0);
        }
        return null;
    }

    public List<StockInfo> getByQueryTime(String startTime, String endTime) {
        long start = System.currentTimeMillis();
        String sql = " SELECT * from stock_info where  1=1  and query_time<'" + endTime + "'" +
                " and query_time >'" + startTime + "';";
        List<StockInfo> stockInfos = queryNavie(sql);
        long end = System.currentTimeMillis();
        log.info("查询耗时：" + (end - start) + "ms");
        return stockInfos;
    }
}
