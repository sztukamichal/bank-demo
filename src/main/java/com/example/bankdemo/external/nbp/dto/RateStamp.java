package com.example.bankdemo.external.nbp.dto;

import java.util.Date;

public record RateStamp(String no, Date effectiveDate, double mid) {
}
