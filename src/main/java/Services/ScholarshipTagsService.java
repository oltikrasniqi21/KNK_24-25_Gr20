package Services;

import Repository.ScholarshipTagsRepository;
import models.ScholarshipTags;

import java.util.List;

public class ScholarshipTagsService {
    private final ScholarshipTagsRepository scholarshipTagsRepository = new ScholarshipTagsRepository();

    public List<ScholarshipTags> getAllScholarshipTags() {
        return scholarshipTagsRepository.getAll();
    }

    public String getTagNameById(int tagId) {
        ScholarshipTags tag = scholarshipTagsRepository.getById(tagId);
        if (tag == null) {return "Unknown";}
        return tag.getTagName();
    }
}