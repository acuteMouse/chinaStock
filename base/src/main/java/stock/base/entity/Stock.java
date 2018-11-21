package stock.base.entity;

import org.springframework.stereotype.Component;

@Component
public class Stock {
    private String name;
    private Double nowPrice;

    public Stock() {
    }

    public Stock(String name, Double nowPrice) {
        this.name = name;
        this.nowPrice = nowPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(Double nowPrice) {
        this.nowPrice = nowPrice;
    }
}
