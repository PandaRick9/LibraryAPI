package by.baraznov.booktrackerservice.service;

import by.baraznov.booktrackerservice.model.BookInformation;

import java.util.List;

public interface BookInformationService {
    void changeStatus(int id);
    List<BookInformation> findAllFreeBooks();
}
