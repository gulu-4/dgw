package com.chards.committee.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class UserInfo implements Serializable {
    private String id;
    private String name;
    private String department;
    private String work;
}
