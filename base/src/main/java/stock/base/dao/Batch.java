package stock.base.dao;

import stock.base.entity.StockInfo;

import java.util.List;

/**
 * @author cgl
 * @date 2018/11/14 22:48
 */
public interface Batch<T> {
    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    List<T> batchSave(List<T> list);


    List<StockInfo> batchInsert(List<StockInfo> list);
}
