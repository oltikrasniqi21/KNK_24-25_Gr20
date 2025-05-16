package test;

import CreateDTO.CreateScholarshipDTO;
import Repository.ScholarshipsRepository;
import UpdateDTO.UpdateScholarshipDTO;

import java.sql.Date;
import java.time.LocalDate;

public class OltiTest {
    public static void main(String[] args) {
        ScholarshipsRepository scholarshipsRepository = new ScholarshipsRepository();
        CreateScholarshipDTO scholarshipDTO = new CreateScholarshipDTO(
                "STEM","MASHT",1000, LocalDate.of(2025,5,19)
                ,8.0, 2, "Mjeksi");

        scholarshipsRepository.create(scholarshipDTO);
        UpdateScholarshipDTO updateScholarshipDTO = new UpdateScholarshipDTO(
                2,1200, LocalDate.of(2025,10,20),9.2,2,"FIEK"
        );

        scholarshipsRepository.update(updateScholarshipDTO);
        scholarshipsRepository.delete(2);
    }
}
