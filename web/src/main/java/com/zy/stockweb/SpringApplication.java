package com.zy.stockweb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author cgl
 * @date 2018/11/12 23:31
 */
@SpringBootApplication
@Slf4j
@ComponentScan(basePackages = {"stock.base.*","com.zy.stockweb"})
public class SpringApplication {


    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
    }


}

