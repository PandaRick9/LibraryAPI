package by.baraznov.bookstorageservice.mapper.book;

import by.baraznov.bookstorageservice.dto.CreateBookDTO;
import by.baraznov.bookstorageservice.mapper.BaseMapper;
import by.baraznov.bookstorageservice.db1.model.Book;
import org.mapstruct.Mapper;

/**
 * CreateBookMapper is a mapper interface for converting between Book entities and CreateBookDTOs.
 */
@Mapper(config = BaseMapper.class)
public interface CreateBookMapper extends BaseMapper<Book, CreateBookDTO>{
}