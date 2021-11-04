package com.example.codefellowship.Controllers;

import com.example.codefellowship.Models.ApplicationUser;
import com.example.codefellowship.Repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class UserController {
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @GetMapping("/signup")
    public String getSignUpPage(){
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView signUpUser(@RequestParam String username,
                                   @RequestParam String password,
                                   @RequestParam String firstName,
                                   @RequestParam String lastName,
                                   @RequestParam String dateOfBirth,
                                   @RequestParam String bio){
        ApplicationUser applicationUser = new ApplicationUser(username, encoder.encode(password),firstName,lastName,dateOfBirth,bio);
        applicationUserRepository.save(applicationUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(applicationUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("profile");
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/profile")
    public  String getProfilePage(Principal principal, Model model){
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        model.addAttribute("user",user);
        return "profile";
    }
    @GetMapping("/users")
    public String getAllUsers(Model model){
        model.addAttribute("appUsers" , applicationUserRepository.findAll());
        return "users" ;
    }
    @GetMapping("/user/{id}")
    public String getUser(@PathVariable int id , Model model){
        model.addAttribute("user",applicationUserRepository.findById(id).get());
        return "userProfile";
    }

    @PostMapping("/follow/{id}")
    public RedirectView follow(@PathVariable int id, Principal principal){
        ApplicationUser followingUser = applicationUserRepository.findById(id).get();
        ApplicationUser loggedInUser = applicationUserRepository.findByUsername(principal.getName());
        loggedInUser.follow(followingUser);
        applicationUserRepository.save(loggedInUser);
        return new RedirectView("/user/"+id);
    }

    @GetMapping("/feed")
    public String posts(Principal principal, Model model){
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(principal.getName());
        model.addAttribute("followedUsers" , applicationUser.getFollowing());
        return "feed";

    }
@GetMapping("/")
public String getIndex(){
        return "index";
}
}
