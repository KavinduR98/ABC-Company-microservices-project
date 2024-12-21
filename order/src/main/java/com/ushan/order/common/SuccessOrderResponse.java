package com.ushan.order.common;

import com.ushan.order.dto.OrderDTO;
import lombok.Getter;

@Getter
public class SuccessOrderResponse implements OrderResponse{
    private final OrderDTO order;

    public SuccessOrderResponse(OrderDTO order){
        this.order = order;
    }
}
