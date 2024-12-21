package com.ushan.order.service;

import com.ushan.inventory.dto.InventoryDTO;
import com.ushan.order.common.ErrorOrderResponse;
import com.ushan.order.common.OrderResponse;
import com.ushan.order.common.SuccessOrderResponse;
import com.ushan.order.dto.OrderDTO;
import com.ushan.order.model.Order;
import com.ushan.order.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Transactional
public class OrderService {

    private final WebClient webClient;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    public OrderService(WebClient.Builder webClientBuilder, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/api/v1").build();
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    public List<OrderDTO> getAllOrders(){
        List<Order> orderList = orderRepository.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDTO>>(){}.getType());
    }

    public OrderResponse saveOrder(OrderDTO orderDTO){

        Integer itemId = orderDTO.getItemId();
//        System.out.println("Received Item ID: " + itemId);

        try{
            InventoryDTO inventoryResponse = webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/get_item/{itemId}").build(itemId))
                    .retrieve()
                    .bodyToMono(InventoryDTO.class)
                    .block();

            assert inventoryResponse != null;

            System.out.println(inventoryResponse);

            if (inventoryResponse.getQuantity() > 0){
                orderRepository.save(modelMapper.map(orderDTO, Order.class));
                return new SuccessOrderResponse(orderDTO);
            }
            else {
                return new ErrorOrderResponse("Item not available, please try later!");
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;

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
