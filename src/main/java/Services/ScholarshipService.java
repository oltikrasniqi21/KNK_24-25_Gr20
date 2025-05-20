package Services;

import CreateDTO.CreateScholarshipDTO;
import Exceptions.InvalidFieldException;
import Repository.ScholarshipsRepository;
import UpdateDTO.UpdateScholarshipDTO;
import javafx.scene.control.Alert;
import models.Scholarships;
import utils.AlertMessages;

import java.time.LocalDate;
import java.util.ArrayList;

public class ScholarshipService{
    private final ScholarshipsRepository scholarshipRepository;

    public ScholarshipService(){
        this.scholarshipRepository = new ScholarshipsRepository();
    }

    public Scholarships create(CreateScholarshipDTO scholarshipDTO) throws RuntimeException{
        ArrayList<Scholarships> scholarshipsArray = scholarshipRepository.getAll();

        for(Scholarships scholarship : scholarshipsArray){
            if(scholarship.getScholarship_name() == scholarshipDTO.getScholarship_name()
            && scholarship.getProvider() == scholarshipDTO.getProvider()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Kjo burse ekziston!");
                return null;
            }
        }

        if(scholarshipDTO.getRequired_gpa() > 10.0 || scholarshipDTO.getRequired_gpa() <5.0){
            throw new InvalidFieldException(AlertMessages.GPA);
        }

        if(scholarshipDTO.getDeadline_date().isBefore(LocalDate.now())){
            throw new InvalidFieldException(AlertMessages.DEADLINE);
        }

        if(scholarshipDTO.getRequired_year()<1 || scholarshipDTO.getRequired_year()>6){
            throw new InvalidFieldException(AlertMessages.YEAR);
        }


        System.out.println("Service working...");
        Alert alert = new Alert(Alert.AlertType.INFORMATION, LocaleAlertMessages.getLocalizedMessage(AlertMessages.SUCCESSFUL_ADD_BUNDLE));
        alert.showAndWait();
        return scholarshipRepository.create(scholarshipDTO); //Funksioni create e kthen Scholarships model
    }

    public Scholarships update(UpdateScholarshipDTO scholarshipDTO) throws RuntimeException{
        if(scholarshipDTO.getRequired_gpa() > 10.0 || scholarshipDTO.getRequired_gpa() <5.0){
            throw new InvalidFieldException(AlertMessages.GPA);
        }

        if(scholarshipDTO.getDeadline_date().isBefore(LocalDate.now())){
            throw new InvalidFieldException(AlertMessages.DEADLINE);
        }

        if(scholarshipDTO.getRequired_year()<1 || scholarshipDTO.getRequired_year()>6){
            throw new InvalidFieldException(AlertMessages.YEAR);
        }


        System.out.println("Service Update working...");
        Alert alert = new Alert(Alert.AlertType.INFORMATION, LocaleAlertMessages.getLocalizedMessage(AlertMessages.SUCCESSFUL_EDIT_BUNDLE));
        alert.showAndWait();
        return scholarshipRepository.update(scholarshipDTO); //Funksioni update e kthen Scholarships model
    }

}
