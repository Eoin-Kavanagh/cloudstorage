package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/notes")
    public String createNote(Authentication authentication, @ModelAttribute Note note, Model model) {
        System.out.println("Adding a note");
        User user = this.userService.getUser(authentication.getName());
        int userId = user.getUserId();
        note.setUserId(userId);
        this.noteService.createNote(note);
        model.addAttribute("notesList", this.noteService.getNotes(userId));
        return "home";
    }

    @PostMapping("/notes/edit")
    public String editNote(Authentication authentication, @ModelAttribute Note note, Model model) {
        System.out.println("Editing a note");
        User user = this.userService.getUser(authentication.getName());
        int userId = user.getUserId();
        note.setUserId(userId);
        this.noteService.updateNote(note);
        return "redirect:/home";
    }

    @PostMapping("/notes/delete")
    public String deleteNote(Authentication authentication, @ModelAttribute Note note, Model model) {
        System.out.println("Deleting a note");
        int noteId = note.getNoteId();
        this.noteService.deleteNote(noteId);
        return "redirect:/home";
    }
}
