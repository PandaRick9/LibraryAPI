package by.baraznov.booktrackerservice.service.impl;

import by.baraznov.booktrackerservice.model.BookInformation;
import by.baraznov.booktrackerservice.repository.BookInformationRepository;
import by.baraznov.booktrackerservice.service.BookInformationService;
import by.baraznov.booktrackerservice.util.BookNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for managing book information.
 */
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class BookInformationServiceImpl implements BookInformationService {

    private final BookInformationRepository bookInformationRepository;

    /**
     * Retrieves all available (non-deleted) books with a free status.
     * @return A list of books that are free and not marked as deleted.
     */
    @Override
    public List<BookInformation> findAllFreeBooks() {
        return bookInformationRepository.findByStatusTrueAndDeletedFalse();
    }

    /**
     * Finds a book by its ID.
     * @param id The ID of the book to retrieve.
     * @return The found book or null if no book with the given ID exists.
     */
    @Override
    public BookInformation findOne(int id) {
        return bookInformationRepository.findById(id).orElseThrow(() -> new BookNotFound("Book not found with id: " + id));
    }

    /**
     * Change the status of a book between available and unavailable.
     * @param id The ID of the book whose status needs to be changed.
     */
    @Override
    @Transactional
    public void changeStatus(int id) {
        BookInformation book = bookInformationRepository.findById(id).orElseThrow(() -> new BookNotFound("Book not found with id: " + id));
        if (book != null) {
            book.setStatus(!book.getStatus());
            bookInformationRepository.save(book);
        }
    }
}