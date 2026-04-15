package com.tugas6.deploy.controller;

import com.tugas6.deploy.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    // Data temporary
    private static List<User> mahasiswaList = new ArrayList<>();

    // Login
    @GetMapping({"/", "/login"})
    public String loginPage(HttpSession session) {
        if (session.getAttribute("isLoggedIn") != null) {
            return "redirect:/home";
        }
        return "login";
    }

    @PostMapping("/login")
    public String prosesLogin(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session, Model model) {

        if ("admin".equals(username) && "20230140227".equals(password)) {
            session.setAttribute("isLoggedIn", true);
            session.setAttribute("userNim", password);
            return "redirect:/home";
        }
        model.addAttribute("error", "Username atau Password salah!");
        return "login";
    }

    // Home page
    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        if (session.getAttribute("isLoggedIn") == null) {
            return "redirect:/login";
        }
        model.addAttribute("mahasiswaList", mahasiswaList);
        model.addAttribute("userNim", session.getAttribute("userNim"));
        return "home";
    }

    // Form page
    @GetMapping("/form")
    public String formPage(HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null) {
            return "redirect:/login";
        }
        return "form";
    }

    @PostMapping("/form")
    public String simpanData(@RequestParam String nama,
                             @RequestParam String nim,
                             @RequestParam String jenisKelamin) {

        mahasiswaList.add(new User(nama, nim, jenisKelamin));
        return "redirect:/home";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}