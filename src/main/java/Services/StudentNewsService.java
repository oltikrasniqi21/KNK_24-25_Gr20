package Services;

import Repository.NewsRepository;
import models.News;

import java.util.List;

public class StudentNewsService {

    private final NewsRepository newsRepository = new NewsRepository();

    public List<News> getAllNews() {
        return newsRepository.getAllNews();
    }
}
