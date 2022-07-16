package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialController {

    private UserService userService;
    private CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping("credentials")
    public String addCredential(Authentication authentication, @ModelAttribute Credential credential, Model model) {
        System.out.println("Adding a credential");
        User user = this.userService.getUser(authentication.getName());
        int userId = user.getUserId();
        credential.setUserId(userId);
        this.credentialService.createCredential(credential);
        return "redirect:/home";
    }

    @PostMapping("/credentials/edit")
    public String editCredential(Authentication authentication, @ModelAttribute Credential credential, Model model) {
        System.out.println("Editing a credential");
        User user = this.userService.getUser(authentication.getName());
        int userId = user.getUserId();
        credential.setUserId(userId);
        this.credentialService.updateCredential(credential);
        return "redirect:/home";
    }

    @PostMapping("/credentials/delete")
    public String deleteCredential(Authentication authentication, @ModelAttribute Credential credential, Model model) {
        System.out.println("Deleting a credential");
        int credentialId = credential.getCredentialId();
        this.credentialService.deleteCredential(credentialId);
        return "redirect:/home";
    }

}
