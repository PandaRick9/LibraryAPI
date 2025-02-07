package by.baraznov.booktrackerservice.repository;

import by.baraznov.booktrackerservice.model.BookInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;

@Repository
public interface BookInformationRepository extends JpaRepository<BookInformation, Integer> {
    List<BookInformation> findByFreeTrue();
}
