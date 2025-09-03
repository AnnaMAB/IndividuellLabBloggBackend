package org.example.individuelllabbloggbackend.controllers;

import org.example.individuelllabbloggbackend.entities.Entry;
import org.example.individuelllabbloggbackend.services.EntryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class BloggController {

    private final EntryServiceImpl entryService;

    public BloggController(EntryServiceImpl entryService) {
     this.entryService = entryService;
    }


    @GetMapping("/test")                                // (Det ska räcka att man är autentiserad)
    public ResponseEntity<String> testMetod(@AuthenticationPrincipal Jwt jwt) {          //Hämtar alla blogginlägg
        return ResponseEntity.ok(String.format("Entry with Id: s has been successfully deleted."));                                                 //TODO
    }

    @GetMapping("/posts")                                // (Det ska räcka att man är autentiserad)
    public ResponseEntity<List<Entry>> getAllEntries() {          //Hämtar alla blogginlägg
        return ResponseEntity.ok(entryService.getAllEntries());                                                   //TODO
    }

    @GetMapping("/post/{id}")                                // (Det ska räcka att man är autentiserad)
    public ResponseEntity<Entry> getEntry(@PathVariable("id") Integer id) {          //Hämtar ett specifikt blogginlägg
        return ResponseEntity.ok(entryService.getEntryById(id));                                                   //TODO
    }

    @PostMapping("/newpost")                                    //Skapar ett nytt blogginlägg
    public ResponseEntity<Entry> createEntry(@RequestBody Entry entry) {       //(Kräver rollen user)
        return ResponseEntity.ok(entryService.createEntry(entry));
    }

    @PutMapping("/updatepost")                                  // Uppdaterar ett blogginlägg
    public ResponseEntity<Entry> updateEntry(@RequestBody Entry entry) { // (Kräver user och rätt ägare av blogginlägget)
        return ResponseEntity.ok(entryService.updateEntry(entry));
    }

    @DeleteMapping("/deletepost/{id}") // (Kräver user, rätt ägare av blogginlägget, eller rollen admin)
    public ResponseEntity<String> deletePost(@PathVariable Integer id) {
        entryService.deleteEntryById(id);                                    // Raderar ett blogginlägg
        return ResponseEntity.ok(String.format("Entry with Id: %s has been successfully deleted.", id));
    }

    @GetMapping("/count")                       //(Kräver rollen admin)
    public ResponseEntity<Integer> getEntryCount() {        //Hämtar information om antalet blogginlägg
        return ResponseEntity.ok(entryService.getEntryCount());
    }

}
