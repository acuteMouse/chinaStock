package stock.rabbit.cum;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import stock.base.dao.Batch;
import stock.base.entity.StockInfo;

import java.util.ArrayList;
import java.util.List;

import static stock.rabbit.Constant.RmqQueueConstant.STOCK_QUEUE;


/**
 * @author Jimy
 */
@Component
@RabbitListener(queues = STOCK_QUEUE)
@Slf4j
public class Customer3 {
    @Autowired
    Batch batch;

    private static List<StockInfo> stockInfoList=new ArrayList<>(100);

    @RabbitHandler
    public void process(String stockInfoStr) {
        log.info("记录："+stockInfoStr);
        StockInfo stockInfo = JSON.parseObject(stockInfoStr, StockInfo.class);
        stockInfoList.add(stockInfo);
        if (stockInfoList.size()>100){
            batch.batchInsert(stockInfoList);
            stockInfoList.clear();
        }
    }

}