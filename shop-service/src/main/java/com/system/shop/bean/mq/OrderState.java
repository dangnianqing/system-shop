package com.system.shop.bean.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderState implements Serializable {

    private static final long serialVersionUID = 1L;
    private String orderCode;
    private String status;
}
