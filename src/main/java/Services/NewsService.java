package Services;

import CreateDTO.CreateNewsDTO;
import Repository.NewsRepository;
import Repository.ScholarshipTagsRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.News;
import models.ScholarshipTags;

import java.util.List;

public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService() {
        this.newsRepository = new NewsRepository();
    }

    public News publishNews(CreateNewsDTO createNewsDTO) {
        return newsRepository.create(createNewsDTO);
    }

    public List<News> getAllNews() {
        return newsRepository.getAllNews();
    }

    public ObservableList<News> getAllNewsObservable() {
        return FXCollections.observableArrayList(getAllNews());
    }

    public boolean deleteNews(int newsId) {
        return newsRepository.delete(newsId);
    }




}
