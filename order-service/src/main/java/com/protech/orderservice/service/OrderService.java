package com.protech.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.protech.orderservice.dto.InventoryResponse;
import com.protech.orderservice.dto.OrderLineItemsDto;
import com.protech.orderservice.dto.OrderRequest;
import com.protech.orderservice.model.Order;
import com.protech.orderservice.model.OrderLineItems;
import com.protech.orderservice.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final OrderRepository orderRepository;
	private final WebClient.Builder webClientBuilder;

    public String placeOrder(OrderRequest orderRequest) {
    	Order order =new Order();
    	order.setOrderNumber(UUID.randomUUID().toString());

    	List<OrderLineItems> orderLineitems = orderRequest.getOrderLineItemsDtosList()
    			.stream()
    			.map(this::mapToDto)
    			.toList();
    	order.setOrderLineItemsList(orderLineitems);
    	
    	List<String> skuCodes= order.getOrderLineItemsList().stream().map(orderLineItem -> orderLineItem.getSkuCode()).toList();

    	
    	InventoryResponse[] inventoryResponseArray= webClientBuilder.build().get()
    		.uri("http://inventory-service/api/inventory", 
    				uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
    		.retrieve()
    		.bodyToMono(InventoryResponse[].class)
    		.block();
    		
    	Boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
    			.allMatch(inventoryResponse -> inventoryResponse.isInStock());   
    	
    	if(allProductsInStock) {
    	orderRepository.save(order);
    	return "Order placed successfully...!!";
    	} else
    	{
    		throw new IllegalArgumentException("Product is not in stock , please try again later");
    	}
    }

    
    
    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto)
    {
    	
    	OrderLineItems orderLineItems=new OrderLineItems();
    	orderLineItems.setPrice(orderLineItemsDto.getPrice());
    	orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
    	orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
    	return orderLineItems;
    }
    
    
//    private static Order mapToOrder(OrderRequest orderRequest) {
//        Order order = new Order();
//        order.setPrice(orderRequest.price());
//        order.setQuantity(orderRequest.quantity());
//        order.setSkuCode(orderRequest.skuCode());
//        return order;
//    }
}
