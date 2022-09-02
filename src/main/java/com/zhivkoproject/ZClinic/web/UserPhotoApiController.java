package com.zhivkoproject.ZClinic.web;

import com.zhivkoproject.ZClinic.model.service.UserServiceModel;
import com.zhivkoproject.ZClinic.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class UserPhotoApiController {
    private final UserService userService;


    @GetMapping("/api/loggedUserPhoto")
    @Transactional
    public ResponseEntity<Map<String, String>> userPhoto(Principal principal) {
        Map<String, String> photo = new HashMap<>();
        if (principal != null){
            UserServiceModel userServiceModel = userService.findUser(principal.getName());
            photo.put("imageAddress", userServiceModel.getImageUrl());
        }

        return new ResponseEntity<Map<String, String>>(photo, HttpStatus.OK);
    }
}
