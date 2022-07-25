package com.zhivkoproject.ZClinic.web;

import com.zhivkoproject.ZClinic.model.binding.MedicalTestAddBindingModel;
import com.zhivkoproject.ZClinic.model.entity.UserRole;
import com.zhivkoproject.ZClinic.model.enums.UserRoleEnum;
import com.zhivkoproject.ZClinic.model.service.MedicalTestServiceModel;
import com.zhivkoproject.ZClinic.service.TestService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/tests")
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping()
    public String index(@AuthenticationPrincipal UserDetails userDetails,
                        Model model){

        List<MedicalTestServiceModel> getAllTests = testService.getAllMedicalTests();
       /* UserRole admin = new UserRole();
        admin.setUserRole(UserRoleEnum.ADMIN);

        UserRole moderator = new UserRole();
        moderator.setUserRole(UserRoleEnum.MODERATOR);

        UserRole doctor = new UserRole();
        doctor.setUserRole(UserRoleEnum.DOCTOR);
*/
        model.addAttribute("getAllMedicalTests", getAllTests);


        return "examination-index";

    }


    @GetMapping("/add")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_DOCTOR')")
    public String add(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        return "examination-add";
    }

    @ModelAttribute("medicalTestAddBindingModel")
    public MedicalTestAddBindingModel medicalTestAddBindingModel() {
        return new MedicalTestAddBindingModel();
    }

    @PostMapping("/add")
    @PostAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_DOCTOR')")
    public String add(@AuthenticationPrincipal UserDetails userDetails,
                      @Valid MedicalTestAddBindingModel medicalTestAddBindingModel,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes) {
        String username = userDetails.getUsername();

        medicalTestAddBindingModel.setAddedByUsername(username);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("medicalTestAddBindingModel", medicalTestAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.medicalTestAddBindingModel",
                    bindingResult);
            return "redirect:/tests/add";
        }
        this.testService.addMedicalTest(medicalTestAddBindingModel);

        return "redirect:/tests";
    }


}
