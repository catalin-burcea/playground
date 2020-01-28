package com.cburcea.playground.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CarMapper {
    @Mapping(source = "make", target = "manufacturer")
    @Mapping(source = "numberOfSeats", target = "seatCount")
    CarDto carToCarDto(Car car);
//
//    @Mapping(source = "name", target = "fullName")
//    PersonDto personToPersonDto(Person person);
}