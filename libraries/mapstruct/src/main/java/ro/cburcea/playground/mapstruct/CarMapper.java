package ro.cburcea.playground.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CarMapper {

    @Mapping(source = "make", target = "manufacturer")
    @Mapping(source = "numberOfSeats", target = "seatCount")
    CarDto carToCarDto(Car car);

    @Mapping(source = "manufacturer", target = "make")
    @Mapping(source = "seatCount", target = "numberOfSeats")
    Car carDtoToCar(CarDto car);

}