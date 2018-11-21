package stock.base.dao;

import stock.base.entity.StockCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cgl
 * @date 2018/11/12 23:33
 */
@Service
public interface StockCodeDao extends CrudRepository<StockCode, Long> {
    /**
     * 根据股票代码查询
     *
     * @param code
     * @return
     */
    public List<StockCode> getByCode(String code);


}
