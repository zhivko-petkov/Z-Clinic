package com.zhivkoproject.ZClinic.web;

import com.zhivkoproject.ZClinic.model.service.CartServiceModel;
import com.zhivkoproject.ZClinic.model.service.MedicalTestServiceModel;
import com.zhivkoproject.ZClinic.service.CartService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping()
    public String index(@AuthenticationPrincipal UserDetails userDetails,
                        Model model) {
        CartServiceModel currentUserCart = cartService.findCartByUser(userDetails.getUsername());

        int countOfTestsInCart = 0;
        BigDecimal totalPrice = new BigDecimal(0);

        if (currentUserCart.getId() != null){
            countOfTestsInCart = currentUserCart.getTests().size();

            for (MedicalTestServiceModel medicalTestServiceModel: currentUserCart.getTests()) {
                totalPrice = totalPrice.add(medicalTestServiceModel.getPrice());
            }
        }
        model.addAttribute("userCart", currentUserCart);
        model.addAttribute("countOfTestsInCart", countOfTestsInCart);
        model.addAttribute("totalPrice", totalPrice);

    return "orders-home";
    }

    @GetMapping("/add/{id}")
    public String add(@PathVariable Long id, Principal principal){
        cartService.addMedicalTestsInCart(id, principal.getName());

        return "redirect:/tests";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Principal principal){
        cartService.deleteMedicalTestInCart(id, principal.getName());

        return "redirect:/cart";
    }

    @GetMapping("/order/{id}")
    public String order(@PathVariable Long id, Principal principal) {
        cartService.makeOrder(id, principal.getName());
        cartService.removeCart(id, principal.getName());

        return "redirect:/";
    }



   // @PostMapping()

}
