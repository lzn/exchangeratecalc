package com.lzn.exchangerates.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Setter
@Getter
public class NBPTable {

    private String table;
    private String no;
    private String effectiveDate;
    private List<Rate> rates;

}
