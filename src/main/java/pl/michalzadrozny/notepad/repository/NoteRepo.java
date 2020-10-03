package pl.michalzadrozny.notepad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.michalzadrozny.notepad.entity.Note;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepo extends JpaRepository<Note, Long> {

    List<Note> findAllByOrderByLastModifiedDateDesc();
}
