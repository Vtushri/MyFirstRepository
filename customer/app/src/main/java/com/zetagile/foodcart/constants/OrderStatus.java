package com.zetagile.foodcart.constants;

import java.io.Serializable;

public enum OrderStatus implements Serializable {
    PENDING,
    PLACED,
    PROCESSING,
    SHIPPED,
    CANCELLED
}
