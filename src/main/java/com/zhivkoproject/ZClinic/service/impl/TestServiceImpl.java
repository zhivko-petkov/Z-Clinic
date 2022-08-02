package com.zhivkoproject.ZClinic.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhivkoproject.ZClinic.model.binding.MedicalTestAddBindingModel;
import com.zhivkoproject.ZClinic.model.entity.Test;
import com.zhivkoproject.ZClinic.model.entity.User;
import com.zhivkoproject.ZClinic.model.service.MedicalTestServiceModel;
import com.zhivkoproject.ZClinic.model.service.UserServiceModel;
import com.zhivkoproject.ZClinic.repository.TestRepository;
import com.zhivkoproject.ZClinic.service.CategoryService;
import com.zhivkoproject.ZClinic.service.TestService;
import com.zhivkoproject.ZClinic.service.UserService;
import io.joshworks.restclient.http.MediaType;
import io.joshworks.restclient.http.Unirest;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final CategoryService categoryService;
    private final UserDetailsService userDetailsService;
    private final ModelMapper modelMapper;
    private final UserService userService;


    public TestServiceImpl(TestRepository testRepository, CategoryService categoryService, UserDetailsService userDetailsService, ModelMapper modelMapper, UserService userService) {
        this.testRepository = testRepository;
        this.categoryService = categoryService;
        this.userDetailsService = userDetailsService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public List<MedicalTestServiceModel> getAllMedicalTests() {
        return testRepository
                .findAllByOffer(true)
                .stream()
                .map(test -> modelMapper.map(test, MedicalTestServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addMedicalTest(MedicalTestAddBindingModel medicalTestAddBindingModel) {
        Test currentTest = modelMapper.map(medicalTestAddBindingModel, Test.class);
        UserServiceModel userServiceModel = userService.findUser(medicalTestAddBindingModel.getAddedByUsername());
        currentTest.setAddedBy(modelMapper.map(userServiceModel, User.class));
        currentTest.setCategory(categoryService.findCategory(medicalTestAddBindingModel.getCategory()));
        currentTest.setOffer(true);
        testRepository.save(currentTest);
    }


    @Override
    public void deleteTest(Long id) {
        Test test = testRepository.findById(id).orElse(null);
        test.setOffer(false);
        testRepository.save(test);
    }

    @Override
    public MedicalTestServiceModel findTestById(Long id) {
        Test test = testRepository.findById(id).orElse(null);
        MedicalTestServiceModel medicalTestServiceModel = modelMapper.map(test, MedicalTestServiceModel.class);

        return medicalTestServiceModel;
    }

    @Override
    public void editTest(MedicalTestAddBindingModel medicalTestEditBindingModel, Long id, String username) {
        //get old test
        Test oldTest = testRepository.findById(id).orElse(null);
        oldTest.setOffer(false);
        String oldName = oldTest.getName();
        oldTest.setName(oldName);
        testRepository.save(oldTest);

        /*
        add new test which is same as old, but is edited and actual now
        if I save old test as edit I will have inconsistency orders
        */

        Test test = modelMapper.map(medicalTestEditBindingModel, Test.class);
        test.setCategory(categoryService.findCategory(medicalTestEditBindingModel.getCategory()));
        test.setOffer(true);
        test.setAddedBy(modelMapper.map(userService.findUser(username), User.class));
        testRepository.save(test);
    }

    @Override
    public boolean isUniqueName(String name) {
        Test test = testRepository.findByName(name).orElse(null);
        if (test != null){
            return false;
        }
        return true;
    }


}
