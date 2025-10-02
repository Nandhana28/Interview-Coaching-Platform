package com.interviewcoaching.controllers;

import com.interviewcoaching.models.SelfNote;
import com.interviewcoaching.services.SelfNotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "http://localhost:3000")
public class SelfNotesController {

    @Autowired
    private SelfNotesService selfNotesService;

    @PostMapping
    public ResponseEntity<SelfNote> createNote(@RequestHeader("Authorization") String token,
                                              @RequestBody Map<String, Object> request) {
        String userId = extractUserIdFromToken(token);
        String title = (String) request.get("title");
        String content = (String) request.get("content");
        String category = (String) request.get("category");
        String[] tags = request.get("tags") != null ? 
            ((List<String>) request.get("tags")).toArray(new String[0]) : new String[0];
        
        SelfNote note = selfNotesService.createNote(userId, title, content, category, tags);
        return ResponseEntity.ok(note);
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<SelfNote> updateNote(@PathVariable String noteId,
                                              @RequestBody Map<String, Object> request) {
        String title = (String) request.get("title");
        String content = (String) request.get("content");
        String category = (String) request.get("category");
        String[] tags = request.get("tags") != null ? 
            ((List<String>) request.get("tags")).toArray(new String[0]) : new String[0];
        
        SelfNote updatedNote = selfNotesService.updateNote(noteId, title, content, category, tags);
        return ResponseEntity.ok(updatedNote);
    }

    @GetMapping
    public ResponseEntity<List<SelfNote>> getUserNotes(@RequestHeader("Authorization") String token) {
        String userId = extractUserIdFromToken(token);
        List<SelfNote> notes = selfNotesService.getUserNotes(userId);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<SelfNote>> getNotesByCategory(@RequestHeader("Authorization") String token,
                                                            @PathVariable String category) {
        String userId = extractUserIdFromToken(token);
        List<SelfNote> notes = selfNotesService.getNotesByCategory(userId, category);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SelfNote>> searchNotes(@RequestHeader("Authorization") String token,
                                                     @RequestParam String query) {
        String userId = extractUserIdFromToken(token);
        List<SelfNote> notes = selfNotesService.searchNotes(userId, query);
        return ResponseEntity.ok(notes);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Map<String, String>> deleteNote(@PathVariable String noteId) {
        selfNotesService.deleteNote(noteId);
        return ResponseEntity.ok(Map.of("message", "Note deleted successfully"));
    }

    private String extractUserIdFromToken(String token) {
        return "user123"; // Mock implementation
    }
}