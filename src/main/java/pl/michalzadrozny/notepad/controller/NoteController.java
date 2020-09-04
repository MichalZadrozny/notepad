package pl.michalzadrozny.notepad.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.michalzadrozny.notepad.entity.Note;
import pl.michalzadrozny.notepad.repository.NoteRepo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class NoteController {

    private NoteRepo noteRepo;

    @Autowired
    public NoteController(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    @GetMapping("/{id}")
    public String getNotes(Model model, @PathVariable long id) {

        List<Note> noteList = noteRepo.findAll();
        noteList.sort(Comparator.comparing(Note::getLastModifiedDate));
        model.addAttribute("noteList", noteList);

        Optional<Note> firstNote = noteRepo.findById(id);

        if (firstNote.isPresent()) {
            model.addAttribute("note", firstNote.get());
        }

        return "index";
    }

    @GetMapping
    public String getNotes(Model model) {

        List<Note> noteList = noteRepo.findAll();
        noteList.sort(Comparator.comparing(Note::getLastModifiedDate));
        model.addAttribute("noteList", noteList);

        if (noteList.isEmpty()) {
            Note emptyNote = new Note("");
            model.addAttribute("note", emptyNote);
        } else {
            Note note = noteList.get(0);
            model.addAttribute("note", note);
        }

        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable long id) {
        if (noteRepo.existsById(id)) {
            log.info("Deleting note with id: " + id);
            noteRepo.deleteById(id);
        } else {
            log.warn("Note with id " + id + "does not exit");
        }
        return "redirect:/";
    }

    @GetMapping("/save/{id}")
    public String saveNote(@PathVariable long id) {
        if (noteRepo.existsById(id)) {
            log.info("Saving note with id: " + id);
//            noteRepo
        } else {
            log.warn("Note with id " + id + "does not exit");
        }
        return "redirect:/";
    }
}
