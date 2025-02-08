package by.baraznov.booktrackerservice.service;

import by.baraznov.booktrackerservice.db1.model.BookInformation;

import java.util.List;

public interface BookInformationService {
    void changeStatus(int id);
    List<BookInformation> findAllFreeBooks();
    BookInformation findOne(int id);
}
