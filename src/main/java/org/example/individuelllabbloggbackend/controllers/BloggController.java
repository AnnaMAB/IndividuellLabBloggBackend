package org.example.individuelllabbloggbackend.controllers;

import org.example.individuelllabbloggbackend.entities.Entry;
import org.example.individuelllabbloggbackend.services.EntryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class BloggController {

    private final EntryServiceImpl entryService;

    @Autowired
    public BloggController(EntryServiceImpl entryService) {
     this.entryService = entryService;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Entry>> getAllEntries() {
        return ResponseEntity.ok(entryService.getAllEntries());
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Entry> getEntry(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(entryService.getEntryById(id));
    }

    @PostMapping("/newpost")
    public ResponseEntity<Entry> createEntry(@RequestBody Entry entry) {
        return ResponseEntity.ok(entryService.createEntry(entry));
    }

    @PutMapping("/updatepost")
    public ResponseEntity<Entry> updateEntry(@RequestBody Entry entry) {
        return ResponseEntity.ok(entryService.updateEntry(entry));
    }

    @DeleteMapping("/deletepost/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Integer id) {
        entryService.deleteEntryById(id);
        return ResponseEntity.ok(String.format("Entry with Id: %s has been successfully deleted.", id));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getEntryCount() {
        return ResponseEntity.ok(entryService.getEntryCount());
    }

}
