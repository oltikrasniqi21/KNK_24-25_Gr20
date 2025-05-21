package Services;

import Repository.UsersRepository;
import models.Students;

import java.util.List;

public class StudentListService {
    private final UsersRepository usersRepository;

    public StudentListService() {
        this.usersRepository = new UsersRepository();
    }

    public List<Students> getAllStudents() {
        return usersRepository.getAllStudents();
    }

    public List<Students> searchStudents(String searchTerm) {
        return usersRepository.searchStudents(searchTerm);
    }
}