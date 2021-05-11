package com.nyrmt.price.controller;

import com.nyrmt.price.bean.PriceInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/price")
public class PriceController {

    @GetMapping("/{id}")
    public PriceInfo getPrice(@PathVariable Long id){
        PriceInfo priceInfo = new PriceInfo();
        priceInfo.setId(id);
        priceInfo.setPrice(new BigDecimal(100));
        return priceInfo;
    }
}
