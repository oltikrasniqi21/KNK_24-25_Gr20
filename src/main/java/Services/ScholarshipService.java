package Services;

import CreateDTO.CreateScholarshipDTO;
import Exceptions.InvalidFieldException;
import Exceptions.LocaleMessages;
import Repository.ScholarshipsRepository;
import javafx.scene.control.Alert;
import models.Scholarships;

import java.time.LocalDate;
import java.util.ArrayList;

public class ScholarshipService{
    private ScholarshipsRepository scholarshipRepository;
    private CreateScholarshipDTO scholarshipDTO;

    public ScholarshipService(){
        this.scholarshipRepository = new ScholarshipsRepository();
    }

    public Scholarships create(CreateScholarshipDTO scholarshipDTO) throws RuntimeException{
        ArrayList<Scholarships> scholarshipsArray = new ArrayList<>();
        scholarshipsArray = scholarshipRepository.getAll();

        for(Scholarships scholarship : scholarshipsArray){
            if(scholarship.getScholarship_name() == scholarshipDTO.getScholarship_name()
            && scholarship.getProvider() == scholarshipDTO.getProvider()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Kjo burse ekziston!");
                return null;
            }
        }

        if(scholarshipDTO.getRequired_gpa() > 10.0 || scholarshipDTO.getRequired_gpa() <5.0){
            throw new InvalidFieldException(LocaleMessages.GPA);
        }

        if(scholarshipDTO.getDeadline_date().isBefore(LocalDate.now())){
            throw new InvalidFieldException(LocaleMessages.DEADLINE);
        }



        System.out.println("Service working...");
        Alert alert = new Alert(Alert.AlertType.INFORMATION, LocaleMessages.SUCESSFUL_ADD_BUNDLE);
        alert.showAndWait();
        return scholarshipRepository.create(scholarshipDTO); //Funksioni create e kthen Scholarships model
    }

//    private boolean isYearValid(){
//        if(scholarshipDTO.getRequired_year()<1 || scholarshipDTO.getRequired_year()>6){
//            throw new InvalidFieldException(LocaleMessages.YEAR);
//        }
//    }
}
