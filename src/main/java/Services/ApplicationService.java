package Services;

import CreateDTO.CreateApplicationDto;
import Repository.ApplicationsRepository;
import Repository.ScholarshipsRepository;
import Repository.UsersRepository;
import UpdateDTO.UpdateApplicationsDTO;
import models.Applications;
import models.Scholarships;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApplicationService {
    private final ApplicationsRepository applicationsRepository;
    private final UsersRepository usersRepository;
    private final ScholarshipsRepository scholarshipsRepository;

    public ApplicationService() {
        this.applicationsRepository = new ApplicationsRepository();
        this.usersRepository = new UsersRepository();
        this.scholarshipsRepository = new ScholarshipsRepository();
    }

    public String submitApplication(CreateApplicationDto applicationDto){
        String validationError = validateApplication(applicationDto);
        if (validationError != null){
            return validationError;
        }

        Applications applications = createApplication(applicationDto);
        updateApplicationStatus(new UpdateApplicationsDTO(applications.getApplicationId(), "pending"));
        updateStudentGPA(applicationDto.getSid(), applicationDto.getGpa());

        return null;
    }

    public String validateApplication(CreateApplicationDto dto){

        if (!usersRepository.isValid(dto.getSid())){
            return "Llogaria juaj nuk eshte validuar ende nga administratori.";
        }

        Scholarships scholarships = scholarshipsRepository.getById(dto.getScid());

        if (scholarships == null){
            return "Bursa e zgjedhur nuk ekziston.";
        }

        if(dto.getApplication_date().isAfter(LocalDate.now())){
            return "Data e aplikimit nuk mund te jete ne te ardhmen!";
        }

        if (dto.getGpa() < 6 || dto.getGpa() > 10.0){
            return "GPA duhet te jete midis 6 dhe 10!";
        }

        if (dto.getGpa() < scholarships.getRequired_gpa()){
            return "GPA juaj " + dto.getGpa() + " nuk ploteson kerkesen minimale " + scholarships.getRequired_gpa();
        }

        List<Applications> allApps = applicationsRepository.getAll();

        boolean alreadyApplied = allApps.stream().anyMatch(
                app -> app.getStudentId() == dto.getSid() &&
                        app.getScholarshipId() == dto.getScid());

        if(alreadyApplied){
            return "Keni aplikuar me pare per kete burse!";
        }
        return null;
    }


    public Applications createApplication(CreateApplicationDto dto){
        return applicationsRepository.create(dto);
    }

    public Applications updateApplicationStatus(UpdateApplicationsDTO dto){
        List<String> validStatuses = List.of("pending", "approved", "rejected");
        if (!validStatuses.contains(dto.getStatus())){
            return null;
        }
        return applicationsRepository.update(dto);
    }

    public List<Applications> getApplicationsByStudentId(int studentId){
        List<Applications> result = new ArrayList<>();
        List<Applications> allApps = applicationsRepository.getAll();

        for(Applications app : allApps){
            if(app.getStudentId() == studentId){
                result.add(app);
            }
        }

        return result;
    }

    public Applications getApplicationById(int id){
        return applicationsRepository.getById(id);
    }

    public List<Scholarships> getAllAvailableScholarship(){
        return scholarshipsRepository.getAll();
    }

    private void updateStudentGPA(int studentId, double gpa){
        usersRepository.updateStudentGPA(studentId,gpa);
    }

}
