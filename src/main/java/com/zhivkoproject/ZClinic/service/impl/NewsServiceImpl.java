package com.zhivkoproject.ZClinic.service.impl;

import com.zhivkoproject.ZClinic.model.binding.NewsAddBindingModel;
import com.zhivkoproject.ZClinic.model.binding.NewsEditBindingModel;
import com.zhivkoproject.ZClinic.model.binding.NewsSearchBindingModel;
import com.zhivkoproject.ZClinic.model.entity.News;
import com.zhivkoproject.ZClinic.model.entity.User;
import com.zhivkoproject.ZClinic.model.service.NewsServiceModel;
import com.zhivkoproject.ZClinic.model.service.UserServiceModel;
import com.zhivkoproject.ZClinic.repository.NewsRepository;
import com.zhivkoproject.ZClinic.repository.NewsSpecification;
import com.zhivkoproject.ZClinic.service.NewsService;
import com.zhivkoproject.ZClinic.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
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
    @Scheduled(fixedDelay = 100)
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
        News news = newsRepository.findById(id).orElse(null);
        newsRepository.delete(news);
    }

    @Override
    public void editNews(NewsEditBindingModel newsEditBindingModel) {
        News news = modelMapper.map(newsEditBindingModel, News.class);
        news.setCreatedOn(LocalDate.now());

        newsRepository.save(news);
    }

    @Override
    public void initNews() {
        if(newsRepository.count() > 0){
            return;
        }
        //NEWS 1
        News firstNews = new News();
        firstNews.setCreatedOn(LocalDate.now());
        firstNews.setTitle("MONKEYPOX FATALITIES REPORTED");
        firstNews.setContent("Monkeypox is caused by the monkeypox " +
                "virus, a member of the same family of viruses as " +
                "smallpox, although it is much less severe and experts " +
                "say chances of infection are low. It occurs mostly in " +
                "remote parts of central and west African countries, near " +
                "tropical rainforests. In those regions, there have been more" +
                " than 1,200 cases of monkeypox since the start of the year." +
                " Two main strains of the virus - west African and central" +
                " African - are known to exist, and it's the milder one " +
                "from west Africa which is now circulating in other regions " +
                "of the world. The unusually high numbers of people infected" +
                " with monkeypox outside of Africa with no travel links to" +
                " the region, means the virus is now spreading in the " +
                "community. The UK Health Security Agency says anyone " +
                "concerned they could be infected should phone NHS 111 or " +
                "contact their local sexual health clinic, but call or " +
                "email ahead of a visit.");
        firstNews.setImgUrl("https://bit.ly/3bqke4R");


        //NEWS 2
        News secondNews = new News();
        secondNews.setCreatedOn(LocalDate.now());
        secondNews.setTitle("VACCINATED AND TEST POSITIVE?");
        secondNews.setContent("As COVID-19 cases surge, fueled by the highly transmissible" +
                " omicron variant of the coronavirus, " +
                "Americans wonder how to approach the holidays " +
                "and safely travel and gather with friends and family. " +
                "The pace of new cases in the USA is up 41% compared with" +
                " a month ago, according to a USA TODAY analysis of Johns " +
                "Hopkins data. For much of December, cases hovered around " +
                "120,000 but have jumped to more than 130,000 a day. As of" +
                " Saturday, the omicron variant accounted for 73.2% of new" +
                " COVID-19 infections in the nation, the Centers for" +
                " Disease Control and Prevention reported. The week ending" +
                " Dec. 11, it accounted for 12.6% of new cases. ");
        secondNews.setImgUrl("https://bit.ly/3QjBA1R");

        //NEWS 3
        News thirdNews = new News();
        thirdNews.setCreatedOn(LocalDate.now());
        thirdNews.setTitle("BUILDING A HEALTHY AND BALANCED DIET");
        thirdNews.setContent("Make most of your meal vegetables and fruits " +
                "– ½ of your plate. Aim for color and variety, and remember" +
                " that potatoes don’t count as vegetables on the Healthy " +
                "Eating Plate because of their negative impact on blood sugar. " +
                "Go for whole grains – ¼ of your plate. Whole and intact grains—whole " +
                "wheat, barley, wheat berries, quinoa, oats, brown rice, and foods made " +
                "with them, such as whole wheat pasta—have a milder effect on blood sugar " +
                "and insulin than white bread, white rice, and other refined grains. Protein" +
                " power – ¼ of your plate. Fish, poultry, beans, and nuts are all healthy, " +
                "versatile protein sources—they can be mixed into salads, and pair well with" +
                " vegetables on a plate. Limit red meat, and avoid processed meats such as " +
                "bacon and sausage. Healthy plant oils – in moderation. Choose healthy " +
                "vegetable oils like olive, canola, soy, corn, sunflower, peanut, and " +
                "others, and avoid partially hydrogenated oils, which contain unhealthy trans fats. " +
                "Remember that low-fat does not mean “healthy.”");
        thirdNews.setImgUrl("https://bit.ly/3cZCu5p");

        newsRepository.save(firstNews);
        newsRepository.save(secondNews);
        newsRepository.save(thirdNews);

    }

    @Override
    public List<NewsServiceModel>  searchNews(NewsSearchBindingModel newsSearchBindingModel) {
        return this.newsRepository.findAll(new NewsSpecification(newsSearchBindingModel)).
                stream().map(news -> modelMapper.map(news, NewsServiceModel.class)).
                toList();

    }
}
