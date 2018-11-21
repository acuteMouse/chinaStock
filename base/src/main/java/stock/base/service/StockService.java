package stock.base.service;

import stock.base.entity.StockCode;
import stock.base.entity.StockInfo;

import java.util.List;
import java.util.Map;

public interface StockService {
    /**
     * 取所有的股票代码
     *
     * @param url 抓去数据地址
     * @return
     */
    Map<String, String> getAllCode(String url);

    /**
     * 获取股票当前价格
     *
     * @param stockCode 股票代码
     * @return 股票实时数据
     */
    StockInfo getPrice(StockCode stockCode);


    /**
     * 查询在振幅swing之内,涨幅increase内，成交额total内的股票数据
     *
     * @param increase  股票代码
     * @param swing     振幅
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param total     成交额
     * @return
     */
    List<StockInfo> getCodeBySwing(Double swing, Double increase, String startTime, String endTime, Long total);
}
