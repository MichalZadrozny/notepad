package pl.michalzadrozny.notepad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.michalzadrozny.notepad.entity.Note;

import java.util.List;

@Repository
public interface NoteRepo extends JpaRepository<Note, Long> {

    @Query(value = "UPDATE Note n SET n.description = :description WHERE n.id = :id", nativeQuery = true)
    List<Note> updateNoteById(@Param("description") String description, @Param("id") long id);
}
