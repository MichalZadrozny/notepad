package pl.michalzadrozny.notepad.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.michalzadrozny.notepad.entity.Note;
import pl.michalzadrozny.notepad.repository.NoteRepo;
import pl.michalzadrozny.notepad.service.NoteService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class NoteController {

    private NoteRepo noteRepo;
    private NoteService noteService;

    @Autowired
    public NoteController(NoteRepo noteRepo, NoteService noteService) {
        this.noteRepo = noteRepo;
        this.noteService = noteService;
    }

    @GetMapping("/{id}")
    public String getNote(Model model, @PathVariable long id) {

        model.addAttribute("noteList", noteService.findAndSortNotes());
        Optional<Note> note = noteRepo.findById(id);

        if (note.isPresent()) {
            model.addAttribute("note", note.get());
        }

        return "index";
    }

    @GetMapping("/new")
    public String addNewNote(Model model) {

        model.addAttribute("noteList", noteService.findAndSortNotes());

        Note emptyNote = new Note("");
        model.addAttribute("note", emptyNote);

        return "index";
    }

    @GetMapping
    public String getNotes(Model model) {

        List<Note> noteList = noteService.findAndSortNotes();
        model.addAttribute("noteList", noteList);

        if (noteList.isEmpty()) {
            model.addAttribute("note", new Note(""));
        } else {
            model.addAttribute("note", noteList.get(0));
        }

        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable long id) {
        noteService.deleteNote(id);
        return "redirect:/";
    }

    @PostMapping("/save")
    public String saveNote(Note note) {
        if (note.getId() != null) {
            noteService.updateNote(note);
        } else {
            noteRepo.save(note);
        }

        return "redirect:/";
    }
}
