package org.example.individuelllabbloggbackend.services;

import org.example.individuelllabbloggbackend.configs.UserInfo;
import org.example.individuelllabbloggbackend.entities.Entry;
import org.example.individuelllabbloggbackend.exceptions.ResourceNotFoundException;
import org.example.individuelllabbloggbackend.repositories.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EntryServiceImpl implements EntryService {

    private final EntryRepository entryRepository;
    private final UserInfo userInfo;

    @Autowired
    public EntryServiceImpl(EntryRepository entryRepository, UserInfo userInfo) {
        this.entryRepository = entryRepository;
        this.userInfo = userInfo;
    }

    @Override
    public List<Entry> getAllEntries() {
        if(entryRepository.count()==0) {
            throw new ResourceNotFoundException("entries", "all entries");
        }
        return entryRepository.findAll();
    }

    @Override
    public Entry getEntryById(int id) {
        Optional<Entry> entry = entryRepository.findById(id);
        if (!entry.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("No entry exist with id: %d", id)
            );
        }
        if(!entry.get().getAuthorId().equals(userInfo.getUserId())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    String.format("You do not have permission to access this page")
            );
        }
        return entry.get();
    }

    @Override
    public Entry createEntry(Entry entry) {
        if(entry.getTitle() == null|| entry.getTitle().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Title required")
            );
        }
        if (entry.getContent() == null|| entry.getContent().length()<4) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Blog content needs to be at least three characters long")
            );
        }
        entry.setAuthorId(userInfo.getUserId());
        entry.setAuthorUsername(userInfo.getUsername());
        entry.setDateCreated(LocalDate.now());
        return entryRepository.save(entry);
    }

    @Override                                  // Uppdaterar ett blogginlägg
    public Entry updateEntry(Entry newEntry) { // (Kräver user och rätt ägare av blogginlägget)
        Optional<Entry> entry = entryRepository.findById(newEntry.getId());
        if (!entry.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("No entry exist with id: %d",newEntry.getId())
            );
        }
        Entry oldEntry = entry.get();
        if(!userInfo.getUserId().equals(oldEntry.getAuthorId())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    String.format("You do not have permission to access this page")
            );
        }
        if(newEntry.getTitle() == null|| newEntry.getTitle().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Title required")
            );
        }
        if (newEntry.getContent() == null|| newEntry.getContent().length()<4) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Blog content needs to be at least three characters long")
            );
        }
        oldEntry.setTitle(newEntry.getTitle());
        oldEntry.setContent(newEntry.getContent());
        oldEntry.setDateCreated(LocalDate.now());

        return entryRepository.save(oldEntry);
    }

    @Override
    public void deleteEntryById(int id) {
        Optional<Entry> entry = entryRepository.findById(id);
        if (!entry.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("No entry exist with id: %d", id)
            );
        }
        if (!entry.get().getAuthorId().equals(userInfo.getUserId())&&!userInfo.isAdmin()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    String.format("You do not have permission to access this page")
            );
        }
        entryRepository.deleteById(id);
    }

    @Override
    public Long getEntryCount() {
        return entryRepository.count();
    }
}
