package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class FileController {

    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/uploadFile")
    public String submit(@RequestParam("file") MultipartFile file, Authentication authentication, RedirectAttributes redirectAttributes, Model model) throws IOException {
        String username = authentication.getName();
        User user = userService.getUser(username);

        try {
            if(file.isEmpty()){
                redirectAttributes.addAttribute("error", true);
                redirectAttributes.addAttribute("message", "Please select a valid file!");
                return "redirect:/home";
            }
            if(fileService.isFileNameDuplicate(file.getOriginalFilename(), user.getUserId())){
                redirectAttributes.addAttribute("error", true);
                redirectAttributes.addAttribute("message", "Duplicate file!");
                return "redirect:/home";
            }
            int fileId = fileService.saveFile(file, user.getUserId());
            redirectAttributes.addAttribute("success", true);
            redirectAttributes.addAttribute("message", "Your file is uploaded!");
        } catch (Exception e){
            redirectAttributes.addAttribute("error", true);
            redirectAttributes.addAttribute("message", "File uploading error!");
        }
        return "redirect:/home";
    }

    @GetMapping("/deleteFile/{fileId}")
    public String deleteFile(@PathVariable int fileId, Authentication authentication, RedirectAttributes redirectAttributes){
        String username = authentication.getName();
        User user = userService.getUser(username);

        try {
            if (user != null){
                fileService.deleteFile(fileId);
                redirectAttributes.addAttribute("success", true);
                redirectAttributes.addAttribute("message", "Your file is deleted!");
            }
        } catch (Exception e){
            redirectAttributes.addAttribute("error", true);
            redirectAttributes.addAttribute("message", "File deleting error!");
        }
        return "redirect:/home";
    }

    @GetMapping("/viewFile/{fileId}")
    public ResponseEntity viewFile(@PathVariable("fileId") int fileId){
        File file = fileService.getFilebyFileId(fileId);
        String fileName = file.getFileName();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\""+fileName+"\"")
                .body(file.getFileData());

    }

}

