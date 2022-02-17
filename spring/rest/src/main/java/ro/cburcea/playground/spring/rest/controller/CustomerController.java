package ro.cburcea.playground.spring.rest.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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

    @Autowired
    private CustomerService customerService;

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


    @GetMapping(value = "/{id}", produces = API_V1)
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable int id) {
        return customerService.findById(id)
                .map(customer -> ResponseEntity.ok(CustomerMapper.INSTANCE.mapToCustomerDto(customer)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{id}/orders", produces = API_V1)
    public ResponseEntity<List<OrderDto>> getCustomerOrders(@PathVariable int id) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
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
    @PutMapping(value = "/{id}", consumes = API_V1)
    public ResponseEntity<Void> updateCustomer(@RequestBody CustomerDto customerDto, @PathVariable int id) {
        Optional<Customer> optionalCustomer = customerService.findById(id);

        Customer updatedCustomer = CustomerMapper.INSTANCE.mapToCustomer(customerDto);
        if (optionalCustomer.isEmpty()) {
            Customer newCustomer = customerService.insert(updatedCustomer);
            return ResponseEntity
                    .created(linkTo(CustomerController.class).slash(newCustomer.getId()).toUri())
                    .build();
        }
        customerService.update(optionalCustomer.get(), updatedCustomer);
        return ResponseEntity.noContent().build();
    }

    /**
     * A PATCH request performs a partial update to an existing resource. The client specifies the URI for the resource.
     * The request body specifies a set of changes to apply to the resource. This can be more efficient than using PUT,
     * because the client only sends the changes, not the entire representation of the resource.
     * Technically PATCH can also create a new resource (by specifying a set of updates to a "null" resource), if the server supports this.
     */
    @PatchMapping(value = "/{id}", consumes = API_V1)
    public ResponseEntity<Void> patchCustomerMethod1(@RequestBody CustomerDto patchedCustomer, @PathVariable int id) {
        Optional<Customer> optionalCustomer = customerService.findById(id);

        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        final Customer customer = optionalCustomer.get();
        if (StringUtils.hasLength(patchedCustomer.getFirstName())) {
            customer.setFirstName(patchedCustomer.getFirstName());
        }
        if (StringUtils.hasLength(patchedCustomer.getLastName())) {
            customer.setLastName(patchedCustomer.getLastName());
        }
        if (patchedCustomer.getAge() > 0) {
            customer.setAge(patchedCustomer.getAge());
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * PATCH method
     * Content-Type = application/json-patch.v1+json
     * [
     * {
     * "op":"test",
     * "path":"/age",
     * "value": 100
     * },
     * {
     * "op":"replace",
     * "path":"/age",
     * "value":77
     * },
     * {
     * "op":"replace",
     * "path":"/firstName",
     * "value":"Fooo"
     * },
     * {
     * "op":"replace",
     * "path":"/orders/0/price",
     * "value":"444"
     * },
     * {
     * "op":"copy",
     * "from":"/orders/1",
     * "path":"/orders/-"
     * },
     * {
     * "op":"remove",
     * "path":"/orders/1"
     * },
     * {
     * "op":"move",
     * "from":"/orders/0",
     * "path":"/orders/-"
     * },
     * {
     * "op":"copy",
     * "from":"/orders/1",
     * "path":"/orders/-"
     * }
     * ]
     */

    // do we need to version PATCH?!
    @PatchMapping(value = "/{id}", consumes = {APPLICATION_JSON_PATCH_V1_JSON})
    public ResponseEntity<Void> patchCustomerMethod2(@RequestBody JsonPatch patch, @PathVariable int id) {
        Optional<Customer> customer = customerService.findById(id);

        if (customer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            CustomerDto customerDtoPatched = Utils.applyPatchToCustomer(patch, CustomerMapper.INSTANCE.mapToCustomerDto(customer.get()));
            Customer patchedCustomer = CustomerMapper.INSTANCE.mapToCustomer(customerDtoPatched);
            customerService.update(customer.get(), patchedCustomer);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.noContent().build();
    }

}
