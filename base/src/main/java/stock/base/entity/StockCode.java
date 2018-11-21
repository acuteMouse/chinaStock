package stock.base.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author cgl
 * @date 2018/11/12 23:13
 */

@Entity
@Data
@Table(name = "stock_code")
public class StockCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 股票代码
     */
    @Column
    private String code;
    /**
     * 股票名称
     */
    @Column
    private String name;
    /**
     * 上市城市
     */
    @Column
    private String city;
    @Column
    Timestamp createTime;

    public StockCode(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public StockCode() {
    }
}
