package by.baraznov.bookstorageservice.service.impl;

import by.baraznov.bookstorageservice.dto.UpdateBookDTO;
import by.baraznov.bookstorageservice.mapper.book.UpdateBookMapper;
import by.baraznov.bookstorageservice.model.Book;
import by.baraznov.bookstorageservice.dto.CreateBookDTO;
import by.baraznov.bookstorageservice.dto.GetBookDTO;
import by.baraznov.bookstorageservice.kafka.KafkaProducer;
import by.baraznov.bookstorageservice.mapper.book.CreateBookMapper;
import by.baraznov.bookstorageservice.mapper.book.GetBookMapper;

import by.baraznov.bookstorageservice.repository.BookRepository;
import by.baraznov.bookstorageservice.service.BookService;
import by.baraznov.util.BookAlreadyExists;
import by.baraznov.util.BookNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for managing books.
 */
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final GetBookMapper getBookMapper;
    private final UpdateBookMapper updateBookMapper;
    private final CreateBookMapper createBookMapper;
    private final KafkaProducer kafkaProducer;

    /**
     * Retrieves all books that are not marked as deleted.
     *
     * @return List of books DTO.
     */
    @Override
    public List<GetBookDTO> getAllBooks() {
        return getBookMapper.toDtos(bookRepository.findByDeletedFalse());
    }

    /**
     * Creates a new book entry and sends a Kafka message about the creation.
     *
     * @param createBookDTO DTO containing book details
     * @return the created book DTO
     */
    @Override
    @Transactional
    public GetBookDTO create(CreateBookDTO createBookDTO) {
        Book book = createBookMapper.toEntity(createBookDTO);
        book.setDeleted(false);
        Book bookByISBN = bookRepository.findByIsbnAndDeletedFalse(book.getIsbn());
        if(bookByISBN != null) {
            if (bookByISBN.getIsbn().equals(book.getIsbn()))
                throw new BookAlreadyExists("There is already a book with that isbn: " + book.getIsbn());
        }
        bookRepository.save(book);
        kafkaProducer.sendAddMessage(getBookByISBN(createBookDTO.getIsbn()).getId());
        return getBookMapper.toDto(createBookMapper.toEntity(createBookDTO));
    }

    /**
     * Retrieves a book by its unique ID.
     *
     * @param id book ID
     * @return the corresponding book DTO
     */
    @Override
    public GetBookDTO getBookById(int id) {
        Book book = bookRepository.findByIdAndDeletedFalse(id);
        if(book == null)
            throw new BookNotFound("Book not found with id: " + id);
        return getBookMapper.toDto(bookRepository.findByIdAndDeletedFalse(id));
    }
    /**
     * Retrieves a book by its ISBN.
     *
     * @param ISBN book ISBN
     * @return the corresponding book DTO
     */
    @Override
    public GetBookDTO getBookByISBN(String ISBN) {
        Book book = bookRepository.findByIsbnAndDeletedFalse(ISBN);
        if(book == null)
            throw new BookNotFound("Book not found with isbn: " + ISBN);
        return getBookMapper.toDto(book);
    }

    /**
     * Updates the book details.
     *
     * @param id book ID
     * @param updateBookDTO DTO containing updated details
     * @return the updated book DTO
     */
    @Override
    @Transactional
    public GetBookDTO update(int id, UpdateBookDTO updateBookDTO) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFound("Book not found with id: " + id));
        updateBookMapper.merge(book, updateBookDTO);
        bookRepository.save(book);
        return getBookMapper.toDto(book);
    }
    /**
     * Soft deletes a book by toggling its deleted status and sends a Kafka delete message.
     *
     * @param id book ID
     */
    @Override
    @Transactional
    public void delete(int id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFound("Book not found with id: " + id));
        kafkaProducer.sendDeleteMessage(id);
        book.setDeleted(!book.getDeleted());

        bookRepository.save(book);
    }
}
