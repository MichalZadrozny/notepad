package pl.michalzadrozny.notepad.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.michalzadrozny.notepad.entity.Note;
import pl.michalzadrozny.notepad.repository.NoteRepo;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NoteService {

    private NoteRepo noteRepo;

    @Autowired
    public NoteService(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    public List<Note> findSortedNotes(){
        List<Note> noteList = noteRepo.findAllByOrderByLastModifiedDateDesc();
        return noteList;
    }

    public void updateNote(Note note){
        Optional<Note> noteToUpdate = Optional.of(noteRepo.getOne(note.getId()));

        if(noteToUpdate.isPresent()){
            noteToUpdate.get().setLastModifiedDate(LocalDateTime.now());
            noteToUpdate.get().setDescription(note.getDescription());
            noteRepo.save(noteToUpdate.get());
        }

    }

    public void deleteNote(long id){
        if (noteRepo.existsById(id)) {
            noteRepo.deleteById(id);
        } else {
            log.warn("Note with id " + id + "does not exit");
        }
    }
}
