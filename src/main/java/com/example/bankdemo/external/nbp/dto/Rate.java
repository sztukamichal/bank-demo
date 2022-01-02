package com.example.bankdemo.external.nbp.dto;

import java.util.List;

public record Rate(Table table, String currency, String code, List<RateStamp> rates) {

}
