package com.exmple.conditionalApp.controller;

import com.exmple.conditionalApp.profile.SystemProfile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ProfileController {
    private final SystemProfile profile; // Используем final для большей безопасности
    public ProfileController(SystemProfile profile) {
        this.profile = profile;
        System.out.println("ProfileController created with profile: " + profile.getProfile()); // Для отладки
    }

    @GetMapping("profile")
    public String getProfile() {
        return profile.getProfile();
    }
}
