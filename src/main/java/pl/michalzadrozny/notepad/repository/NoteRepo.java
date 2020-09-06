package pl.michalzadrozny.notepad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.michalzadrozny.notepad.entity.Note;

@Repository
public interface NoteRepo extends JpaRepository<Note, Long> {
}
