package com.zhivkoproject.ZClinic.service;

import com.zhivkoproject.ZClinic.model.binding.NewsAddBindingModel;
import com.zhivkoproject.ZClinic.model.binding.NewsEditBindingModel;
import com.zhivkoproject.ZClinic.model.service.NewsServiceModel;

import java.util.List;

public interface NewsService {
    void addNews(NewsAddBindingModel newsAddBindingModel, String username);

    List<NewsServiceModel> getAllNews();

    NewsServiceModel getNewsById(Long id);

    void deleteById(Long id);

    void editNews(NewsEditBindingModel newsEditBindingModel);

    void initNews();
}
