package com.protech.orderservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

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

    public void placeOrder(OrderRequest orderRequest) {
    	Order order =new Order();
    	order.setOrderNumber(UUID.randomUUID().toString());

    	List<OrderLineItems> orderLineitems = orderRequest.getOrderLineItemsDtosList()
    			.stream()
    			.map(this::mapToDto)
    			.toList();
    	order.setOrderLineItemsList(orderLineitems);
    	
    	orderRepository.save(order);
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
