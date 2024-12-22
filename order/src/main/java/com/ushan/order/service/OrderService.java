package com.ushan.order.service;

import com.ushan.inventory.dto.InventoryDTO;
import com.ushan.order.common.ErrorOrderResponse;
import com.ushan.order.common.OrderResponse;
import com.ushan.order.common.SuccessOrderResponse;
import com.ushan.order.dto.OrderDTO;
import com.ushan.order.model.Order;
import com.ushan.order.repository.OrderRepository;
import com.ushan.product.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
@Transactional
public class OrderService {

    private final WebClient inventoryWebClient;
    private final WebClient productWebClient;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    public OrderService(WebClient inventoryWebClient, WebClient productWebClient, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.inventoryWebClient = inventoryWebClient;
        this.productWebClient = productWebClient;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    public List<OrderDTO> getAllOrders(){
        List<Order> orderList = orderRepository.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDTO>>(){}.getType());
    }

    public OrderResponse saveOrder(OrderDTO orderDTO){

        Integer itemId = orderDTO.getItemId();
        Integer amount = orderDTO.getAmount();

        try{
            // Fetch inventory details
            InventoryDTO inventoryResponse = inventoryWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/get_item/{itemId}").build(itemId))
                    .retrieve()
                    .bodyToMono(InventoryDTO.class)
                    .block();

            if (inventoryResponse == null) {
                return new ErrorOrderResponse("Inventory details not found for item ID: " + itemId);
            }

            Integer productId = inventoryResponse.getProductId();

            // Fetch product details
            ProductDTO productResponse = productWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/get_product/{productId}").build(itemId))
                    .retrieve()
                    .bodyToMono(ProductDTO.class)
                    .block();

            if (productResponse == null) {
                return new ErrorOrderResponse("Product details not found for product ID: " + productId);
            }

            // Validate inventory quantity and product availability
            if (inventoryResponse.getQuantity() >= amount){
                if (productResponse.getForSale() == 1){
                    // Save order
                    Order order = modelMapper.map(orderDTO, Order.class);
                    orderRepository.save(order);

                    return new SuccessOrderResponse(orderDTO);
                }else {
                    return new ErrorOrderResponse("This item is not for sale");
                }
            }
            else {
                return new ErrorOrderResponse("Item quantity is not enough. Available: "
                        + inventoryResponse.getQuantity() + ", Requested: " + amount);
            }

        }
        catch (WebClientResponseException e) {
            // Handle server-side errors (e.g., 500 Internal Server Error)
            if (e.getStatusCode().is5xxServerError()) {
                return new ErrorOrderResponse("Item not found for ID: " + itemId + ". Please check and try again.");
            }
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
