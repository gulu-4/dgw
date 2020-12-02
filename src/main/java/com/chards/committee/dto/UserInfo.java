package com.chards.committee.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfo implements Serializable {
    private String id;
    private String name;
    private String department;
    private String work;
}
