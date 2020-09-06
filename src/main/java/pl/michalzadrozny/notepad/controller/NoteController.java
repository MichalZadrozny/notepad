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

import java.time.LocalDateTime;
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
    public String getNote(Model model, @PathVariable long id) {

        List<Note> noteList = noteRepo.findAll();
        noteList.sort(Comparator.comparing(Note::getLastModifiedDate));
        model.addAttribute("noteList", noteList);

        Optional<Note> note = noteRepo.findById(id);

        if (note.isPresent()) {
            model.addAttribute("note", note.get());
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

    @PostMapping("/save")
    public String saveNote(Note note) {
        if (note.getId() != null) {
            log.info("Saving note with id: " + note.getId());
            Note noteToUpdate = noteRepo.getOne(note.getId());
            noteToUpdate.setLastModifiedDate(LocalDateTime.now());
            noteToUpdate.setDescription(note.getDescription());
            noteRepo.save(noteToUpdate);
        } else {
            noteRepo.save(note);
        }

        return "redirect:/";
    }
}
