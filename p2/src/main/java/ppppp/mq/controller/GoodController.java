package ppppp.mq.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ppppp.mq.bean.Goods;
import ppppp.mq.service.MQService;

@Controller
@RequestMapping("/goods")
public class GoodController {

    @Autowired
    private MQService mqService;

    @RequestMapping("/add")
    public String add(Goods goods) throws JsonProcessingException {
        mqService.sendMsg(goods);
        return "goods-add";
    }
}
