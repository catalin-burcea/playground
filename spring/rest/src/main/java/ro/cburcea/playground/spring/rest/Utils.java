package ro.cburcea.playground.spring.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import ro.cburcea.playground.spring.rest.dtos.CustomerDto;
import ro.cburcea.playground.spring.rest.dtos.CustomerV2Dto;

public class Utils {

    private static final Logger LOG = LoggerFactory.getLogger(Utils.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static CustomerDto applyPatchToCustomer(JsonPatch patch, CustomerDto customerDto) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(customerDto, JsonNode.class));
        return objectMapper.treeToValue(patched, CustomerDto.class);
    }

    public static CustomerV2Dto applyPatchToCustomerV2(JsonPatch patch, CustomerV2Dto customerV2Dto) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(customerV2Dto, JsonNode.class));
        return objectMapper.treeToValue(patched, CustomerV2Dto.class);
    }

    public static void logHeaders(MultiValueMap<String, String> headers) {
        headers.forEach((key, value) -> {
            LOG.info(String.format("Header '%s' = %s", key, String.join("|", value)));
        });
    }

    public static HttpHeaders buildCustomHeaders() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Custom-Header", "foo");
        return responseHeaders;
    }
}
