package com.zhivkoproject.ZClinic.service.impl;

import com.zhivkoproject.ZClinic.model.binding.NewsAddBindingModel;
import com.zhivkoproject.ZClinic.model.binding.NewsEditBindingModel;
import com.zhivkoproject.ZClinic.model.entity.News;
import com.zhivkoproject.ZClinic.model.entity.User;
import com.zhivkoproject.ZClinic.model.service.NewsServiceModel;
import com.zhivkoproject.ZClinic.model.service.UserServiceModel;
import com.zhivkoproject.ZClinic.repository.NewsRepository;
import com.zhivkoproject.ZClinic.service.NewsService;
import com.zhivkoproject.ZClinic.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public NewsServiceImpl(NewsRepository newsRepository, UserService userService, ModelMapper modelMapper) {
        this.newsRepository = newsRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addNews(NewsAddBindingModel newsAddBindingModel, String username) {
        UserServiceModel userServiceModel = userService.findUser(username);
        User user = modelMapper.map(userServiceModel, User.class);

        News news = modelMapper.map(newsAddBindingModel, News.class);
        news.setAddedBy(user);
        news.setCreatedOn(LocalDate.now());

        newsRepository.save(news);

    }

    @Override
    public List<NewsServiceModel> getAllNews() {
        return newsRepository
                .findAll()
                .stream()
                .map(news-> modelMapper.map(news, NewsServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public NewsServiceModel getNewsById(Long id) {

        return newsRepository.findById(id).map(news -> modelMapper.map(news, NewsServiceModel.class)).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public void editNews(NewsEditBindingModel newsEditBindingModel) {
        News news = modelMapper.map(newsEditBindingModel, News.class);
        news.setCreatedOn(LocalDate.now());

        newsRepository.save(news);
    }
}
