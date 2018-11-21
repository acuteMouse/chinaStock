package stock.rabbit.produce;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import stock.base.entity.StockCode;
import stock.base.entity.StockInfo;
import stock.rabbit.Constant.RmqQueueConstant;

import java.util.List;

/**
 * @author Jimy
 */
@Component
public class Produccer {


    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(List<StockCode> stockCodes) {
        this.rabbitTemplate.convertAndSend("hello");
    }

    /**
     * @param stockInfo
     */
    public void send(StockInfo stockInfo) {
        this.rabbitTemplate.convertAndSend(RmqQueueConstant.STOCK_QUEUE, JSON.toJSONString(stockInfo));
    }
}