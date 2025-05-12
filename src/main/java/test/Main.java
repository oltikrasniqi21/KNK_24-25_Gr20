package test;

import CreateDTO.CreateApplicationDto;
import Services.ApplicationService;
import UpdateDTO.UpdateApplicationsDTO;
import models.Applications;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationService service = new ApplicationService();

        CreateApplicationDto dto = new CreateApplicationDto(1,1,1, LocalDate.now(),"pending");
        Applications created = service.createApplication(dto);

        if(created != null){
            System.out.println("Aplikimi u krijua me sukses: " + created.getApplicationId());
        }else{
            System.out.println("Krijimi i aplikimit deshtoi!");
        }

        if(created != null){
            UpdateApplicationsDTO updatedto = new UpdateApplicationsDTO(created.getApplicationId(), "approved");
            Applications update = service.updateApplicationStatus(updatedto);

            if(updatedto != null){
                System.out.println("Statusi u perditesua ne: " + updatedto.getStatus());
            }else{
                System.out.println("Perditesimi deshtoi!!");
            }
        }

        List<Applications> apps = service.getApplicationsByStudentId(1);
        System.out.println("Aplikimet per studentin 1: ");
        for (Applications app : apps){
            System.out.println("Id: " + app.getStudentId() + ", Burse: " + app.getScholarshipId() + ", Status: " + app.getStatus());
        }
    }
}
