package org.example.individuelllabbloggbackend.services;

import org.example.individuelllabbloggbackend.entities.Entry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntryServiceImpl implements EntryService {


    @Override
    public List<Entry> getAllEntries() {
        return List.of();
    }

    @Override
    public Entry getEntryById(int id) {
        return null;
    }

    @Override
    public Entry createEntry(Entry entry) {
        return null;
    }

    @Override
    public Entry updateEntry(Entry entry) {
        return null;
    }

    @Override
    public void deleteEntryById(int id) {

    }

    @Override
    public Integer getEntryCount() {
        return 0;
    }
}
