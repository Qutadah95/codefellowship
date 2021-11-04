package com.example.codefellowship.Controllers;

import com.example.codefellowship.Models.ApplicationUser;
import com.example.codefellowship.Models.Post;
import com.example.codefellowship.Repositories.ApplicationUserRepository;
import com.example.codefellowship.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class PostController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    PostRepository postRepository;

    @PostMapping("/addpost")
    public RedirectView addPost(Principal principal , String body){
        ApplicationUser user = applicationUserRepository.findByUsername(principal.getName());
        Post newPost = new Post(body, user);
        postRepository.save(newPost);
        return new RedirectView("/profile");
    }
}
