package stock.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cgl
 * @date 2018/11/15 0:46
 */
@RestController
public class TestController {

    @GetMapping("test")
    public String test(){
        return "hello world";
    }
}
