package Services;

import CreateDTO.CreateApplicationDto;
import Repository.ApplicationsRepository;
import UpdateDTO.UpdateApplicationsDTO;
import models.Applications;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApplicationService {
    private final ApplicationsRepository repository;

    public ApplicationService() {
        this.repository = new ApplicationsRepository();
    }

    public String validateApplication(CreateApplicationDto dto){
        if(dto.getApplication_date().isAfter(LocalDate.now())){
            return "Data e aplikimit nuk mund te jete ne te ardhmen!";
        }

        if (dto.getGpa() < 6.0 || dto.getGpa() > 10.0){
            return "GPA duhet te jete midis 6 dhe 10!";
        }

        List<Applications> allApps = repository.getAll();

        boolean alreadyApplied = allApps.stream().anyMatch(
                app -> app.getStudentId() == dto.getSid() &&
                        app.getScholarshipId() == dto.getScid());

        if(alreadyApplied){
            return "Keni aplikuar me pare per kete burse!";
        }

        return null;
    }

    public Applications createApplication(CreateApplicationDto dto){
        return repository.create(dto);
    }

    public Applications updateApplicationStatus(UpdateApplicationsDTO dto){
        List<String> validStatuses = List.of("pending", "approved", "rejected");
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
