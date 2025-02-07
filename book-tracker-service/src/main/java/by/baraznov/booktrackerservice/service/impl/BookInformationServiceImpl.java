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
        return bookInformationRepository.findByStatusTrueAndDeletedFalse();
    }

    @Override
    public BookInformation findOne(int id) {
        return bookInformationRepository.findById(id).orElse(null);
    }

    @Override
    public void changeStatus(int id) {
        BookInformation book =  bookInformationRepository.findById(id).orElse(null);
        if(book != null) {
            book.setStatus(!book.getStatus());
            bookInformationRepository.save(book);
        }
    }


}
