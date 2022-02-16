package ro.cburcea.playground.spring.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import ro.cburcea.playground.spring.rest.dtos.CustomerDto;
import ro.cburcea.playground.spring.rest.dtos.CustomerV2Dto;

public class Utils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static CustomerDto applyPatchToCustomer(JsonPatch patch, CustomerDto customerDto) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(customerDto, JsonNode.class));
        return objectMapper.treeToValue(patched, CustomerDto.class);
    }

    public static CustomerV2Dto applyPatchToCustomerV2(JsonPatch patch, CustomerV2Dto customerV2Dto) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(customerV2Dto, JsonNode.class));
        return objectMapper.treeToValue(patched, CustomerV2Dto.class);
    }
}
