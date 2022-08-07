package com.zhivkoproject.ZClinic.web;

import com.zhivkoproject.ZClinic.model.binding.ResultAddBindingModel;
import com.zhivkoproject.ZClinic.model.entity.*;
import com.zhivkoproject.ZClinic.model.enums.CategoryEnum;
import com.zhivkoproject.ZClinic.model.enums.UserRoleEnum;
import com.zhivkoproject.ZClinic.model.service.ResultServiceModel;
import com.zhivkoproject.ZClinic.model.user.ZClinicUserDetails;
import com.zhivkoproject.ZClinic.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ResultControllerMock {

    @Autowired
    private ModelMapper modelMapper;

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

    @Test
    void resultIndexPage() throws Exception {
        Order order = orderInit();
        mockMvc.perform(get("/result/order/" + order.getId()).
                        with(user(user)).
                        with(csrf())).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("resultsOrder")).
                andExpect(view().name("results-home"));
    }

    @Test
    void resultAddPage() throws Exception {
        Order order = orderInit();
        List<Result> results = new ArrayList<>();
        Result result = new Result();
        result.setResult(BigDecimal.valueOf(12));
        result.setTest(initTest());
        result.setOrder(order);
        results.add(result);
        order.setResults(results);

        mockMvc.perform(get("/result/order/add/" + order.getId()).
                        with(user(user)).
                        with(csrf())).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("form")).
                andExpect(view().name("results-add-edit"));

        /*ResultAddBindingModel resultAddBindingModel = new ResultAddBindingModel();
        resultAddBindingModel.addResult(modelMapper.map(order.getResults().get(0), ResultServiceModel.class));
*/
        /*mockMvc.perform(post("/result/save").
                with(user(user)).
                with(csrf())).
                andExpect(status().isOk()).
                andExpect(view().name("redirect:/orders"));*/
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

    private Order orderInit() {
        Order order = new Order();
        order.setUser(addedBy);
        order.setResults(currentCart.getUser().getResults());
        order.setCreatedOn(LocalDateTime.now());
        order.setReady(true);

        return orderRepository.save(order);
    }

}
