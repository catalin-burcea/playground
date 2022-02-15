package ro.cburcea.playground.spring.rest;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ro.cburcea.playground.spring.rest.dtos.UserDto;
import ro.cburcea.playground.spring.rest.dtos.UserV2Dto;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto mapToUserDto(User user);

    @Mapping(target = "name", expression = "java(user.getFirstName() + ' ' + user.getLastName())")
    UserV2Dto mapToUserV2Dto(User user);

    User mapToUser(UserDto userDto);

    @Mapping(target = "firstName", expression = "java(userV2Dto.getName().split(\" \")[0])")
    @Mapping(target = "lastName", expression = "java(userV2Dto.getName().split(\" \")[1])")
    User mapToUser(UserV2Dto userV2Dto);

    List<UserDto> mapToUsers(List<User> users);

    List<UserV2Dto> mapToUsersV2(List<User> users);

}