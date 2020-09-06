package pl.michalzadrozny.notepad.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.michalzadrozny.notepad.entity.Note;
import pl.michalzadrozny.notepad.repository.NoteRepo;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class NoteService {

    private NoteRepo noteRepo;

    @Autowired
    public NoteService(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    public List<Note> findAndSortNotes(){
        List<Note> noteList = noteRepo.findAll();
        noteList.sort(Comparator.comparing(Note::getLastModifiedDate, Comparator.reverseOrder()));
        return noteList;
    }

    public void updateNote(Note note){
        Note noteToUpdate = noteRepo.getOne(note.getId());
        noteToUpdate.setLastModifiedDate(LocalDateTime.now());
        noteToUpdate.setDescription(note.getDescription());
        noteRepo.save(noteToUpdate);
    }

    public void deleteNote(long id){
        if (noteRepo.existsById(id)) {
            noteRepo.deleteById(id);
        } else {
            log.warn("Note with id " + id + "does not exit");
        }
    }
}
