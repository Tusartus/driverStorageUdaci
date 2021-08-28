package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.IOException;

@Controller
public class NoteController {

    private final NoteService noteService;
    private  final UserService userService;

    @Autowired
    public NoteController(NoteService noteService, UserService userService){
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/uploadNote")
    public String submitNoteSave(@ModelAttribute Note note, RedirectAttributes redirectAttributes, Authentication authentication) throws IOException {
        String username = authentication.getName();
        User user = userService.getUser(username);

        try {
            if (note.getNoteId() == null) {
                note.setUserid(user.getUserId());
                noteService.saveNote(note);
                redirectAttributes.addAttribute("success", true );
                redirectAttributes.addAttribute("message", "New note " +
                        note.getNoteTitle() + "created!");
            }
            else {
                note.setUserid(user.getUserId());
                noteService.updateNote(note);
            }
        } catch (Exception e){
            redirectAttributes.addAttribute("error", true);
            redirectAttributes.addAttribute("message", "Error creating note " +
                    note.getNoteTitle() + "!");
        }
        return "redirect:/home";
    }

    @GetMapping("/deleteNote/{noteId}")
    public String deletePerIdNote(@PathVariable Integer noteId, Authentication authentication, RedirectAttributes redirectAttributes){
        String username = authentication.getName();
        User user = userService.getUser(username);

        try {
            if (user != null){
                noteService.deleteNote(noteId);
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
