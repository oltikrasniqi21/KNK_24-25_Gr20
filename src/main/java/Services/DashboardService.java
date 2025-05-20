package Services;

import Repository.ApplicationsRepository;
import Repository.ScholarshipsRepository;
import Repository.UsersRepository;

import java.util.Map;

public class DashboardService {
    private UsersRepository usersRepository = new UsersRepository();
    private ApplicationsRepository applicationsRepository = new ApplicationsRepository();
    private ScholarshipsRepository scholarshipsRepository = new ScholarshipsRepository();

    public Map<String, Integer> getUserStatusCounts() {
        return usersRepository.countUsersByStatus();
    }

    public Map<String, Integer> getApplicationStatusCounts(){
        return applicationsRepository.countApplicationsByStatus();
    }

    public Map<String, Integer> getUserRoleCounts(){
        return usersRepository.countUsersByRole();
    }
    public int getUserCount(){
        return usersRepository.getUserCount();
    }
    public int getScholarshipCount(){
        return scholarshipsRepository.getRowCount();
    }

    public int getApplicationCount() {
        return applicationsRepository.getRowCount();
    }

    public void getCurrentUserName(){
        //e boj logjiken dikur
    }
}
