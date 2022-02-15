package ro.cburcea.playground.spring.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import ro.cburcea.playground.spring.rest.dtos.UserDto;
import ro.cburcea.playground.spring.rest.dtos.UserV2Dto;

public class UserUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static UserDto applyPatchToUser(JsonPatch patch, UserDto user) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(user, JsonNode.class));
        return objectMapper.treeToValue(patched, UserDto.class);
    }

    public static UserV2Dto applyPatchToUserV2(JsonPatch patch, UserV2Dto user) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(user, JsonNode.class));
        return objectMapper.treeToValue(patched, UserV2Dto.class);
    }
}
