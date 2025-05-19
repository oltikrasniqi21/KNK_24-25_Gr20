package Services;

import Repository.FaqRepository;
import CreateDTO.CreateFaqDTO;
import UpdateDTO.UpdateFaqDTO;
import models.Faq;

import java.util.List;

public class FaqService {
    private final FaqRepository faqRepository = new FaqRepository();

    public List<Faq> getAllFaqs() {
        return faqRepository.getAll();
    }

    public Faq createFaq(String question, String answer) {
        return faqRepository.create(new CreateFaqDTO(question, answer));
    }

    public Faq updateFaq(int id, String question, String answer) {
        return faqRepository.update(new UpdateFaqDTO(id, question, answer));
    }

    public boolean deleteFaq(int id) {
        return faqRepository.delete(id);
    }
}
