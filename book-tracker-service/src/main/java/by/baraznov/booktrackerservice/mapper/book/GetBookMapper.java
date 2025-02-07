package by.baraznov.booktrackerservice.mapper.book;


import by.baraznov.booktrackerservice.dto.GetBookDTO;
import by.baraznov.booktrackerservice.mapper.BaseMapper;
import by.baraznov.booktrackerservice.model.BookInformation;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapper.class)
public interface GetBookMapper extends BaseMapper<BookInformation, GetBookDTO> {
}
