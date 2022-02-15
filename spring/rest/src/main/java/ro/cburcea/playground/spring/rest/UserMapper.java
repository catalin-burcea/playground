package ro.cburcea.playground.spring.rest;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto mapToUserDto(User user);

    UserV2Dto mapToUserV2Dto(User user);

    User map(UserDto userDto);

    List<UserDto> mapToUsers(List<User> users);

    List<UserV2Dto> mapToUsersV2(List<User> users);

}