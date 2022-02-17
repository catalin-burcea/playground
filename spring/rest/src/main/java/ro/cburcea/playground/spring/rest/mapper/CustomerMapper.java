package ro.cburcea.playground.spring.rest.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ro.cburcea.playground.spring.rest.domain.Customer;
import ro.cburcea.playground.spring.rest.dtos.CustomerDto;
import ro.cburcea.playground.spring.rest.dtos.CustomerV2Dto;

import java.util.List;

@Mapper(uses = OrderMapper.class)
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDto mapToCustomerDto(Customer customer);

    @Mapping(target = "name", expression = "java(customer.getFirstName() + ' ' + customer.getLastName())")
    CustomerV2Dto mapToCustomerV2Dto(Customer customer);

    Customer mapToCustomer(CustomerDto customerDto);

    @Mapping(target = "firstName", expression = "java(customerV2Dto.getName().split(\" \")[0])")
    @Mapping(target = "lastName", expression = "java(customerV2Dto.getName().split(\" \")[1])")
    Customer mapToCustomer(CustomerV2Dto customerV2Dto);

    List<CustomerDto> mapToCustomersDto(List<Customer> customers);

    List<CustomerV2Dto> mapToCustomersV2Dto(List<Customer> customers);

}