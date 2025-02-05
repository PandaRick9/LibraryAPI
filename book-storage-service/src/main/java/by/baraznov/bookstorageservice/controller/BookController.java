package by.baraznov.bookstorageservice.controller;

import by.baraznov.bookstorageservice.dto.CreateBookDTO;
import by.baraznov.bookstorageservice.dto.GetBookDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @GetMapping
    public List<GetBookDTO> getAllBooks() {
        return null;
    }

    @PostMapping
    public GetBookDTO create(CreateBookDTO createBookDTO){
        return null;
    }
    @GetMapping("/{id}")
    public GetBookDTO getBookById(@PathVariable int id){
        return null;
    }
    @GetMapping
    public GetBookDTO getBookByISBN(@RequestParam String ISBN){
        return null;
    }
    @PatchMapping("/{id}")
    public GetBookDTO update(@PathVariable int id,CreateBookDTO createBookDTO){
        return null;
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){

    }


}
