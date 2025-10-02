package com.interviewcoaching.services;

import com.interviewcoaching.models.SelfNote;
import com.interviewcoaching.repositories.SelfNotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SelfNotesService {

    @Autowired
    private SelfNotesRepository selfNotesRepository;

    private final Map<String, String> NOTE_CATEGORIES = Map.of(
        "technical", "Technical Notes",
        "behavioral", "Behavioral Notes",
        "company-research", "Company Research",
        "interview-experience", "Interview Experience",
        "preparation-tips", "Preparation Tips",
        "questions-to-ask", "Questions to Ask"
    );

    public SelfNote createNote(String userId, String title, String content, String category, String[] tags) {
        SelfNote note = new SelfNote();
        note.setId(UUID.randomUUID().toString());
        note.setUserId(userId);
        note.setTitle(title);
        note.setContent(content);
        note.setCategory(category != null ? category : "general");
        note.setTags(tags != null ? tags : new String[0]);
        note.setCreatedAt(new Date());
        note.setUpdatedAt(new Date());

        return selfNotesRepository.save(note);
    }

    public SelfNote updateNote(String noteId, String title, String content, String category, String[] tags) {
        Optional<SelfNote> noteOpt = selfNotesRepository.findById(noteId);
        if (noteOpt.isEmpty()) {
            throw new RuntimeException("Note not found");
        }

        SelfNote note = noteOpt.get();
        if (title != null) note.setTitle(title);
        if (content != null) note.setContent(content);
        if (category != null) note.setCategory(category);
        if (tags != null) note.setTags(tags);
        note.setUpdatedAt(new Date());

        return selfNotesRepository.save(note);
    }

    public List<SelfNote> getUserNotes(String userId) {
        return selfNotesRepository.findByUserIdOrderByUpdatedAtDesc(userId);
    }

    public List<SelfNote> getNotesByCategory(String userId, String category) {
        return selfNotesRepository.findByUserIdAndCategory(userId, category);
    }

    public List<SelfNote> searchNotes(String userId, String query) {
        List<SelfNote> allNotes = selfNotesRepository.findByUserIdOrderByUpdatedAtDesc(userId);
        return allNotes.stream()
                .filter(note -> note.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                               note.getContent().toLowerCase().contains(query.toLowerCase()) ||
                               (note.getTags() != null && Arrays.stream(note.getTags())
                                       .anyMatch(tag -> tag.toLowerCase().contains(query.toLowerCase()))))
                .collect(Collectors.toList());
    }

    public SelfNote getNoteById(String noteId) {
        return selfNotesRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));
    }

    public boolean deleteNote(String noteId) {
        if (selfNotesRepository.existsById(noteId)) {
            selfNotesRepository.deleteById(noteId);
            return true;
        }
        return false;
    }

    public Map<String, Object> getNotesStatistics(String userId) {
        List<SelfNote> allNotes = getUserNotes(userId);
        
        Map<String, Long> notesByCategory = allNotes.stream()
                .collect(Collectors.groupingBy(SelfNote::getCategory, Collectors.counting()));
        
        long totalNotes = allNotes.size();
        long notesThisMonth = allNotes.stream()
                .filter(note -> isThisMonth(note.getUpdatedAt()))
                .count();
        
        // Get most used tags
        Map<String, Long> popularTags = allNotes.stream()
                .filter(note -> note.getTags() != null)
                .flatMap(note -> Arrays.stream(note.getTags()))
                .collect(Collectors.groupingBy(tag -> tag, Collectors.counting()));
        
        // Get recent activity
        List<SelfNote> recentNotes = allNotes.stream()
                .limit(5)
                .collect(Collectors.toList());

        return Map.of(
            "totalNotes", totalNotes,
            "notesThisMonth", notesThisMonth,
            "notesByCategory", notesByCategory,
            "popularTags", popularTags.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .limit(10)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
            "recentNotes", recentNotes,
            "categories", NOTE_CATEGORIES
        );
    }

    public List<SelfNote> getNotesByTags(String userId, String tag) {
        return selfNotesRepository.findByUserIdAndTagsContaining(userId, tag);
    }

    public SelfNote duplicateNote(String noteId, String userId) {
        SelfNote originalNote = getNoteById(noteId);
        
        if (!originalNote.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized to duplicate this note");
        }

        SelfNote duplicatedNote = new SelfNote();
        duplicatedNote.setId(UUID.randomUUID().toString());
        duplicatedNote.setUserId(userId);
        duplicatedNote.setTitle(originalNote.getTitle() + " (Copy)");
        duplicatedNote.setContent(originalNote.getContent());
        duplicatedNote.setCategory(originalNote.getCategory());
        duplicatedNote.setTags(originalNote.getTags() != null ? 
            Arrays.copyOf(originalNote.getTags(), originalNote.getTags().length) : new String[0]);
        duplicatedNote.setCreatedAt(new Date());
        duplicatedNote.setUpdatedAt(new Date());

        return selfNotesRepository.save(duplicatedNote);
    }

    private boolean isThisMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);
        
        calendar.setTime(date);
        int noteMonth = calendar.get(Calendar.MONTH);
        int noteYear = calendar.get(Calendar.YEAR);
        
        return noteMonth == currentMonth && noteYear == currentYear;
    }

    public Map<String, String> getNoteCategories() {
        return new HashMap<>(NOTE_CATEGORIES);
    }
}