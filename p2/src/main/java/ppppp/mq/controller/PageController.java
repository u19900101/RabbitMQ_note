package ppppp.mq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ppppp.mq.bean.Goods;

@Controller
public class PageController {
    @RequestMapping("/goods-add.html")
    public String goodAdd(Goods goods){

        return "goods-add";
    }
}
