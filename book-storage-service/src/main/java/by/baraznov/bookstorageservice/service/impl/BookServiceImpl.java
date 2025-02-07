package by.baraznov.bookstorageservice.service.impl;

import by.baraznov.bookstorageservice.dto.CreateBookDTO;
import by.baraznov.bookstorageservice.dto.GetBookDTO;
import by.baraznov.bookstorageservice.kafka.KafkaProducer;
import by.baraznov.bookstorageservice.mapper.book.CreateBookMapper;
import by.baraznov.bookstorageservice.mapper.book.GetBookMapper;
import by.baraznov.bookstorageservice.model.Book;
import by.baraznov.bookstorageservice.repository.BookRepository;
import by.baraznov.bookstorageservice.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final GetBookMapper getBookMapper;
    private final CreateBookMapper createBookMapper;
    private final KafkaProducer kafkaProducer;

    @Override
    public List<GetBookDTO> getAllBooks() {
        return getBookMapper.toDtos(bookRepository.findByDeletedFalse());
    }

    @Override
    public GetBookDTO create(CreateBookDTO createBookDTO) {
        Book book = createBookMapper.toEntity(createBookDTO);
        book.setDeleted(false);
        bookRepository.save(book);
        kafkaProducer.sendAddMessage(getBookByISBN(createBookDTO.getISBN()).getId());
        return getBookMapper.toDto(createBookMapper.toEntity(createBookDTO));
    }

    @Override
    public GetBookDTO getBookById(int id) {
        return getBookMapper.toDto(bookRepository.findByIdAndDeletedFalse(id));
    }

    @Override
    public GetBookDTO getBookByISBN(String ISBN) {
        return getBookMapper.toDto(bookRepository.findByISBNAndDeletedFalse(ISBN));
    }

    @Override
    public GetBookDTO update(int id, CreateBookDTO createBookDTO) {
        Book book = bookRepository.findById(id).orElse(null);
        createBookMapper.merge(book, createBookDTO);
        bookRepository.save(book);
        return getBookMapper.toDto(book);
    }

    @Override
    public void delete(int id) {
        kafkaProducer.sendDeleteMessage(id);
        Book book = bookRepository.findById(id).orElse(null);
        book.setDeleted(!book.getDeleted());
        bookRepository.save(book);
    }
}
