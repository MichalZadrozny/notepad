package pl.michalzadrozny.notepad.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.michalzadrozny.notepad.entity.Note;
import pl.michalzadrozny.notepad.repository.NoteRepo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Controller
public class NoteController {

    private NoteRepo noteRepo;

    @Autowired
    public NoteController(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    @GetMapping
    public String getNotes(Model model){

        List<Note> noteList = noteRepo.findAll();
        Collections.sort(noteList, Comparator.comparing(Note::getLastModifiedDate));

        model.addAttribute("noteList", noteList);

        return "index";
    }
}
