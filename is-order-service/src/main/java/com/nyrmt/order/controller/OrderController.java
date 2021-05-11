package com.nyrmt.order.controller;

import com.nyrmt.order.bean.OrderInfo;
import com.nyrmt.order.bean.PriceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@RestController
@RequestMapping("/order")
public class OrderController {
    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public OrderInfo CreateOrder(@RequestBody OrderInfo orderInfo, @RequestHeader String username){
        PriceInfo priceInfo = restTemplate.getForObject("http://127.0.0.1:8082/price/" + orderInfo.getId(), PriceInfo.class);
        assert priceInfo != null;
        logger.info("price is " + priceInfo.getPrice());
        logger.info("username is " + username);
        return orderInfo;
    }

    @GetMapping("/{id}")
    public OrderInfo getOrder(@PathVariable Long id){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(id);
        orderInfo.setPrice(new BigDecimal(100));
        return orderInfo;
    }
}
