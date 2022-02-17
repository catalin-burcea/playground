package ro.cburcea.playground.spring.rest.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.cburcea.playground.spring.rest.Utils;
import ro.cburcea.playground.spring.rest.domain.Customer;
import ro.cburcea.playground.spring.rest.dtos.CustomerV2Dto;
import ro.cburcea.playground.spring.rest.mapper.CustomerMapper;
import ro.cburcea.playground.spring.rest.service.CustomerService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomerV2Controller {

    private static final String API_V2 = "application/vnd.rest.v2+json";
    private static final String APPLICATION_JSON_PATCH_V2_JSON = "application/json-patch.v2+json";


    @Autowired
    private CustomerService customerService;

    // built-in support for HEAD and OPTIONS for GET methods
    @GetMapping(value = "/customers", produces = API_V2)
    public ResponseEntity<List<CustomerV2Dto>> getAllCustomers() {
        final List<Customer> customers = customerService.findAll();
        return ResponseEntity.ok(CustomerMapper.INSTANCE.mapToCustomersV2Dto(customers));
    }


    @GetMapping(value = "/customers/{id}", produces = API_V2)
    public ResponseEntity<CustomerV2Dto> getCustomerById(@PathVariable int id) {
        return customerService.findById(id)
                .map(customer -> ResponseEntity.ok(CustomerMapper.INSTANCE.mapToCustomerV2Dto(customer)))
                .orElse(ResponseEntity.notFound().build());
    }


    /**
     * POST request creates a resource. The server assigns a URI for the new resource, and returns that URI to the client.
     * In the REST model, you frequently apply POST requests to collections. The new resource is added to the collection.
     * A POST request can also be used to submit data for processing to an existing resource, without any new resource being created.
     */
    @PostMapping(value = "/customers", consumes = API_V2)
    public ResponseEntity<Void> addCustomer(@RequestBody CustomerV2Dto newV2Customer) {
        Optional<Customer> customer = customerService.findById(newV2Customer.getId());

        if (customer.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        customerService.insert(CustomerMapper.INSTANCE.mapToCustomer(newV2Customer));
        return ResponseEntity
                .created(URI.create("/customers/" + newV2Customer.getId()))
                .build();
    }

    @PutMapping(value = "/customers/{id}", consumes = API_V2)
    public ResponseEntity<Void> updateCustomer(@RequestBody CustomerV2Dto customerV2Dto, @PathVariable int id) {
        Optional<Customer> optionalCustomer = customerService.findById(id);

        if (optionalCustomer.isEmpty()) {
            customerService.insert(CustomerMapper.INSTANCE.mapToCustomer(customerV2Dto));
            return ResponseEntity
                    .created(URI.create("/customers/" + customerV2Dto.getId()))
                    .build();
        }
        final Customer customer = optionalCustomer.get();
        customer.setFirstName(customerV2Dto.getName().split(" ")[0]);
        customer.setLastName(customerV2Dto.getName().split(" ")[1]);
        customer.setAge(customerV2Dto.getAge());
        return ResponseEntity.noContent().build();
    }

    /**
     *  PATCH method
     *  Content-Type = application/json-patch.v2+json
     [
     {
     "op":"test",
     "path":"/age",
     "value": 100
     },
     {
     "op":"replace",
     "path":"/age",
     "value":77
     },
     {
     "op":"replace",
     "path":"/name",
     "value":"Fooo Baaar"
     },
     {
     "op":"replace",
     "path":"/orders/0/price",
     "value":"444"
     },
     {
     "op":"copy",
     "from":"/orders/1",
     "path":"/orders/-"
     },
     {
     "op":"remove",
     "path":"/orders/1"
     },
     {
     "op":"move",
     "from":"/orders/0",
     "path":"/orders/-"
     },
     {
     "op":"copy",
     "from":"/orders/1",
     "path":"/orders/-"
     }
     ]
     */

    // do we need to version PATCH?!
    @PatchMapping(value = "/customers/{id}", consumes = {APPLICATION_JSON_PATCH_V2_JSON})
    public ResponseEntity<Void> patchCustomerMethod2(@RequestBody JsonPatch patch, @PathVariable int id) {
        Optional<Customer> optionalCustomer = customerService.findById(id);

        if (optionalCustomer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            CustomerV2Dto customerV2DtoPatched = Utils.applyPatchToCustomerV2(patch, CustomerMapper.INSTANCE.mapToCustomerV2Dto(optionalCustomer.get()));
            customerService.findAll().set(customerService.findAll().indexOf(optionalCustomer.get()), CustomerMapper.INSTANCE.mapToCustomer(customerV2DtoPatched));
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.noContent().build();
    }

}