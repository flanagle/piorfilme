package com.outsera.piorfilme.repository;

import com.outsera.piorfilme.dto.PiorFilmeDTO;
import com.outsera.piorfilme.model.Producer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface ProducerRepository extends ListCrudRepository<Producer, Integer> {


    @Query("""
        SELECT NEW com.outsera.piorfilme.dto.PiorFilmeDTO(
             p.name,
             m2.year - m1.year,
             m1.year,
             m2.year
         )
         FROM Producer p
         JOIN p.movies m1
         JOIN p.movies m2
         WHERE m1.winner = true
           AND m2.winner = true
           AND m2.year > m1.year
           AND NOT EXISTS (
               SELECT 1
               FROM Movie mBetween
               JOIN mBetween.producers pb
               WHERE pb = p
                 AND mBetween.winner = true
                 AND mBetween.year > m1.year
                 AND mBetween.year < m2.year
         )
         AND (m2.year - m1.year) = (
             SELECT MIN(m4.year - m3.year)
             FROM Producer p2
             JOIN p2.movies m3
             JOIN p2.movies m4
             WHERE m3.winner = true
               AND m4.winner = true
               AND m4.year > m3.year
               AND NOT EXISTS (
                   SELECT 1
                   FROM Movie mBetween2
                   JOIN mBetween2.producers pb2
                   WHERE pb2 = p2
                     AND mBetween2.winner = true
                     AND mBetween2.year > m3.year
                     AND mBetween2.year < m4.year
               )
         )
         ORDER BY p.name
    """)
    List<PiorFilmeDTO> findMinIntervals();

    @Query("""
        SELECT NEW com.outsera.piorfilme.dto.PiorFilmeDTO(
             p.name,
             m2.year - m1.year,
             m1.year,
             m2.year
         )
         FROM Producer p
         JOIN p.movies m1
         JOIN p.movies m2
         WHERE m1.winner = true
           AND m2.winner = true
           AND m2.year > m1.year
           AND NOT EXISTS (
               SELECT 1
               FROM Movie mBetween
               JOIN mBetween.producers pb
               WHERE pb = p
                 AND mBetween.winner = true
                 AND mBetween.year > m1.year
                 AND mBetween.year < m2.year
         )
         AND (m2.year - m1.year) = (
             SELECT MAX(m4.year - m3.year)
             FROM Producer p2
             JOIN p2.movies m3
             JOIN p2.movies m4
             WHERE m3.winner = true
               AND m4.winner = true
               AND m4.year > m3.year
               AND NOT EXISTS (
                   SELECT 1
                   FROM Movie mBetween2
                   JOIN mBetween2.producers pb2
                   WHERE pb2 = p2
                     AND mBetween2.winner = true
                     AND mBetween2.year > m3.year
                     AND mBetween2.year < m4.year
               )
         )
         ORDER BY p.name
    """)
    List<PiorFilmeDTO> findMaxIntervals();

    Producer findByName(String name);
}
