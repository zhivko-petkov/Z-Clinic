package com.zhivkoproject.ZClinic.web;

import com.zhivkoproject.ZClinic.model.binding.NewsAddBindingModel;
import com.zhivkoproject.ZClinic.model.binding.NewsEditBindingModel;
import com.zhivkoproject.ZClinic.model.binding.NewsSearchBindingModel;
import com.zhivkoproject.ZClinic.model.service.NewsServiceModel;
import com.zhivkoproject.ZClinic.service.NewsService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;
    private final ModelMapper modelMapper;

    public NewsController(NewsService newsService, ModelMapper modelMapper) {
        this.newsService = newsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public String index(Model model) {
        List<NewsServiceModel> news = newsService.getAllNews();
        model.addAttribute("allNews", news);
        return "news-index";
    }

    @GetMapping("/{id}")
    public String home(@PathVariable Long id, Model model){
        NewsServiceModel news = newsService.getNewsById(id);
        model.addAttribute("news", news);
        return "news-home";
    }

    @ModelAttribute("newsAddBindingModel")
    public NewsAddBindingModel newsAddBindingModel() {
        return new NewsAddBindingModel();
    }


    @GetMapping("/add")
    public String add(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        return "news-add";
    }

    @PostMapping("/add")
    public String add(@AuthenticationPrincipal UserDetails userDetails,
                      @Valid NewsAddBindingModel newsAddBindingModel,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("newsAddBindingModel", newsAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newsAddBindingModel",
                    bindingResult);
            return "redirect:/news/add";
        }

        this.newsService.addNews(newsAddBindingModel, userDetails.getUsername());

        return "redirect:/news";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         Model model){
        newsService.deleteById(id);

        return "news-index";

    }

    @ModelAttribute("newsEditBindingModel")
    public NewsEditBindingModel newsEditBindingModel() {
        return new NewsEditBindingModel();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       Model model){

        NewsEditBindingModel newsEditBindingModel = modelMapper.map(newsService.getNewsById(id), NewsEditBindingModel.class);

        model.addAttribute("newsId", id);
        model.addAttribute("newsEditBindingModel", newsEditBindingModel);

        return "news-edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @Valid NewsEditBindingModel newsEditBindingModel,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       @AuthenticationPrincipal UserDetails userDetails){
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("newsEditBindingModel", newsEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.newsEditBindingModel",
                    bindingResult);
            return "redirect:/news/edit/{id}";
        }
        newsEditBindingModel.setId(id);
        newsService.editNews(newsEditBindingModel);
        return "redirect:/news";

    }

    @GetMapping("/search")
    public String searchQuery(@Valid NewsSearchBindingModel newsSearchBindingModel,
                              BindingResult bindingResult,
                              Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("newsSearchBindingModel", newsSearchBindingModel);
            model.addAttribute("org.springframework.validation.BindingResult.newsSearchBindingModel",
                    bindingResult);
            return "news-search";
        }

        if (!model.containsAttribute("newsSearchBindingModel")) {
            model.addAttribute("newsSearchBindingModel", newsSearchBindingModel);
        }

        if (!newsSearchBindingModel.isEmpty()) {
            model.addAttribute("news", newsService.searchNews(newsSearchBindingModel));
        }

        return "news-search";

    }




}
