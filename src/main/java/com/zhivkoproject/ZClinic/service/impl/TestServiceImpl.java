package com.zhivkoproject.ZClinic.service.impl;

import com.zhivkoproject.ZClinic.model.binding.MedicalTestAddBindingModel;
import com.zhivkoproject.ZClinic.model.entity.Test;
import com.zhivkoproject.ZClinic.model.entity.User;
import com.zhivkoproject.ZClinic.model.enums.CategoryEnum;
import com.zhivkoproject.ZClinic.model.service.MedicalTestServiceModel;
import com.zhivkoproject.ZClinic.model.service.UserServiceModel;
import com.zhivkoproject.ZClinic.repository.TestRepository;
import com.zhivkoproject.ZClinic.service.CategoryService;
import com.zhivkoproject.ZClinic.service.TestService;
import com.zhivkoproject.ZClinic.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
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

    @Override
    public void initTests() {
        if(testRepository.count() > 0){
            return;
        }
        //TEST 1
        Test firstTest = new Test();
        firstTest.setName("SarsCov2");
        firstTest.setOffer(true);
        firstTest.setCategory(categoryService.findCategory(CategoryEnum.VIROLOGY));
        firstTest.setPrice(BigDecimal.valueOf(24.69));
        firstTest.setMaterial("oral");
        User user = modelMapper.map(userService.findUser(userService.findUsernameById(Long.parseLong("1"))),  User.class);
        firstTest.setAddedBy(user);
        testRepository.save(firstTest);

        Test secondTest = new Test();
        secondTest.setName("LTT Soy Food Allergy Test");
        secondTest.setOffer(true);
        secondTest.setCategory(categoryService.findCategory(CategoryEnum.BIOCHEMICAL));
        secondTest.setPrice(BigDecimal.valueOf(95.00));
        secondTest.setMaterial("blood");
        secondTest.setAddedBy(user);
        testRepository.save(secondTest);

        Test thirdTest = new Test();
        thirdTest.setName("LDL");
        thirdTest.setOffer(true);
        thirdTest.setCategory(categoryService.findCategory(CategoryEnum.HEMATOLOGY));
        thirdTest.setPrice(BigDecimal.valueOf(2.90));
        thirdTest.setMaterial("blood");
        thirdTest.setAddedBy(user);
        testRepository.save(thirdTest);

        Test fourthTest = new Test();
        fourthTest.setName("HDL");
        fourthTest.setOffer(true);
        fourthTest.setCategory(categoryService.findCategory(CategoryEnum.HEMATOLOGY));
        fourthTest.setPrice(BigDecimal.valueOf(1.90));
        fourthTest.setMaterial("blood");
        fourthTest.setAddedBy(user);
        testRepository.save(fourthTest);

        Test fifthTest = new Test();
        fifthTest.setName("Triglycerides");
        fifthTest.setOffer(true);
        fifthTest.setCategory(categoryService.findCategory(CategoryEnum.HEMATOLOGY));
        fifthTest.setPrice(BigDecimal.valueOf(1.60));
        fifthTest.setMaterial("blood");
        fifthTest.setAddedBy(user);
        testRepository.save(fifthTest);

        Test sixthTest = new Test();
        sixthTest.setName("Microalbuminuria");
        sixthTest.setOffer(true);
        sixthTest.setCategory(categoryService.findCategory(CategoryEnum.BIOCHEMICAL));
        sixthTest.setPrice(BigDecimal.valueOf(19.85));
        sixthTest.setMaterial("urine");
        sixthTest.setAddedBy(user);
        testRepository.save(sixthTest);



    }


}
