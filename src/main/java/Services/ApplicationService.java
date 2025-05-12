package Services;

import CreateDTO.CreateApplicationDto;
import Repository.ApplicationsRepository;
import UpdateDTO.UpdateApplicationsDTO;
import models.Applications;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApplicationService {
    private final ApplicationsRepository repository;

    public ApplicationService() {
        this.repository = new ApplicationsRepository();
    }

    public Applications createApplication(CreateApplicationDto dto){
        if(dto.getApplication_date().isAfter(LocalDate.now())){
            System.out.println("Data e aplikimit nuk mund te jete ne te ardhmen!");
            return null;
        }

        List<Applications> allApps = repository.getAll();

        boolean alreadyApplied = allApps.stream().anyMatch(
                app -> app.getStudentId() == dto.getSid() &&
                        app.getScholarshipId() == dto.getSid());

        if(alreadyApplied){
            System.out.println("Keni aplikuar njehere!!!");
            return null;
        }

        return repository.create(dto);
    }

    public Applications updateApplicationStatus(UpdateApplicationsDTO dto){
        List<String> validStatuses = List.of("Pending", "Approved", "Rejected");
        if (!validStatuses.contains(dto.getStatus())){
            System.out.println("Statusi eshte i pavlefshem!!");
            return null;
        }

        return repository.update(dto);
    }

    public List<Applications> getApplicationsByStudentId(int studentId){
        List<Applications> result = new ArrayList<>();
        List<Applications> allApps = repository.getAll();

        for(Applications app : allApps){
            if(app.getStudentId() == studentId){
                result.add(app);
            }
        }

        return result;
    }

    public Applications getApplicationById(int id){
        return repository.getById(id);
    }
}
