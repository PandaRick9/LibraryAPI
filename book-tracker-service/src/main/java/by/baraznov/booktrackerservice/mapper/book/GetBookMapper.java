package by.baraznov.booktrackerservice.mapper.book;


import by.baraznov.booktrackerservice.dto.GetBookDTO;
import by.baraznov.booktrackerservice.mapper.BaseMapper;
import by.baraznov.booktrackerservice.db1.model.BookInformation;
import org.mapstruct.Mapper;

/**
 * GetBookMapper is a mapper interface for converting between BookInformation entities and GetBookDTOs.
 */
@Mapper(config = BaseMapper.class)
public interface GetBookMapper extends BaseMapper<BookInformation, GetBookDTO> {
}