package com.zhivkoproject.ZClinic.service.impl;

import com.zhivkoproject.ZClinic.model.binding.UserAddBindingModel;
import com.zhivkoproject.ZClinic.model.binding.UserRegisterBindingModel;
import com.zhivkoproject.ZClinic.model.entity.User;
import com.zhivkoproject.ZClinic.model.entity.UserRole;
import com.zhivkoproject.ZClinic.model.enums.UserRoleEnum;
import com.zhivkoproject.ZClinic.model.binding.UserEditBindingModel;
import com.zhivkoproject.ZClinic.model.service.UserServiceModel;
import com.zhivkoproject.ZClinic.repository.OrderRepository;
import com.zhivkoproject.ZClinic.repository.UserRepository;
import com.zhivkoproject.ZClinic.repository.UserRoleRepository;
import com.zhivkoproject.ZClinic.service.UserService;
import org.modelmapper.ModelMapper;
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

        //DOCTOR 1
        User doctorUser = new User();
        doctorUser.setUsername("doctor");
        doctorUser.setEmail("doctor@doctor.bg");
            doctorUser.setFirstName("Ivan");
        doctorUser.setSurname("Kolev");
        doctorUser.setPassword(passwordEncoder.encode("topsecret"));
        doctorUser.setImageUrl("https://cdn.pixabay.com/photo/2017/07/23/10/44/dentist-2530990_960_720.jpg");
        doctorUser.setAddress("bul.Bulgaria 23, Sofia");
        doctorUser.setAdditionalDetails("Doctor Ivan is a dentist, also known " +
                "as a dental surgeon, is a health care professional who specializes " +
                "in dentistry (the diagnosis, prevention, management, and treatment of " +
                "diseases and conditions of the oral cavity and other aspects of the " +
                "craniofacial complex including the temporomandibular joint). The " +
                "dentist's supporting team aids in providing oral health services." +
                " The dental team includes dental assistants, dental hygienists, " +
                "dental technicians, and sometimes dental therapists.");

        List<UserRole> currentDoctorRoles = doctorUser.getUserRoles();
        currentDoctorRoles.add(userRoleRepository.findByUserRole(UserRoleEnum.DOCTOR));
        doctorUser.setUserRoles(currentDoctorRoles);

        userRepository.save(doctorUser);

        //DOCTOR 2
        User secontDoctorUser = new User();
        secontDoctorUser.setUsername("florin");
        secontDoctorUser.setEmail("florin@zclinic.bg");
        secontDoctorUser.setFirstName("Lilly");
        secontDoctorUser.setSurname("Florin");
        secontDoctorUser.setPassword(passwordEncoder.encode("topsecret"));
        secontDoctorUser.setImageUrl("https://cdn.pixabay.com/photo/2020/09/22/03/23/doctor-5591782_960_720.jpg");
        secontDoctorUser.setAddress("str. Dondukov 11, Sofia");
        secontDoctorUser.setAdditionalDetails("Doctor Lilly is an Anesthesiologists focus on " +
                "the care of surgical patients and pain relief. " +
                "They administer drugs (anesthetics) that reduce or eliminate the sensation of " +
                "pain during an operation or another medical procedure. During surgery, they are" +
                " responsible for adjusting the amount of anesthetic as needed, and monitoring the" +
                " patient's heart rate, body temperature, blood pressure, and breathing. They also" +
                " work outside of the operating room, providing pain relief for patients in the" +
                " intensive care unit, for women in labor and delivery of babies, and for patients " +
                "who suffer from chronic pain. Anesthesiologists work with other physicians and surgeons " +
                "to decide on treatments and procedures before, during, and after surgery.");

        List<UserRole> secondCurrentDoctorRoles = secontDoctorUser.getUserRoles();
        secondCurrentDoctorRoles.add(userRoleRepository.findByUserRole(UserRoleEnum.DOCTOR));
        secontDoctorUser.setUserRoles(secondCurrentDoctorRoles);

        userRepository.save(secontDoctorUser);

        //DOCTOR 2
        User thirdDoctorUser = new User();
        thirdDoctorUser.setUsername("miller");
        thirdDoctorUser.setEmail("miller@zclinic.bg");
        thirdDoctorUser.setFirstName("Sara");
        thirdDoctorUser.setSurname("Miller");
        thirdDoctorUser.setPassword(passwordEncoder.encode("topsecret"));
        thirdDoctorUser.setImageUrl("https://cdn.pixabay.com/photo/2021/02/15/16/01/woman-6018388_960_720.jpg");
        thirdDoctorUser.setAddress("bul. Patriarh Evtimii 21, Stara Zagora");
        thirdDoctorUser.setAdditionalDetails("Doctor Sara is a surgeon. She treat injuries, diseases," +
                " and deformities through operations. Using a variety of instruments, a " +
                "surgeon corrects physical deformities, repairs bone and tissue after injuries," +
                " or performs preventive or elective surgeries on patients. Although a large number" +
                " perform general surgery, many surgeons choose to specialize in a specific area. " +
                "Specialties include orthopedic surgery (the treatment of the musculoskeletal system)," +
                "neurological surgery (treatment of the brain and nervous system), cardiovascular surgery," +
                " and plastic or reconstructive surgery. Like other physicians, surgeons examine patients, " +
                "perform and interpret diagnostic tests, and counsel patients on preventive healthcare. Some" +
                " specialist physicians also perform surgery.");

        List<UserRole> thirdCurrentDoctorRoles = thirdDoctorUser.getUserRoles();
        thirdCurrentDoctorRoles.add(userRoleRepository.findByUserRole(UserRoleEnum.DOCTOR));
        thirdDoctorUser.setUserRoles(thirdCurrentDoctorRoles);

        userRepository.save(thirdDoctorUser);


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
