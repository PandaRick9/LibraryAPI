package by.baraznov.booktrackerservice.controller;

import by.baraznov.booktrackerservice.dto.GetBookDTO;
import by.baraznov.booktrackerservice.mapper.book.GetBookMapper;
import by.baraznov.booktrackerservice.service.impl.BookInformationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tracker")
@AllArgsConstructor
public class BookController {
    private final BookInformationServiceImpl bookInformationService;
    private final GetBookMapper getBookMapper;

    @GetMapping
    public List<GetBookDTO> getAllFreeBooks(){
        return getBookMapper.toDtos(bookInformationService.findAllFreeBooks());//TODO test all functions and create functional for kafka
    }

    @PostMapping("/{id}")
    public GetBookDTO setNewStatus(@PathVariable int id){
        bookInformationService.changeStatus(id);
        return null;
    }

}
