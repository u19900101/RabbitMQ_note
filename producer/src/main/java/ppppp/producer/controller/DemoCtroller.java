package ppppp.producer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ppppp.producer.service.MQService;

import javax.annotation.Resource;

@RestController
public class DemoCtroller {
    @Resource
    private MQService mqService;

    @RequestMapping("test")
    public String demo(String msg){
        mqService.sendMsg(msg);
        return "success";
    }
}
