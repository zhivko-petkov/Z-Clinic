package com.zhivkoproject.ZClinic.service.impl;

import com.zhivkoproject.ZClinic.model.binding.UserAddBindingModel;
import com.zhivkoproject.ZClinic.model.binding.UserLoginBindingModel;
import com.zhivkoproject.ZClinic.model.binding.UserRegisterBindingModel;
import com.zhivkoproject.ZClinic.model.entity.Order;
import com.zhivkoproject.ZClinic.model.entity.User;
import com.zhivkoproject.ZClinic.model.entity.UserRole;
import com.zhivkoproject.ZClinic.model.enums.UserRoleEnum;
import com.zhivkoproject.ZClinic.model.binding.UserEditBindingModel;
import com.zhivkoproject.ZClinic.model.service.NewsServiceModel;
import com.zhivkoproject.ZClinic.model.service.UserServiceModel;
import com.zhivkoproject.ZClinic.repository.OrderRepository;
import com.zhivkoproject.ZClinic.repository.UserRepository;
import com.zhivkoproject.ZClinic.repository.UserRoleRepository;
import com.zhivkoproject.ZClinic.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;

    public UserServiceImpl(UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           PasswordEncoder passwordEncoder,
                           ModelMapper modelMapper,
                           OrderRepository orderRepository) {

        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.orderRepository = orderRepository;
    }

    @Override
    public void register(UserRegisterBindingModel userRegisterBindingModel) {
        User newUser = modelMapper.map(userRegisterBindingModel, User.class);
        newUser.setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()));
        List<UserRole> userRoles = newUser.getUserRoles();
        userRoles.add(userRoleRepository.findByUserRole(UserRoleEnum.STANDART));
        newUser.setUserRoles(userRoles);

        this.userRepository.save(newUser);

    }


    @Override
    public void initUserRoles() {
        if(userRoleRepository.count() > 0){
            return;
        }
        Arrays.stream(UserRoleEnum.values())
                .forEach(userRoleEnum -> {
                    UserRole userRole = new UserRole();
                   userRole.setUserRole(userRoleEnum);
                    userRoleRepository.save(userRole);
                });
    }

    @Override
    public void initUser() {
        if(userRepository.count() > 0){
            return;
        }
        //ADMIN
        User admUser = new User();
        admUser.setUsername("admin");
        admUser.setEmail("admin@admin.bg");
        admUser.setFirstName("Admin");
        admUser.setSurname("Admin");
        admUser.setPassword(passwordEncoder.encode("topsecret"));
        admUser.setAddress("Sofia");

        List<UserRole> currentRoles = admUser.getUserRoles();
        currentRoles.add(userRoleRepository.findByUserRole(UserRoleEnum.ADMIN));
        admUser.setUserRoles(currentRoles);

        userRepository.save(admUser);

        //DOCTOR
        User doctorUser = new User();
        doctorUser.setUsername("doctor");
        doctorUser.setEmail("doctor@doctor.bg");
        doctorUser.setFirstName("Doctor");
        doctorUser.setSurname("Doctor");
        doctorUser.setPassword(passwordEncoder.encode("topsecret"));
        doctorUser.setAddress("Sofia");

        List<UserRole> currentDoctorRoles = doctorUser.getUserRoles();
        currentDoctorRoles.add(userRoleRepository.findByUserRole(UserRoleEnum.DOCTOR));
        doctorUser.setUserRoles(currentDoctorRoles);

        userRepository.save(doctorUser);

        //MODERATOR
        User moderatorUser = new User();
        moderatorUser.setUsername("moderator");
        moderatorUser.setEmail("moderator@moderator.bg");
        moderatorUser.setFirstName("Moderator");
        moderatorUser.setSurname("Moderator");
        moderatorUser.setPassword(passwordEncoder.encode("topsecret"));
        moderatorUser.setAddress("Plovdiv");

        List<UserRole> currentModeratorRoles = moderatorUser.getUserRoles();
        currentModeratorRoles.add(userRoleRepository.findByUserRole(UserRoleEnum.MODERATOR));
        moderatorUser.setUserRoles(currentModeratorRoles);

        userRepository.save(moderatorUser);

        //STANDART
        User standartUser = new User();
        standartUser.setUsername("standart");
        standartUser.setEmail("standart@standart.bg");
        standartUser.setFirstName("Standart");
        standartUser.setSurname("Standart");
        standartUser.setPassword(passwordEncoder.encode("topsecret"));
        standartUser.setAddress("Varna");

        List<UserRole> currentStandartRoles = standartUser.getUserRoles();
        currentStandartRoles.add(userRoleRepository.findByUserRole(UserRoleEnum.STANDART));
        standartUser.setUserRoles(currentStandartRoles);
        userRepository.save(standartUser);

    }

    @Override
    public UserServiceModel findUser(String username) {
        User currentUser = userRepository.findByUsername(username).orElse(null);
        UserServiceModel currentUserService = modelMapper.map(currentUser, UserServiceModel.class);

        return currentUserService;
    }


    @Override
    public void editUserImage(UserEditBindingModel userEditBindingModel) {
        //you have to find user by email
        //if you want to make mapping 
        User user = modelMapper.map(userEditBindingModel, User.class);
        User mapUser = userRepository.findByUsername(user.getUsername()).orElse(null);

        if(userEditBindingModel.getImageUrl() == null){
            user.setImageUrl(mapUser.getImageUrl());
        }

        user.setUsername(mapUser.getUsername());
        user.setPassword(mapUser.getPassword());
        user.setId(mapUser.getId());
        userRepository.save(user);
    }

    @Override
    public boolean checkUserPassword(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);

        if (passwordEncoder.matches(password, user.getPassword())){
            return true;
        }
        return false;
    }

    @Override
    public void changeUserPassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username).orElse(null);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public List<UserServiceModel> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(users -> modelMapper.map(users, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addUser(UserAddBindingModel userAddBindingModel) {
        User newUser = modelMapper.map(userAddBindingModel, User.class);
        newUser.setPassword(passwordEncoder.encode(userAddBindingModel.getPassword()));

        List<UserRole> userRoles = newUser.getUserRoles();
        UserRole role = userRoleRepository.findByUserRole(userAddBindingModel.getRole());
        //role.setUserRole(userAddBindingModel.getRole());
        userRoles.add(role);

        newUser.setUserRoles(userRoles);

        this.userRepository.save(newUser);
    }

    //edited 11:37
    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);

        this.userRepository.delete(user);
    }

    @Override
    public String findUsernameById(Long id) {

        User user = userRepository.findById(id).orElse(null);
        return user.getUsername();

    }

    @Override
    public UserServiceModel findUserByEmail(String email) {
        return modelMapper.map(userRepository.findByEmail(email), UserServiceModel.class);
    }

    @Override
    public boolean checkUsernameExists(String username) {

        User user = userRepository.findByUsername(username).orElse(null);
        if(user != null){
            return false;
        }

        return true;
    }

    @Override
    public boolean checkEmailExists(String email) {
        User user = userRepository.findByEmail(email).orElse(null);

        if(user != null){
            return false;
        }

        return true;
    }

    @Override
    public List<UserServiceModel> getDoctors() {
        UserRole doctor = userRoleRepository.findByUserRole(UserRoleEnum.DOCTOR);
        List<User> findAllDoctors = userRepository.findAllByUserRolesContaining(doctor);

        return findAllDoctors.stream()
                .map(doctors-> modelMapper.map(doctors, UserServiceModel.class))
                .collect(Collectors.toList());
    }


}
