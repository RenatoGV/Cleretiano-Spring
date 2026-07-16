package com.rengv.cleretiano.repository;

import com.rengv.cleretiano.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
