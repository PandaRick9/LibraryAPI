package by.baraznov.bookstorageservice.repository;

import by.baraznov.bookstorageservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findByISBN (String ISBN);
}
