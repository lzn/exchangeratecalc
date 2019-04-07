package com.lzn.exchangerates.controller;


import com.lzn.exchangerates.model.Rate;
import com.lzn.exchangerates.model.Result;
import com.lzn.exchangerates.service.NBPRatesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

@Slf4j
@RestController
public class RatesController {

    @Autowired
    private NBPRatesService ratesService;

    @GetMapping("/calculate")
    public Result calculate(@RequestParam(value="value", required=true) String value,
                            @RequestParam(value="src", required=true) String src,
                            @RequestParam(value="dst", required=true) String dst) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, URISyntaxException {

        log.info("calculate: " + value + " ; src: " + src + " ; dst: " + dst);
        HashMap<String, Rate> rates = ratesService.getRates();

        BigDecimal v = new BigDecimal(value);
        Result r = new Result();

        if(!"PLN".equals(src)){
            Rate srcRate = rates.get(src);
            if(srcRate == null){
                log.info("Incorrect src currency");
                r.setMsg("Incorrect src currency");
                r.setResult(null);
                return r;
            }
            v=v.multiply(new BigDecimal(srcRate.getMid()));
        }

        if(!"PLN".equals(dst)){
            Rate dstRate = rates.get(dst);
            if(dstRate == null){
                log.info("Incorrect dst currency");
                r.setMsg("Incorrect dst currency");
                r.setResult(null);
                return r;
            }
            v = v.divide(new BigDecimal(dstRate.getMid()), MathContext.DECIMAL128);
        }

        r.setMsg("OK");
        r.setResult(v);
        log.info("Result: " + v.toString());
        return r;
    }

}
