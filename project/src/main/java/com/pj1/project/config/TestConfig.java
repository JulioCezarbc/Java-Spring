package com.pj1.project.config;

import com.pj1.project.entities.*;
import com.pj1.project.entities.enums.OrderStatus;
import com.pj1.project.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void run(String... args) throws Exception {

        Category cat1 = new Category(null, "Electronics");
        Category cat2 = new Category(null, "Books");
        Category cat3 = new Category(null, "Computers");
        Category cat4 = new Category(null, "Pelucia");


        Product p1 = new Product(null, "Harry Potter", "Melhor livro de ficção", 450.00, "");
        Product p2 = new Product(null, "Smart Tv", "Melhor Smart do mercado", 3550.00, "");
        Product p3 = new Product(null, "MacBook", "Notebook ", 5550.00, "");
        Product p4 = new Product(null, "PC Gamer", "PC GAMER BA", 6650.00, "");
        Product p5 = new Product(null, "Lilo", "Pelucia Lilo", 120.00, "");

        categoryRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4));
        productRepository.saveAll(Arrays.asList(p1,p2,p3,p4,p5));


        p1.getCategories().add(cat2);
        p2.getCategories().add(cat1);
        p2.getCategories().add(cat3);
        p3.getCategories().add(cat3);
        p4.getCategories().add(cat3);
        p5.getCategories().add(cat4);

        productRepository.saveAll(Arrays.asList(p1,p2,p3,p4,p5));




        User u1 = new User(null, "Maria", "maria@gmail.com", "98888", "789");
        User u2 = new User(null, "Bob", "bob@gmail.com", "978456", "13456");

        Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), OrderStatus.PAID, u1);
        Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"),OrderStatus.WAITING_PAYMENT, u2);
        Order o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"),OrderStatus.WAITING_PAYMENT, u1);


        userRepository.saveAll(Arrays.asList(u1,u2));
        orderRepository.saveAll(Arrays.asList(o1,o2,o3));

        OrderItem oi1 = new OrderItem(o1,p1,2,p1.getPrice());
        OrderItem oi2 = new OrderItem(o1,p3,1,p4.getPrice());
        OrderItem oi3 = new OrderItem(o2,p3,2,p1.getPrice());
        OrderItem oi4 = new OrderItem(o3,p5,2,p5.getPrice());

        orderItemRepository.saveAll(Arrays.asList(oi1,oi2,oi3,oi4));


        Payment pay1 = new Payment(null,Instant.parse("2019-06-20T21:53:07Z"), o1);
        o1.setPayment(pay1);
        orderRepository.save(o1);



    }
}
