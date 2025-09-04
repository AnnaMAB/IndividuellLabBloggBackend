package org.example.individuelllabbloggbackend.services;

import org.example.individuelllabbloggbackend.entities.Entry;

import java.util.List;

public interface EntryService {

    List<Entry> getAllEntries();
    Entry getEntryById(int id);
    Entry createEntry(Entry entry);
    Entry updateEntry(Entry entry);
    void deleteEntryById(int id);
    Long getEntryCount();

}
