package Services;

import Repository.ApplicationsRepository;
import Repository.UsersRepository;

import java.util.Map;

public class DashboardService {
    private UsersRepository usersRepository = new UsersRepository();
    private ApplicationsRepository applicationsRepository = new ApplicationsRepository();

    public Map<String, Integer> getUserStatusCounts() {
        return usersRepository.countUsersByStatus();
    }

    public Map<String, Integer> getApplicationStatusCounts(){
        return applicationsRepository.countApplicationsByStatus();
    }
}
