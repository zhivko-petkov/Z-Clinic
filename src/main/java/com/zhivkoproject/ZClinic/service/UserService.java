package com.zhivkoproject.ZClinic.service;

import com.zhivkoproject.ZClinic.model.binding.UserAddBindingModel;
import com.zhivkoproject.ZClinic.model.binding.UserLoginBindingModel;
import com.zhivkoproject.ZClinic.model.binding.UserRegisterBindingModel;
import com.zhivkoproject.ZClinic.model.entity.User;
import com.zhivkoproject.ZClinic.model.binding.UserEditBindingModel;
import com.zhivkoproject.ZClinic.model.service.UserServiceModel;

import java.util.List;

public interface UserService {
    void register(UserRegisterBindingModel userRegisterBindingModel);

    void initUserRoles();

    void initUser();

    UserServiceModel findUser(String username);

    void editUserImage(UserEditBindingModel userEditBindingModel);

    boolean checkUserPassword(String username, String password);

    void changeUserPassword(String username, String newPassword);

    List<UserServiceModel> getAllUsers();

    void addUser(UserAddBindingModel userAddBindingModel);

    void deleteUser(Long id);

    String findUsernameById(Long id);

    UserServiceModel findUserByEmail(String email);

    boolean checkUsernameExists(String username);

    boolean checkEmailExists(String email);
}
