package by.baraznov.bookstorageservice.mapper.book;


import by.baraznov.bookstorageservice.dto.UpdateBookDTO;
import by.baraznov.bookstorageservice.mapper.BaseMapper;
import by.baraznov.bookstorageservice.model.Book;
import org.mapstruct.Mapper;

/**
 * UpdateBookMapper is a mapper interface for converting between Book entities and UpdateBookDTOs.
 */
@Mapper(config = BaseMapper.class)
public interface UpdateBookMapper extends BaseMapper<Book, UpdateBookDTO>{
}