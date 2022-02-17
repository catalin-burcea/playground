package ro.cburcea.playground.spring.rest.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.cburcea.playground.spring.rest.domain.Order;
import ro.cburcea.playground.spring.rest.dtos.OrderDto;

import java.util.List;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto mapToOrderDto(Order order);

    Order mapToOrder(OrderDto orderDto);

    List<OrderDto> mapToOrdersDto(List<Order> orders);

}