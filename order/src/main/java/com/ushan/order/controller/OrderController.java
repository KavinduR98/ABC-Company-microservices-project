package com.ushan.order.controller;

import com.ushan.order.common.OrderResponse;
import com.ushan.order.dto.OrderDTO;
import com.ushan.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(path = "/test")
    public String test(){
        return "This is an Order service!";
    }

    @GetMapping(path = "/get_orders")
    public List<OrderDTO> getOrders(){
        return orderService.getAllOrders();
    }

    @PostMapping(path = "/save_order")
    public OrderResponse saveOrder(@RequestBody OrderDTO orderDTO){
        return orderService.saveOrder(orderDTO);
    }

    @PutMapping(path = "/update_order")
    public OrderDTO updateOrder(@RequestBody OrderDTO orderDTO){
        return orderService.updateOrder(orderDTO);
    }

    @DeleteMapping(path = "/delete_order/{orderId}")
    public String deleteOrder(@PathVariable Integer orderId){
        return orderService.deleteOrder(orderId);
    }

    @GetMapping(path = "/get_order/{orderId}")
    public OrderDTO getOrder(@PathVariable Integer orderId){
        return orderService.getOrder(orderId);
    }

}
