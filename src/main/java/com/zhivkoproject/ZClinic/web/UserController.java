package com.zhivkoproject.ZClinic.web;

import com.zhivkoproject.ZClinic.config.FileUploadUtil;
import com.zhivkoproject.ZClinic.model.binding.*;
import com.zhivkoproject.ZClinic.model.entity.UserRole;
import com.zhivkoproject.ZClinic.model.enums.UserRoleEnum;
import com.zhivkoproject.ZClinic.model.service.UserServiceModel;
import com.zhivkoproject.ZClinic.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;



    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("userBindingModel")
    public UserRegisterBindingModel initUserModel() {
        return new UserRegisterBindingModel();
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterBindingModel userBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userBindingModel", userBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userBindingModel",
                    bindingResult);
            return "redirect:/users/register";
        }
        this.userService.register(userBindingModel);

        return "redirect:/";
    }

    @ModelAttribute("userLoginBindingModel")
    public UserLoginBindingModel userLoginBindingModel() {
        return new UserLoginBindingModel();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

   /*
    @PostMapping("/login")
    */
   /* public String loginConfirm(@Valid UserLoginBindingModel userLoginBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);

            return "redirect:login";
        }

        //boolean userExists = userService.userExist(userLoginBindingModel.getUsername(), userLoginBindingModel.getPassword());
        /*UserServiceModel checkUser = userService.findByUsernameAndPassword(userLoginBindingModel.getUsername(),
                userLoginBindingModel.getPassword());*/

       /* userService.loginUser(userLoginBindingModel);
        return "redirect:/";
    }
    */

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
        return "user-home";
    }

    @PostMapping("/profile")
    public String profile(@Valid UserEditBindingModel userEditBindingModel,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          @AuthenticationPrincipal UserDetails userDetails,
                          @RequestParam("image") MultipartFile multipartFile) throws IOException {


        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        UserEditBindingModel user = modelMapper.map(userService.findUser(userDetails.getUsername()), UserEditBindingModel.class);
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
            return "redirect:/users/profile";
        }

        userService.editUserImage(userEditBindingModel);

        return "redirect:/users/profile";
    }

    @GetMapping("/profile/changePassword")
    public String changePassword(Principal principal, Model model){
        String username = principal.getName();
        UserChangePasswordBindingModel userChangePasswordBindingModel = new UserChangePasswordBindingModel();
        model.addAttribute("userChangePasswordBindingModel", userChangePasswordBindingModel);
        return "user-password";
    }

    @PostMapping("/profile/changePassword")
    public String changePassword(@Valid UserChangePasswordBindingModel userChangePasswordBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal UserDetails userDetails){

        boolean checkUser = userService.checkUserPassword(userDetails.getUsername(), userChangePasswordBindingModel.getOldPassword());

        if(!checkUser){
            redirectAttributes.addFlashAttribute("errorOldPassword", true);
            return "redirect:/users/profile/changePassword";
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userChangePasswordBindingModel", userChangePasswordBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userChangePasswordBindingModel",
                    bindingResult);
            return "redirect:/users/profile/changePassword";
        }

        userService.changeUserPassword(userDetails.getUsername(), userChangePasswordBindingModel.getNewPassword());


        return  "redirect:/users/profile";
    }


    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_DOCTOR')")
    public String index(@AuthenticationPrincipal UserDetails userDetails,
                           Model model){

        List<UserServiceModel> getAllUsers = userService.getAllUsers();
        UserRole admin = new UserRole();
        admin.setUserRole(UserRoleEnum.ADMIN);

        UserRole moderator = new UserRole();
        moderator.setUserRole(UserRoleEnum.MODERATOR);

        UserRole doctor = new UserRole();
        doctor.setUserRole(UserRoleEnum.DOCTOR);

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
        return "user-add";
    }

    @PostMapping("/add")
    //@PostAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_DOCTOR')")
    public String add(@Valid UserAddBindingModel userAddBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userAddBindingModel", userAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userAddBindingModel",
                    bindingResult);
            return "redirect:/users/add";
        }
        this.userService.addUser(userAddBindingModel);

        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Principal principal){
        userService.deleteUser(id);

        return "redirect:/users/";
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

        return "redirect:/users/";
    }





}