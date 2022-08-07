package com.zhivkoproject.ZClinic.web;

import com.zhivkoproject.ZClinic.model.entity.*;
import com.zhivkoproject.ZClinic.model.enums.CategoryEnum;
import com.zhivkoproject.ZClinic.model.enums.UserRoleEnum;
import com.zhivkoproject.ZClinic.model.user.ZClinicUserDetails;
import com.zhivkoproject.ZClinic.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerMock {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private CartRepository cartRepository;

    private Cart currentCart;

    private User addedBy;

    private ZClinicUserDetails user;

    @BeforeEach
    void setUp(){
        User user = new User();

        //ROLE ADMIN
        UserRole userRole = new UserRole();
        userRole.setUserRole(UserRoleEnum.ADMIN);
        userRole = userRoleRepository.save(userRole);


        List<UserRole> roles = new ArrayList<>();
        roles.add(userRole);


        user.setAddress("Stara Zagora");
        user.setImageUrl("https://api.time.com/wp-content/uploads/2019/08/better-smartphone-photos.jpg");
        user.setEmail("stoyan@zclinic.bg");
        user.setFirstName("Stoyan");
        user.setSurname("Marinov");
        user.setUsername("stoyan");
        user.setAdditionalDetails("I'm famous person who likes.......");
        user.setPassword("12345");
        user.setUserRoles(roles);

        List<GrantedAuthority> authorities = user.getUserRoles().stream().map(r -> {
            return new SimpleGrantedAuthority("ROLE_".concat(r.getUserRole().name()));
        }).collect(Collectors.toList());

        Long userId = user.getId();


        addedBy = userRepository.save(user);
        this.user = new ZClinicUserDetails(user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getFirstName(),
                user.getSurname(),
                authorities);

        currentCart = cartInit();

    }

    @AfterEach
    void refresh() {
        userRepository.deleteAll();
        orderRepository.deleteAll();
        testRepository.deleteAll();
        cartRepository.deleteAll();
        resultRepository.deleteAll();
    }

    //only alone
    @org.junit.jupiter.api.Test
    void ordersGetByIdPage() throws Exception {

        mockMvc.perform(get("/cart/order/" + currentCart.getId()).
                        with(user(user)).
                        with(csrf())).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/"));

        mockMvc.perform(get("/orders/" + 1).
                        with(user(user)).
                        with(csrf())).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("loggedUserOrders")).
                andExpect(view().name("results-index"));

        mockMvc.perform(get("/orders/delete/" + 1).
                        with(user(user)).
                        with(csrf())).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/orders"));
    }


    @org.junit.jupiter.api.Test
    void ordersIndexPage() throws Exception {
        mockMvc.perform(get("/orders").
                        with(user(user)).
                        with(csrf())).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("orders")).
                andExpect(view().name("orders-index"));
    }

    @org.junit.jupiter.api.Test
    void ordersIndexLoggedUserPage() throws Exception {
        mockMvc.perform(get("/orders/loggedUser").
                        with(user(user)).
                        with(csrf())).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("loggedUserOrders")).
                andExpect(view().name("results-index"));
    }


    private com.zhivkoproject.ZClinic.model.entity.Test initTest() {
        com.zhivkoproject.ZClinic.model.entity.Test test = new com.zhivkoproject.ZClinic.model.entity.Test();
        test.setName("LHJ");
        test.setAddedBy(addedBy);
        test.setMaterial("blood");
        test.setPrice(BigDecimal.valueOf(12.36));
        test.setCategory(categoryRepository.findCategoryByCategoryEnum(CategoryEnum.BIOCHEMICAL));
        test.setOffer(true);
        test.setDelay(false);

        com.zhivkoproject.ZClinic.model.entity.Test saved = testRepository.save(test);

        return saved;
    }

    private Cart cartInit() {
        com.zhivkoproject.ZClinic.model.entity.Test test = initTest();
        List<com.zhivkoproject.ZClinic.model.entity.Test> testList = new ArrayList<>();
        testList.add(test);

        Cart cart = new Cart();
        cart.setTests(testList);
        cart.setUser(addedBy);

        Cart save = cartRepository.save(cart);
        return save;
    }

}
