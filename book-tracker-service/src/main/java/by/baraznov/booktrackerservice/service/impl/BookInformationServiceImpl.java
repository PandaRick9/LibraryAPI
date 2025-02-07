package by.baraznov.booktrackerservice.service.impl;

import by.baraznov.booktrackerservice.model.BookInformation;
import by.baraznov.booktrackerservice.repository.BookInformationRepository;
import by.baraznov.booktrackerservice.service.BookInformationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookInformationServiceImpl implements BookInformationService {
    private final BookInformationRepository bookInformationRepository;

    @Override
    public List<BookInformation> findAllFreeBooks() {
        return bookInformationRepository.findByFreeTrue();
    }
    @Override
    public void changeStatus(int id) {
        BookInformation book =  bookInformationRepository.findById(id).orElse(null);
        if(book != null) {
            book.setFree(false);
            bookInformationRepository.save(book);
        }
    }


}
