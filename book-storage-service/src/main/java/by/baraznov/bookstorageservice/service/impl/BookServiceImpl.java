package by.baraznov.bookstorageservice.service.impl;

import by.baraznov.bookstorageservice.dto.CreateBookDTO;
import by.baraznov.bookstorageservice.dto.GetBookDTO;
import by.baraznov.bookstorageservice.mapper.book.CreateBookMapper;
import by.baraznov.bookstorageservice.mapper.book.GetBookMapper;
import by.baraznov.bookstorageservice.model.Book;
import by.baraznov.bookstorageservice.repository.BookRepository;
import by.baraznov.bookstorageservice.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final GetBookMapper getBookMapper;
    private final CreateBookMapper createBookMapper;

    @Override
    public List<GetBookDTO> getAllBooks() {
        return getBookMapper.toDtos(bookRepository.findAll());
    }

    @Override
    public GetBookDTO create(CreateBookDTO createBookDTO) {
        bookRepository.save(createBookMapper.toEntity(createBookDTO));
        return getBookMapper.toDto(createBookMapper.toEntity(createBookDTO));
    }

    @Override
    public GetBookDTO getBookById(int id) {
        return getBookMapper.toDto(bookRepository.findById(id).orElse(null));
    }

    @Override
    public GetBookDTO getBookByISBN(String ISBN) {
        return getBookMapper.toDto(bookRepository.findByISBN(ISBN));
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
        bookRepository.delete(bookRepository.findById(id).orElse(null));
    }
}
