package com.babak.entity;


import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    private int id;
    private Status status;
    private String detailStatus;
    private Timestamp created;
    private List<OrderItem> orderItems;
}
