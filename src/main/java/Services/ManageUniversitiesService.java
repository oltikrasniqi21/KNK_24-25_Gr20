package Services;

import CreateDTO.CreateFacultiesDTO;
import CreateDTO.CreateMajorsDTO;
import CreateDTO.CreateUniversitiesDTO;
import Repository.FacultiesRepository;
import Repository.MajorsRepository;
import Repository.UniversitiesRepository;
import models.Faculties;
import models.Majors;
import models.Universities;

import java.util.List;

public class ManageUniversitiesService {
    private final UniversitiesRepository universitiesRepository = new UniversitiesRepository();
    private final FacultiesRepository facultiesRepository = new FacultiesRepository();
    private final MajorsRepository majorsRepository = new MajorsRepository();

    public List<Universities> getAllUniversities() throws Exception {
        return universitiesRepository.getAll();
    }

    public Universities createUniversity(CreateUniversitiesDTO dto) throws Exception {
        return universitiesRepository.create(dto);
    }

    public boolean deleteUniversity(int universityId) throws Exception {
        List<Faculties> faculties = facultiesRepository.getAll().stream()
                .filter(f -> f.getUniversityId() == universityId).toList();

        for (Faculties faculty : faculties) {
            List<Majors> majors = majorsRepository.getAll().stream()
                    .filter(m -> m.getFacultyId() == faculty.getFacultyId()).toList();

            for (Majors major : majors) {
                majorsRepository.delete(major.getMajorId());
            }
            facultiesRepository.delete(faculty.getFacultyId());
        }

        return universitiesRepository.delete(universityId);
    }

    public List<Faculties> getFacultiesByUniversityId(int universityId) throws Exception {
        return facultiesRepository.getAll().stream()
                .filter(f -> f.getUniversityId() == universityId).toList();
    }

    public Faculties createFaculty(CreateFacultiesDTO dto) throws Exception {
        return facultiesRepository.create(dto);
    }

    public boolean deleteFaculty(int facultyId) throws Exception {
        List<Majors> majors = majorsRepository.getAll().stream()
                .filter(m -> m.getFacultyId() == facultyId).toList();

        for (Majors major : majors) {
            majorsRepository.delete(major.getMajorId());
        }

        return facultiesRepository.delete(facultyId);
    }

    public List<Majors> getMajorsByFacultyId(int facultyId) throws Exception {
        return majorsRepository.getAll().stream()
                .filter(m -> m.getFacultyId() == facultyId).toList();
    }

    public Majors createMajor(CreateMajorsDTO dto) throws Exception {
        return majorsRepository.create(dto);
    }

    public boolean deleteMajor(int majorId) throws Exception {
        return majorsRepository.delete(majorId);
    }
}
