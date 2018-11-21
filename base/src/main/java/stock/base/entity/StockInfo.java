package stock.base.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 股票价格记录表
 *
 * @author cgl
 * @date 2018/11/14 21:07
 */
@Data
@Entity
@Table(name = "stock_info", indexes = {@Index(name = "queryTime", columnList = "queryTime")
        , @Index(name = "code", columnList = "code")})
public class StockInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    /**
     * 股票代码
     */
    @Column
    private String code;
    /**
     * 开盘价
     */
    @Column
    private Double openPrice;
    /**
     * 当前价格
     */
    @Column
    private Double nowPrice;
    /**
     * 当日最高价
     */
    @Column
    private Double maxPrice;
    /**
     * 当日最低价
     */
    @Column
    private Double minPrice;

    /**
     * 成交股数
     */
    @Column
    private Long count;

    @Column
    private Double lastOff;

    /**
     * 成交金额
     */
    @Column
    private Double total;

    /**
     * 查询时间
     */
    @Column
    private Timestamp queryTime;

    /**
     * 记录创建时间
     */
    @Column

    private Timestamp creatTime;

    public StockInfo(String name, String code, Double openPrice, Double nowPrice, Double maxPrice, Double minPrice, Long count, Double total, Timestamp queryTime) {
        this.code = code;
        this.name = name;
        this.openPrice = openPrice;
        this.nowPrice = nowPrice;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.count = count;
        this.total = total;
        this.queryTime = queryTime;
    }

    public StockInfo() {
    }

    /**
     * 振幅
     */
    @Column
    private Double swing;

    /**
     * 涨幅
     */
    @Column
    private Double increase;

}
