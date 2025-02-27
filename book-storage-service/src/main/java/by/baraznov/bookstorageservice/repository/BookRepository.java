package by.baraznov.bookstorageservice.repository;

import by.baraznov.bookstorageservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository for managing Book entities.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    /**
     * Finds a book by its ISBN, excluding deleted books.
     * @param ISBN The ISBN of the book.
     * @return The book entity if found, otherwise null.
     */
    Book findByIsbnAndDeletedFalse(String ISBN);

    /**
     * Retrieves all books that have not been marked as deleted.
     * @return A list of non-deleted books.
     */
    List<Book> findByDeletedFalse();

    /**
     * Finds a book by its ID, excluding deleted books.
     * @param id The ID of the book.
     * @return The book entity if found, otherwise null.
     */
    Book findByIdAndDeletedFalse(int id);
}
