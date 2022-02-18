package ro.cburcea.playground.spring.rest;

import org.springframework.stereotype.Component;
import ro.cburcea.playground.spring.rest.domain.Customer;
import ro.cburcea.playground.spring.rest.domain.Director;
import ro.cburcea.playground.spring.rest.domain.Movie;
import ro.cburcea.playground.spring.rest.domain.Order;
import ro.cburcea.playground.spring.rest.repository.CustomerRepository;
import ro.cburcea.playground.spring.rest.repository.DirectorRepository;
import ro.cburcea.playground.spring.rest.repository.MovieRepository;
import ro.cburcea.playground.spring.rest.repository.OrderRepository;

import javax.annotation.PostConstruct;
import java.util.Random;

@Component
public class AppStartupRunner {

    private final OrderRepository orderRepository;
    private final MovieRepository movieRepository;
    private final CustomerRepository customerRepository;
    private final DirectorRepository directorRepository;

    public AppStartupRunner(OrderRepository orderRepository, MovieRepository movieRepository,
                            CustomerRepository customerRepository, DirectorRepository directorRepository) {
        this.orderRepository = orderRepository;
        this.movieRepository = movieRepository;
        this.customerRepository = customerRepository;
        this.directorRepository = directorRepository;
    }

    @PostConstruct
    public void init() {

        for (int i = 1; i < 20; i++) {
            Customer customer = new Customer("firstName" + new Random().nextInt(), "lastName" + new Random().nextInt(), new Random().nextInt());
            customerRepository.save(customer);
            final Order order1 = new Order(new Random().nextInt(), new Random().nextInt());
            final Order order2 = new Order(new Random().nextInt(), new Random().nextInt());
            orderRepository.save(order1);
            orderRepository.save(order2);
            customer.addOrder(order1);
            customer.addOrder(order2);
            customerRepository.save(customer);
        }


        for (int i = 1; i < 100; i++) {
            Director director = new Director("firstName" + new Random().nextInt(), "lastName" + new Random().nextInt(), 1950 + i);
            directorRepository.save(director);
            final Movie movie1 = new Movie("Title" + new Random().nextInt(), 1980 + i, new Random().nextInt());
            final Movie movie2 = new Movie("Title" + new Random().nextInt(), 1980 + i, new Random().nextInt());
            movieRepository.save(movie1);
            movieRepository.save(movie2);
            director.addMovie(movie1);
            director.addMovie(movie2);
            directorRepository.save(director);
        }

    }
}