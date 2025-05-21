package Services;

import Repository.FeedbackRepository;
import UpdateDTO.UpdateFeedbackDTO;
import models.Feedback;

import java.util.List;

public class FeedbackService {

    private final FeedbackRepository feedbackRepository = new FeedbackRepository();

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public Feedback submitResponse(UpdateFeedbackDTO dto) {
        return feedbackRepository.update(dto);
    }
}
