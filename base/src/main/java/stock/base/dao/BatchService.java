package stock.base.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import stock.base.entity.StockInfo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author cgl
 * @date 2018/11/14 22:49
 */
@Service
@Slf4j
@Transactional
public class BatchService implements Batch<StockInfo> {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<StockInfo> batchSave(List<StockInfo> list) {
        int batchSize = 20;
        for (int i = 0; i < list.size(); i++) {
            if (i > 0 && i % batchSize == 0) {
                em.flush();
                em.clear();
            }
            em.persist(list.get(i));
        }
        return list;
    }

    @Override
    public List<StockInfo> batchInsert(List<StockInfo> list) {
        String sql = "insert into stock_info (code, count, creat_time, " +
                "max_price, min_price, now_price, open_price, query_time, total,name,last_off,swing,increase)" +
                " values (?, ?, now(), ?, ?, ?, ?, ?, ?,?,?,?,?)";
        int[] result = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, list.get(i).getCode());
                ps.setLong(2, list.get(i).getCount());
                ps.setDouble(3, list.get(i).getMaxPrice());
                ps.setDouble(4, list.get(i).getMinPrice());
                ps.setDouble(5, list.get(i).getNowPrice());
                ps.setDouble(6, list.get(i).getOpenPrice());
                ps.setTimestamp(7, list.get(i).getQueryTime());
                ps.setDouble(8, list.get(i).getTotal());
                ps.setString(9, list.get(i).getName());
                ps.setDouble(10, list.get(i).getLastOff());
                ps.setDouble(11, list.get(i).getSwing());
                ps.setDouble(12, list.get(i).getIncrease());
            }

            @Override
            public int getBatchSize() {
                return list.size();
            }
        });
        log.info("批量保存成功"+result.length+"条记录。");
        return null;
    }
}
