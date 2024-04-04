package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.mysql.UserRepository;
import com.example.demo.mysql.UserRole;
import com.example.demo.mysql.UserRoleRepository;

import jakarta.servlet.http.HttpServletRequest;

import com.example.demo.mysql.User;

@Controller
public class WelcomeController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserRoleRepository userRoleRepository;

  @GetMapping(path = "/all")
  public @ResponseBody Iterable<User> getAllUsers() {
    return userRepository.findAll();
  }

  @GetMapping("/user")
  @ModelAttribute("url")
  public String getUser() {
    return "user";
  }

  @GetMapping("/home")
  @ModelAttribute("url")
  public String getHome() {
    return "home";
  }

  @GetMapping("/admin")
  @ModelAttribute("url")
  public String getAdmin(Model model) {
    List<Map<String, Object>> userDetails = new ArrayList<>();
    List<UserRole> records = userRoleRepository.findAllByRoleId(1);

    for (UserRole record : records) {
      Optional<User> currentUser = userRepository.findById(record.getUserId());

      if (currentUser.isPresent()) {
        String username = currentUser.get().getUsername();
        Integer id = currentUser.get().getId();
        if (!username.equals("admin")) {
          Map<String, Object> userDetail = new HashMap<>();

          userDetail.put("id", id);
          userDetail.put("username", username);
          userDetails.add(userDetail);
        }
      }
    }

    model.addAttribute("userDetails", userDetails);
    return "admin";
  }

  @GetMapping("/login")
  public String Welcome() {
    return "login";
  }

  @PostMapping(path = "/register")
  public void addNewUser(HttpServletRequest request) {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String hashed = new BCryptPasswordEncoder().encode(password);

    System.out.println("----------------------");
    System.out.println("Username: " + username);
    System.out.println("Password: " + password);
    System.out.println("hashed: " + hashed);
    System.out.println("----------------------");

    User user = new User();
    user.setUsername(username);
    user.setPassword(hashed);
    userRepository.save(user);

    UserRole userRole = new UserRole();
    userRole.setUserId(user.getId());
    userRole.setRoleId(1);
    userRoleRepository.save(userRole);
  }
}
