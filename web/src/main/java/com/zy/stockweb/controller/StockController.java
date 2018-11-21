package com.zy.stockweb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zy.stockweb.common.ResponseMap;
import stock.base.entity.StockInfo;
import stock.base.service.StockService;
import stock.base.util.DateUtil;
import stock.job.StockJob;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author cgl
 * @date 2018/11/16 21:16
 */
@RestController
@Slf4j
@RequestMapping("code")
public class StockController {

    @Autowired
    StockService stockService;

    @Autowired
    StockJob job;

    /**
     * 成交额、振幅选股法
     * 上午收盘后振幅swing% 以内，成交额小于total元，涨幅在increase内的股票
     *
     * @param swing
     * @param total
     * @return
     */
    @GetMapping("queryBySwing")
    public ResponseMap queryBySwing(Double swing, Long total, Double increase) {
        ResponseMap responseMap = new ResponseMap();
        if (swing == null) {
            responseMap.setReturnFail("振幅swing为空！");
            return responseMap;
        }
        if (increase == null) {
            responseMap.setReturnFail("涨幅increase为空！");
            return responseMap;
        }
        String start= DateUtil.getToday();
        String end=DateUtil.format(DateUtil.getSecondDay(new Timestamp(System.currentTimeMillis())));
        List<StockInfo> codeList = stockService.getCodeBySwing(swing, increase,start  , end, total);
        if (codeList != null) {
            responseMap.setReturnSuccess(codeList, "共" + String.valueOf(codeList.size()) + "条记录");
        }
        return responseMap;
    }

}
