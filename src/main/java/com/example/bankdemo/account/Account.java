package com.example.bankdemo.account;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT")
@Getter
public class Account {
    @Id
    private Integer id;
    private String name;
    private Double balance;
}
