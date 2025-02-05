package by.baraznov.bookstorageservice.mapper.book;

import by.baraznov.bookstorageservice.dto.GetBookDTO;
import by.baraznov.bookstorageservice.mapper.BaseMapper;
import by.baraznov.bookstorageservice.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapper.class)
public interface GetBookMapper extends BaseMapper<Book, GetBookDTO> {
}
