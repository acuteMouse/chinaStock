package com.zy.stock.mq.rabbit.constant;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author cgl
 * @date 2018/11/14 22:03
 */
public class RmqQueueConstant {
    public final static String STOCK_QUEUE = "stock_queue";
    public static AtomicInteger atomicInteger=new AtomicInteger(0);
}
