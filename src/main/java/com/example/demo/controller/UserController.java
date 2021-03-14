package com.example.demo.controller;

import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.model.Role;
import com.example.demo.model.Stripe;
import com.example.demo.model.Transaction;
import com.example.demo.model.User;
import com.example.demo.service.ProductService;
import com.example.demo.service.StripeService;
import com.example.demo.service.TransactionService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;

@RestController
public class UserController {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private StripeService stripeService;

    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    // =====================
    // 1. Users
    @PostMapping("/api/user/registration")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        //default role.
        user.setRole(Role.USER);
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/api/user/login")
    public ResponseEntity<?> getUser(Principal principal) {
        // principal = httpServletRequest.getUserPrincipal.
        if (principal == null) {
            // logout will also use here so we should return ok http status.
            return ResponseEntity.ok(principal);
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) principal;
        User user = userService.findByUsername(authenticationToken.getName());
        user.setToken(tokenProvider.generateToken(authenticationToken));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    // =====================
    // 2. Product
    @GetMapping("/api/user/products")
    public ResponseEntity<?> getAllProducts() {
        return new ResponseEntity<>(productService.findAllProducts(), HttpStatus.OK);
    }


    // =====================
    // 3. Transactions
    @PostMapping("/api/user/purchase")
    public ResponseEntity<?> purchaseProduct(@RequestBody Transaction transaction) {
        transaction.setPurchaseDate(LocalDateTime.now());
        transactionService.saveTransaction(transaction);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("/api/user/stripe")
    public String home(Stripe model) {
        model.setAmount(50 * 100); // In cents
        model.setStripePublicKey(stripePublicKey);
        return "index";
    }

    @PostMapping("/api/user/charge")
    public String chargeCard(HttpServletRequest request) throws Exception {
        String token = request.getParameter("stripeToken");
        Double amount = Double.parseDouble(request.getParameter("amount"));
        stripeService.chargeNewCard(token, amount);
        return "result";
    }
}