package ro.cburcea.playground.mapstruct;

import org.mapstruct.factory.Mappers;

public class MapStructDemo {

    public static void main(String[] args) {
        CarMapper mapper = Mappers.getMapper(CarMapper.class);
        Car car = new Car();
        car.setMake("make");
        car.setNumberOfSeats(33);
        car.setType("type");

        CarDto carDto = mapper.carToCarDto(car);

        System.out.println(carDto.getManufacturer());
        System.out.println(carDto.getSeatCount());
        System.out.println(carDto.getType());
    }

}
