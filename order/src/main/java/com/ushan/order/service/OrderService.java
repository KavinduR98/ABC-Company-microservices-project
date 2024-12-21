package com.ushan.order.service;

import com.ushan.order.dto.OrderDTO;
import com.ushan.order.model.Order;
import com.ushan.order.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<OrderDTO> getAllOrders(){
        List<Order> orderList = orderRepository.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDTO>>(){}.getType());
    }

    public OrderDTO saveOrder(OrderDTO orderDTO){
        orderRepository.save(modelMapper.map(orderDTO, Order.class));
        return orderDTO;
    }

    public OrderDTO updateOrder(OrderDTO orderDTO){
        orderRepository.save(modelMapper.map(orderDTO, Order.class));
        return orderDTO;
    }

    public String deleteOrder(Integer orderId){
        // Check if the order exists
        boolean exists = orderRepository.existsById(orderId);

        if (!exists) {
            return "Order with ID " + orderId + " not found!";
        }

        // If the item exists, delete it
        orderRepository.deleteById(orderId);
        return "Order Deleted!";
    }

    public OrderDTO getOrder(Integer orderId){
        Order order = orderRepository.getOrderById(orderId);
        return modelMapper.map(order, OrderDTO.class);
    }

}
