package by.baraznov.booktrackerservice.repository;

import by.baraznov.booktrackerservice.model.BookInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing BookInformation entities.
 */
@Repository
public interface BookInformationRepository extends JpaRepository<BookInformation, Integer> {

    /**
     * Retrieves all books that are available (status is true) and not deleted.
     * @return A list of available books.
     */
    List<BookInformation> findByStatusTrueAndDeletedFalse();
}