package by.baraznov.bookstorageservice.service.impl;

import by.baraznov.bookstorageservice.db1.model.Book;
import by.baraznov.bookstorageservice.dto.CreateBookDTO;
import by.baraznov.bookstorageservice.dto.GetBookDTO;
import by.baraznov.bookstorageservice.kafka.KafkaProducer;
import by.baraznov.bookstorageservice.mapper.book.CreateBookMapper;
import by.baraznov.bookstorageservice.mapper.book.GetBookMapper;

import by.baraznov.bookstorageservice.db1.repository.BookRepository;
import by.baraznov.bookstorageservice.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing books.
 */
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final GetBookMapper getBookMapper;
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
    public GetBookDTO create(CreateBookDTO createBookDTO) {
        Book book = createBookMapper.toEntity(createBookDTO);
        book.setDeleted(false);
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
        return getBookMapper.toDto(bookRepository.findByIsbnAndDeletedFalse(ISBN));
    }

    /**
     * Updates the book details.
     *
     * @param id book ID
     * @param createBookDTO DTO containing updated details
     * @return the updated book DTO
     */
    @Override
    public GetBookDTO update(int id, CreateBookDTO createBookDTO) {
        Book book = bookRepository.findById(id).orElse(null);
        createBookMapper.merge(book, createBookDTO);
        bookRepository.save(book);
        return getBookMapper.toDto(book);
    }
    /**
     * Soft deletes a book by toggling its deleted status and sends a Kafka delete message.
     *
     * @param id book ID
     */
    @Override
    public void delete(int id) {
        kafkaProducer.sendDeleteMessage(id);
        Book book = bookRepository.findById(id).orElse(null);
        book.setDeleted(!book.getDeleted());
        bookRepository.save(book);
    }
}
