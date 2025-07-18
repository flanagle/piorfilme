package com.outsera.piorfilme.service;

import com.outsera.piorfilme.model.Movie;
import com.outsera.piorfilme.model.Producer;
import com.outsera.piorfilme.repository.MovieRepository;
import com.outsera.piorfilme.repository.ProducerRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class LoadFilmesImpl implements ILoadFilmes {

    final MovieRepository movieRepository;
    final ProducerRepository producerRepository;

    public LoadFilmesImpl(MovieRepository movieRepository, ProducerRepository producerRepository) {
        this.movieRepository = movieRepository;
        this.producerRepository = producerRepository;
    }


    @PostConstruct
    public void loadFilmes() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("movielist2.csv");

        if (inputStream == null) {
            throw new IOException("File not found");
        }

        populaBase(inputStream);

    }

    @Override
    public void reloadData(MultipartFile file) {
        deleteFilmes();
        try {
            populaBase(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void populaBase(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
//            reader.readLine(); // skip header
            String[] values = reader.readLine().split(";", -1);
            checkHeader(values);
            String line;
            while ((line = reader.readLine()) != null) {
                values = line.split(";", -1);
                Movie movie = new Movie();
                movie.setYear(Integer.parseInt(values[0].trim()));
                movie.setTitle(values[1].trim());
                movie.setStudios(values[2].trim());
                movie.setWinner("yes".equalsIgnoreCase(values[4].trim()));
                String[] producers = values[3].split(", | and ") ;
                for (String p : producers) {
                    Producer producer = producerRepository.findByName(p);
                    if (producer == null) {
                        producer = new Producer();
                        producer.setName(p);
                        producerRepository.save(producer);
                    }
                    if (movie.getProducers() == null) {
                        movie.setProducers(new java.util.ArrayList<>());
                    }
                    movie.getProducers().add(producer);
                }
                movieRepository.save(movie);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkHeader(String[] values) {

        for (int i = 0; i < values.length; i++) {
            if (i == 0 && "year".equals(values[i].trim())) {
                continue;
            }
            if (i == 1 && "title".equals(values[i].trim())) {
                continue;
            }
            if (i == 2 && "studios".equals(values[i].trim())) {
                continue;
            }
            if (i == 3 && "producers".equals(values[i].trim())) {
                continue;
            }
            if (i == 4 && "winner".equals(values[i].trim())) {
                continue;
            }
            throw new RuntimeException("Header invalido");
        }
    }

    @Override
    public void deleteFilmes() {
        movieRepository.deleteAll();
        producerRepository.deleteAll();
    }
}
