package org.example.individuelllabbloggbackend.repositories;

import org.example.individuelllabbloggbackend.entities.Entry;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EntryRepository  extends JpaRepository<Entry, Integer> {


}
