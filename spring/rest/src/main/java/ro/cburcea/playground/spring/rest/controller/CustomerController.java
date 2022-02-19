package ro.cburcea.playground.spring.rest.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.cburcea.playground.spring.rest.Utils;
import ro.cburcea.playground.spring.rest.domain.Customer;
import ro.cburcea.playground.spring.rest.domain.Order;
import ro.cburcea.playground.spring.rest.dtos.CustomerDto;
import ro.cburcea.playground.spring.rest.dtos.OrderDto;
import ro.cburcea.playground.spring.rest.mapper.CustomerMapper;
import ro.cburcea.playground.spring.rest.mapper.OrderMapper;
import ro.cburcea.playground.spring.rest.service.CustomerService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private static final String API_V1 = "application/vnd.rest.v1+json";
    private static final String APPLICATION_JSON_PATCH_V1_JSON = "application/json-patch.v1+json";
    private static final String PATCH_DESC = "    \n" +
            "     PATCH method\n" +
            "     Content-Type = application/json-patch.v1+json\n" +
            "     [\n" +
            "     {\n" +
            "     \"op\":\"test\",\n" +
            "     \"path\":\"/age\",\n" +
            "     \"value\": 100\n" +
            "     },\n" +
            "     {\n" +
            "     \"op\":\"replace\",\n" +
            "     \"path\":\"/age\",\n" +
            "     \"value\":77\n" +
            "     },\n" +
            "     {\n" +
            "     \"op\":\"replace\",\n" +
            "     \"path\":\"/firstName\",\n" +
            "     \"value\":\"Fooo\"\n" +
            "     },\n" +
            "     {\n" +
            "     \"op\":\"copy\",\n" +
            "     \"from\":\"/orders/1\",\n" +
            "     \"path\":\"/orders/-\"\n" +
            "     },\n" +
            "     {\n" +
            "     \"op\":\"remove\",\n" +
            "     \"path\":\"/orders/1\"\n" +
            "     },\n" +
            "     {\n" +
            "     \"op\":\"move\",\n" +
            "     \"from\":\"/orders/0\",\n" +
            "     \"path\":\"/orders/-\"\n" +
            "     },\n" +
            "     {\n" +
            "     \"op\":\"copy\",\n" +
            "     \"from\":\"/orders/1\",\n" +
            "     \"path\":\"/orders/-\"\n" +
            "     }\n" +
            "     ]\n" +
            "     ";

    @Autowired
    private CustomerService customerService;


    @Operation(summary = "Get a list of customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A list of customers or an empty list if there is no customer",
                    content = {@Content(mediaType = API_V1, schema = @Schema(implementation = CustomerDto.class))})}
    )
    // built-in support for HEAD and OPTIONS for GET methods
    @GetMapping(produces = API_V1)
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        final CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.SECONDS);
        final List<Customer> customers = customerService.findAll();
        return ResponseEntity
                .ok()
                .cacheControl(cacheControl)
                .body(CustomerMapper.INSTANCE.mapToCustomersDto(customers));
    }


    @Operation(summary = "Get a customer by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found",
                    content = {@Content(mediaType = API_V1, schema = @Schema(implementation = CustomerDto.class))}),
            @ApiResponse(responseCode = "404", description = "Customer not found")}
    )
    @GetMapping(value = "/{id}", produces = API_V1)
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id) {
        return customerService.findById(id)
                .map(customer -> ResponseEntity.ok(CustomerMapper.INSTANCE.mapToCustomerDto(customer)))
                .orElse(ResponseEntity.notFound().build());
    }


    @Operation(summary = "Get a list of orders by customer id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found",
                    content = {@Content(mediaType = API_V1, schema = @Schema(implementation = OrderDto.class))}),
            @ApiResponse(responseCode = "404", description = "Customer not found")}
    )
    @GetMapping(value = "/{id}/orders", produces = API_V1)
    public ResponseEntity<List<OrderDto>> getCustomerOrders(@PathVariable Long id) {
        if (customerService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        final CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.SECONDS);
        final List<Order> orders = customerService.findAllOrdersByCustomerId(id);
        return ResponseEntity
                .ok()
                .cacheControl(cacheControl)
                .body(OrderMapper.INSTANCE.mapToOrdersDto(orders));
    }


    /**
     * POST request creates a resource. The server assigns a URI for the new resource, and returns that URI to the client.
     * In the REST model, you frequently apply POST requests to collections. The new resource is added to the collection.
     * A POST request can also be used to submit data for processing to an existing resource, without any new resource being created.
     */
    @Operation(summary = "Create a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer successfully created"),
            @ApiResponse(responseCode = "409", description = "Customer already exists")}
    )
    @PostMapping(consumes = API_V1)
    public ResponseEntity<Void> addCustomer(@RequestBody CustomerDto newCustomerDto) {
        Optional<Customer> customer = customerService.findById(newCustomerDto.getId());

        if (customer.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Customer newCustomer = customerService.insert(CustomerMapper.INSTANCE.mapToCustomer(newCustomerDto));
        return ResponseEntity
                .created(linkTo(CustomerController.class).slash(newCustomer.getId()).toUri())
                .build();
    }


    @Operation(summary = "Delete a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "204", description = "Customer successfully deleted")}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);

        if (customer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        customerService.findAll().remove(customer.get());
        return ResponseEntity.noContent().build();
    }


    /**
     * A PUT request creates a resource or updates an existing resource.
     * The client specifies the URI for the resource. The request body contains a complete representation of the resource.
     * If a resource with this URI already exists, it is replaced. Otherwise a new resource is created, if the server supports doing so.
     * PUT requests are most frequently applied to resources that are individual items, such as a specific customer,
     * rather than collections. A server might support updates but not creation via PUT. Whether to support creation via PUT
     * depends on whether the client can meaningfully assign a URI to a resource before it exists.
     * If not, then use POST to create resources and PUT or PATCH to update.
     */
    @Operation(summary = "Update a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer successfully created"),
            @ApiResponse(responseCode = "204", description = "Customer successfully updated")}
    )
    @PutMapping(value = "/{id}", consumes = API_V1)
    public ResponseEntity<Void> updateCustomer(@RequestBody CustomerDto customerDto, @PathVariable Long id) {
        Optional<Customer> optionalCustomer = customerService.findById(id);

        if (optionalCustomer.isEmpty()) {
            Customer updatedCustomer = CustomerMapper.INSTANCE.mapToCustomer(customerDto);
            Customer newCustomer = customerService.insert(updatedCustomer);
            return ResponseEntity
                    .created(linkTo(CustomerController.class).slash(newCustomer.getId()).toUri())
                    .build();
        }
        Customer customer = CustomerMapper.INSTANCE.updateCustomerFromDto(customerDto, optionalCustomer.get());
        customerService.update(customer);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Partially update a customer using JSON PATCH", description = PATCH_DESC)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid JSON input"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "An unexpected exception occurred")}
    )
    @PatchMapping(value = "/{id}", consumes = {APPLICATION_JSON_PATCH_V1_JSON})
    public ResponseEntity<String> patchCustomerMethod2(@RequestBody JsonPatch patch, @PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);

        if (customer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            CustomerDto customerDtoPatched = Utils.applyPatchToCustomer(patch, CustomerMapper.INSTANCE.mapToCustomerDto(customer.get()));
            Customer patchedCustomer = CustomerMapper.INSTANCE.updateCustomerFromDto(customerDtoPatched, customer.get());
            customerService.update(patchedCustomer);
        } catch (JsonPatchException | JsonProcessingException e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    // do we need to version PATCH?!


    /**
     * A PATCH request performs a partial update to an existing resource. The client specifies the URI for the resource.
     * The request body specifies a set of changes to apply to the resource. This can be more efficient than using PUT,
     * because the client only sends the changes, not the entire representation of the resource.
     * Technically PATCH can also create a new resource (by specifying a set of updates to a "null" resource), if the server supports this.
     */
    @Operation(summary = "Partially update a customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "204", description = "Customer successfully updated")}
    )
    @PatchMapping(value = "/{id}", consumes = API_V1)
    public ResponseEntity<Void> patchCustomerMethod1(@RequestBody CustomerDto patchedCustomer, @PathVariable Long id) {
        Optional<Customer> optionalCustomer = customerService.findById(id);

        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Customer customer = CustomerMapper.INSTANCE.updateCustomerFromDto(patchedCustomer, optionalCustomer.get());

        customerService.update(customer);
        return ResponseEntity.noContent().build();
    }

}
