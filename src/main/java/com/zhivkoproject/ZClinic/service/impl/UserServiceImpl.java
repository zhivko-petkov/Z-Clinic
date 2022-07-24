package com.zhivkoproject.ZClinic.service.impl;

import com.zhivkoproject.ZClinic.model.binding.UserAddBindingModel;
import com.zhivkoproject.ZClinic.model.binding.UserLoginBindingModel;
import com.zhivkoproject.ZClinic.model.binding.UserRegisterBindingModel;
import com.zhivkoproject.ZClinic.model.entity.User;
import com.zhivkoproject.ZClinic.model.entity.UserRole;
import com.zhivkoproject.ZClinic.model.enums.UserRoleEnum;
import com.zhivkoproject.ZClinic.model.binding.UserEditBindingModel;
import com.zhivkoproject.ZClinic.model.service.UserServiceModel;
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
    private final UserDetailsService userDetailsService;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.userDetailsService = userDetailsService;
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
        User firstUser = new User();
        firstUser.setUsername("admin");
        firstUser.setEmail("admin@admin.bg");
        firstUser.setFirstName("adm");
        firstUser.setSurname("adm");
        firstUser.setPassword(passwordEncoder.encode("topsecret"));
        firstUser.setAddress("Sofia");
        List<UserRole> currentRoles = firstUser.getUserRoles();
        currentRoles.add(userRoleRepository.findByUserRole(UserRoleEnum.ADMIN));
        firstUser.setUserRoles(currentRoles);
        userRepository.save(firstUser);
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


}
