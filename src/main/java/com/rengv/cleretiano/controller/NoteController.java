package com.rengv.cleretiano.controller;

import com.rengv.cleretiano.dto.NoteRequestDTO;
import com.rengv.cleretiano.dto.NoteResponseDTO;
import com.rengv.cleretiano.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<NoteResponseDTO> findAll() {
        return noteService.findAll();
    }

    @GetMapping("/{id}")
    public NoteResponseDTO findById(@PathVariable Long id) {
        return noteService.findById(id);
    }

    @PostMapping
    public NoteResponseDTO create(@Valid @RequestBody NoteRequestDTO request) {
        return noteService.create(request);
    }

    @PutMapping("/{id}")
    public NoteResponseDTO update(@PathVariable Long id, @Valid @RequestBody NoteRequestDTO request) {
        return noteService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        noteService.delete(id);
    }
}
