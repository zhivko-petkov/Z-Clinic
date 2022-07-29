package com.zhivkoproject.ZClinic.web;

import com.zhivkoproject.ZClinic.config.FileUploadUtil;
import com.zhivkoproject.ZClinic.model.binding.MedicalTestAddBindingModel;
import com.zhivkoproject.ZClinic.model.binding.UserAddBindingModel;
import com.zhivkoproject.ZClinic.model.binding.UserEditBindingModel;
import com.zhivkoproject.ZClinic.model.entity.UserRole;
import com.zhivkoproject.ZClinic.model.enums.UserRoleEnum;
import com.zhivkoproject.ZClinic.model.service.MedicalTestServiceModel;
import com.zhivkoproject.ZClinic.service.CartService;
import com.zhivkoproject.ZClinic.service.OrderService;
import com.zhivkoproject.ZClinic.service.ResultService;
import com.zhivkoproject.ZClinic.service.TestService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/tests")
public class TestController {
    private final TestService testService;
    private final ResultService resultService;
    private final OrderService orderService;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    public TestController(TestService testService,
                          ResultService resultService,
                          OrderService orderService,
                          CartService cartService,
                          ModelMapper modelMapper) {
        this.testService = testService;
        this.resultService = resultService;
        this.orderService = orderService;
        this.cartService = cartService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public String index(@AuthenticationPrincipal UserDetails userDetails,
                        Model model, Principal principal){

        List<MedicalTestServiceModel> getAllTests = testService.getAllMedicalTests();
        model.addAttribute("getAllMedicalTests", getAllTests);

        return "examination-index";

    }

    @ModelAttribute("medicalTestAddBindingModel")
    public MedicalTestAddBindingModel medicalTestAddBindingModel() {
        return new MedicalTestAddBindingModel();
    }

    @GetMapping("/add")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_DOCTOR')")
    public String add(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if(!model.containsAttribute("isTestNameUnique")) {
            model.addAttribute("isTestNameUnique", true);
        }
        return "examination-add";
    }


    @PostMapping("/add")
    //@PostAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_DOCTOR')")
    public String add(@AuthenticationPrincipal UserDetails userDetails,
                      @Valid MedicalTestAddBindingModel medicalTestAddBindingModel,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {
        String username = userDetails.getUsername();
        boolean isNameUnique = testService.isUniqueName(medicalTestAddBindingModel.getName());

        medicalTestAddBindingModel.setAddedByUsername(username);


        if (bindingResult.hasErrors() || !isNameUnique) {
            redirectAttributes.addFlashAttribute("medicalTestAddBindingModel", medicalTestAddBindingModel);
            if (isNameUnique){
                redirectAttributes.addFlashAttribute("isTestNameUnique", true);
            } else {
                redirectAttributes.addFlashAttribute("isTestNameUnique", false);
            }
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.medicalTestAddBindingModel",
                    bindingResult);
            return "redirect:/tests/add";
        }

        this.testService.addMedicalTest(medicalTestAddBindingModel);

        return "redirect:/tests";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         Principal principal){

        orderService.deleteResultInOrders(id);
        cartService.deleteTest(id);
        testService.deleteTest(id);

        return "redirect:/tests";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       Principal principal,
                       Model model){

        MedicalTestAddBindingModel medicalTestEditBindingModel = modelMapper.map(testService.findTestById(id), MedicalTestAddBindingModel.class);

        if(!model.containsAttribute("isTestNameUnique")) {
            model.addAttribute("isTestNameUnique", true);
        }

        model.addAttribute("medicalTestEditBindingModel", medicalTestEditBindingModel);
        model.addAttribute("testId", id);

        return "examination-edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @Valid MedicalTestAddBindingModel medicalTestEditBindingModel,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       @AuthenticationPrincipal UserDetails userDetails){

        MedicalTestServiceModel medicalTestServiceModel = testService.findTestById(id);
        boolean isNameUnique;

        if (!medicalTestServiceModel.getName().equals(medicalTestEditBindingModel.getName())){
            isNameUnique = testService.isUniqueName(medicalTestEditBindingModel.getName());
        } else {
            isNameUnique = true;
        }

        if (bindingResult.hasErrors() || !isNameUnique) {
            redirectAttributes.addFlashAttribute("medicalTestEditBindingModel", medicalTestEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.medicalTestEditBindingModel",
                    bindingResult);
            if (isNameUnique){
                redirectAttributes.addFlashAttribute("isTestNameUnique", true);
            } else {
                redirectAttributes.addFlashAttribute("isTestNameUnique", false);
            }
            return "redirect:/tests/edit/{id}";
        }

        testService.editTest(medicalTestEditBindingModel, id, userDetails.getUsername());

        return "redirect:/tests";
    }

}
