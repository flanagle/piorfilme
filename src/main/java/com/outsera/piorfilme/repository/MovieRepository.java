package com.outsera.piorfilme.repository;

import com.outsera.piorfilme.model.Movie;
import org.springframework.data.repository.ListCrudRepository;

public interface MovieRepository extends ListCrudRepository<Movie, Integer> {
}
