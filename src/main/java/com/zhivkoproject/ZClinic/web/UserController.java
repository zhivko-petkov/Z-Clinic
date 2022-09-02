package com.zhivkoproject.ZClinic.web;

import com.zhivkoproject.ZClinic.config.FileUploadUtil;
import com.zhivkoproject.ZClinic.model.binding.*;
import com.zhivkoproject.ZClinic.model.entity.UserRole;
import com.zhivkoproject.ZClinic.model.enums.UserRoleEnum;
import com.zhivkoproject.ZClinic.model.service.MedicalTestServiceModel;
import com.zhivkoproject.ZClinic.model.service.UserServiceModel;
import com.zhivkoproject.ZClinic.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @ModelAttribute("userBindingModel")
    public UserRegisterBindingModel initUserModel() {
        return new UserRegisterBindingModel();
    }

    @GetMapping("/register")
    public String register(Model model) {
        if(!model.containsAttribute("isUsernameUnique")) {
            model.addAttribute("isUsernameUnique", true);
        }
        if(!model.containsAttribute("isEmailUnique")) {
            model.addAttribute("isEmailUnique", true);
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterBindingModel userBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        boolean isUsernameUnique = userService.checkUsernameExists(userBindingModel.getUsername());
        boolean isEmailUnique = userService.checkEmailExists(userBindingModel.getEmail());


        if (bindingResult.hasErrors() || !isEmailUnique || !isUsernameUnique) {
            redirectAttributes.addFlashAttribute("userBindingModel", userBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userBindingModel",
                    bindingResult);

            if (!isEmailUnique) {
                redirectAttributes.addFlashAttribute("isEmailUnique", false);
            }

            if(!isUsernameUnique){
                redirectAttributes.addFlashAttribute("isUsernameUnique", false);
            }

            return "redirect:/users/register";
        }
        this.userService.register(userBindingModel);

        return "redirect:/";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login-error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);

        redirectAttributes.addFlashAttribute("bad_credentials", true);

        return "redirect:/users/login";
    }


    @ModelAttribute
    public UserEditBindingModel userServiceModel(){
        return new UserEditBindingModel();
    }


    @GetMapping("/profile")
    public String profile(Principal principal, Model model){
        String username = principal.getName();
        UserServiceModel user = userService.findUser(username);
        UserEditBindingModel userEditBindingModel = modelMapper.map(user, UserEditBindingModel.class);
        model.addAttribute("userEditBindingModel", userEditBindingModel);
        if(!model.containsAttribute("isEmailUnique")) {
            model.addAttribute("isEmailUnique", true);
        }
        return "user-home";
    }

    @PostMapping("/profile")
    public String profile(@Valid UserEditBindingModel userEditBindingModel,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          @AuthenticationPrincipal UserDetails userDetails,
                          @RequestParam("image") MultipartFile multipartFile) throws IOException {

        UserServiceModel userServiceModel = userService.findUser(userDetails.getUsername());

        boolean isEmailUnique;
        String email = userServiceModel.getEmail();

        if (!((userEditBindingModel.getEmail()).equals(email))){
            isEmailUnique = userService.checkEmailExists(userEditBindingModel.getEmail());
        } else {
            isEmailUnique = true;
        }

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        UserEditBindingModel user = modelMapper.map(userService.findUser(userDetails.getUsername()), UserEditBindingModel.class);
        userEditBindingModel.setRoles(user.getRoles());
        userEditBindingModel.setUsername(user.getUsername());



        String uploadDir = "src/main/resources/static/user-photos/" + user.getUsername();
        if(!fileName.isEmpty()) {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            userEditBindingModel.setImageUrl("/"+"user-photos"+"/"+ user.getUsername()+ "/" + fileName);
        }
        if (bindingResult.hasErrors() || !isEmailUnique) {
            redirectAttributes.addFlashAttribute("userEditBindingModel", userEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userEditBindingModel",
                    bindingResult);

            if (!isEmailUnique) {
                redirectAttributes.addFlashAttribute("isEmailUnique", false);
            }

            return "redirect:/users/profile";
        }

        userService.editUserImage(userEditBindingModel);

        return "redirect:/users/profile";
    }

    @ModelAttribute("userChangePasswordBindingModel")
    public UserChangePasswordBindingModel userChangePasswordBindingModel() {return new UserChangePasswordBindingModel();}

    @GetMapping("/profile/changePassword")
    public String changePassword(Principal principal, Model model){
        if (!model.containsAttribute("errorOldPassword"))
            model.addAttribute("errorOldPassword", false);

        return "user-password";
    }

    @PostMapping("/profile/changePassword")
    public String changePassword(@Valid UserChangePasswordBindingModel userChangePasswordBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal UserDetails userDetails){

        boolean checkUser = userService.checkUserPassword(userDetails.getUsername(),
                userChangePasswordBindingModel.getOldPassword());



        if (bindingResult.hasErrors() || !checkUser) {

            if(!checkUser)
                redirectAttributes.addFlashAttribute("errorOldPassword", true);

            redirectAttributes.addFlashAttribute("userChangePasswordBindingModel", userChangePasswordBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userChangePasswordBindingModel",
                    bindingResult);

            return "redirect:/users/profile/changePassword";
        }

        userService.changeUserPassword(userDetails.getUsername(), userChangePasswordBindingModel.getNewPassword());


        return  "redirect:/users/profile";
    }

    @GetMapping ("/profile/resetPassword/{id}")
    public String passwordReset(@PathVariable Long id) {
        userService.changeUserPassword(userService.findUsernameById(id), "topsecret");
        return "redirect:/users";
    }


    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_DOCTOR')")
    public String index(@AuthenticationPrincipal UserDetails userDetails,
                           Model model){
        List<UserServiceModel> getAllUsers = userService.getAllUsers();
        model.addAttribute("getAllUsers", getAllUsers);
        return "user-index";
    }

    @ModelAttribute("userAddBindingModel")
    public UserAddBindingModel userAddBindingModel() {
        return new UserAddBindingModel();
    }

    @GetMapping("/add")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_DOCTOR')")
    public String add(Model model) {
        if(!model.containsAttribute("isUsernameUnique"))
            model.addAttribute("isUsernameUnique", true);

        if(!model.containsAttribute("isEmailUnique"))
            model.addAttribute("isEmailUnique", true);

        return "user-add";
    }

    @PostMapping("/add")
    //@PostAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_DOCTOR')")
    public String add(@Valid UserAddBindingModel userAddBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        boolean isUsernameUnique = userService.checkUsernameExists(userAddBindingModel.getUsername());
        boolean isEmailUnique = userService.checkEmailExists(userAddBindingModel.getEmail());

        if (bindingResult.hasErrors() || !isUsernameUnique || !isEmailUnique) {
            redirectAttributes.addFlashAttribute("userAddBindingModel", userAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userAddBindingModel",
                    bindingResult);

            if (!isEmailUnique){
                redirectAttributes.addFlashAttribute("isEmailUnique", false);
            }

            if (!isUsernameUnique){
                redirectAttributes.addFlashAttribute("isUsernameUnique", false);
            }

            return "redirect:/users/add";
        }
        this.userService.addUser(userAddBindingModel);

        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Principal principal){
        if (userService.findUser(principal.getName()).getId() == id){
            userService.deleteUser(id);
            return "redirect:/users/logout";
        }
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Principal principal, Model model){
        String username = userService.findUsernameById(id);
        UserEditBindingModel userEditBindingModel = modelMapper.map(userService.findUser(username), UserEditBindingModel.class);
        userEditBindingModel.setUsername(username);
        model.addAttribute("userEditBindingModel", userEditBindingModel);
        model.addAttribute("userId", id);
        return "user-edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @Valid UserEditBindingModel userEditBindingModel,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          @AuthenticationPrincipal UserDetails userDetails,
                          @RequestParam("image") MultipartFile multipartFile) throws IOException {

        String username = userService.findUsernameById(id);

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        UserEditBindingModel user = modelMapper.map(userService.findUser(username), UserEditBindingModel.class);
        userEditBindingModel.setRoles(user.getRoles());
        userEditBindingModel.setUsername(user.getUsername());

        String uploadDir = "src/main/resources/static/user-photos/" + user.getUsername();
        if(!fileName.isEmpty()) {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            userEditBindingModel.setImageUrl("/"+"user-photos"+"/"+ user.getUsername()+ "/" + fileName);
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userEditBindingModel", userEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userEditBindingModel",
                    bindingResult);
            return "redirect:/users/edit/{id}";
        }

        userService.editUserImage(userEditBindingModel);

        return "redirect:/users";
    }





}
