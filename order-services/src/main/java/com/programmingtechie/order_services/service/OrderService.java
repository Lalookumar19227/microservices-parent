package com.programmingtechie.order_services.service;


//import com.programmingtechie.order_services.dto.InventoryResponse;
import com.programmingtechie.order_services.dto.InventoryResponse;
import com.programmingtechie.order_services.dto.OrderLineItemsDto;
import com.programmingtechie.order_services.dto.OrderRequest;
//import com.programmingtechie.order_services.event.OrderPlacedEvent;
import com.programmingtechie.order_services.model.Order;
import com.programmingtechie.order_services.model.OrderLineItems;
import com.programmingtechie.order_services.repository.OrderRepository;
//import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
//import org.springframework.web.reactive.function.client.WebClient;

//import java.util.Arrays;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


//@RequiredArgsConstructor
@Transactional
//@Slf4j
@Service
public class OrderService {


    private final OrderRepository orderRepository;
    private final WebClient webClient;
    private final ObservationRegistry observationRegistry;
    private final ApplicationEventPublisher applicationEventPublisher;



    public OrderService(OrderRepository orderRepository, WebClient webClient,
                        ObservationRegistry observationRegistry,
                        ApplicationEventPublisher applicationEventPublisher) {
        this.orderRepository = orderRepository;
        this.webClient = webClient;
        this.observationRegistry = observationRegistry;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);


        List<String> skuCodes=order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                        .toList();

        // Call Inventory Service, and place order if product is in
        // stock
      InventoryResponse[]  inventoryResponsesArray=webClient.get()
                      .uri("http://localhost:8083/api/inventory",
                              uriBuilder -> uriBuilder .queryParam("skuCode", skuCodes).build())
                              .retrieve()
                                      .bodyToMono(InventoryResponse[].class)
                                              .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponsesArray)
                    .allMatch(InventoryResponse::isInStock);

      if(allProductsInStock){
        orderRepository.save(order);

    } else {
          throw new IllegalArgumentException("Product is not in stock, please try again later");
      }
//        List<String> skuCodes = order.getOrderLineItemsList().stream()
//                .map(OrderLineItems::getSkuCode)
//                .toList();
//
//        // Call Inventory Service, and place order if product is in
//        // stock
//        Observation inventoryServiceObservation = Observation.createNotStarted("inventory-service-lookup",
//                this.observationRegistry);
//        inventoryServiceObservation.lowCardinalityKeyValue("call", "inventory-service");
//        return inventoryServiceObservation.observe(() -> {
//            InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
//                    .uri("http://inventory-service/api/inventory",
//                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
//                    .retrieve()
//                    .bodyToMono(InventoryResponse[].class)
//                    .block();
//
//            boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
//                    .allMatch(InventoryResponse::isInStock);
//
//            if (allProductsInStock) {
//                orderRepository.save(order);
//                // publish Order Placed Event
//                applicationEventPublisher.publishEvent(new OrderPlacedEvent(this, order.getOrderNumber()));
//                return "Order Placed";
//            } else {
//                throw new IllegalArgumentException("Product is not in stock, please try again later");
//            }
//        });


//    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
//        OrderLineItems orderLineItems = new OrderLineItems();
//        orderLineItems.setPrice(orderLineItemsDto.getPrice());
//        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
//        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
//        return orderLineItems;
//    }


//    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
//        OrderLineItems orderLineItems = new OrderLineItems();
//        orderLineItems.setPrice(orderLineItemsDto.getPrice());
//        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
//        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
//        return orderLineItems;
//    }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {

        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;


    }
    }

