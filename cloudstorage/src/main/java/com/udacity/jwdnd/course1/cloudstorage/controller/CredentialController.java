package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class CredentialController {

    private UserService userService;
    private CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService){
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping("/uploadCredential")
    public String submitCredentialUser(@ModelAttribute Credential credential, RedirectAttributes redirectAttributes, Authentication authentication) throws IOException {
        String username = authentication.getName();
        User user = userService.getUser(username);

        try {
            if (credential.getCredentialId() == null) {
                credential.setUserid(user.getUserId());
                credentialService.saveCredential(credential);
                redirectAttributes.addAttribute("success", true );
                redirectAttributes.addAttribute("message", "New credential " +
                        credential.getCredentialId() + "created!");
            }
            else {
                credentialService.updateCredential(credential);
                redirectAttributes.addAttribute("success", true );
                redirectAttributes.addAttribute("message", "Credential " +
                        credential.getCredentialId() + "updated!");
            }
        } catch (Exception e){
            redirectAttributes.addAttribute("error", true);
            redirectAttributes.addAttribute("message", credential.getUserid() + credential.getUrl() + credential.getUserName() + credential.getPassWord() + "key:" + credential.getKey()+
                    credential.getCredentialId() + "!");
        }
        return "redirect:/home";
    }

    @GetMapping("/deleteCredential/{credentialId}")
    public String deleteNotes(@PathVariable Integer credentialId, Authentication authentication, RedirectAttributes redirectAttributes){
        String username = authentication.getName();
        User user = userService.getUser(username);

        try {
            if (user != null){
                credentialService.deleteCredential(credentialId);
                redirectAttributes.addAttribute("success", true);
                redirectAttributes.addAttribute("message", "Your note is deleted!");
            }
        } catch (Exception e){
            redirectAttributes.addAttribute("error", true);
            redirectAttributes.addAttribute("message", "Note deleting error!");
        }
        return "redirect:/home";

    }


}
