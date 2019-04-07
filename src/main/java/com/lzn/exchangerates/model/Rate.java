package com.lzn.exchangerates.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Setter
@Getter
public class Rate {

    private String currency;
    private String code;
    private String mid;

}
