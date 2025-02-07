package by.baraznov.bookstorageservice.repository;

import by.baraznov.bookstorageservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findByISBNAndDeletedFalse(String ISBN);


    List<Book> findByDeletedFalse();

    Book findByIdAndDeletedFalse(int id);
}
