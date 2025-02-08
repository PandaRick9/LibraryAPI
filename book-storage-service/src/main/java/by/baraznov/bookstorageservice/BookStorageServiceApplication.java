package by.baraznov.bookstorageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class BookStorageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookStorageServiceApplication.class, args);
    }

}
