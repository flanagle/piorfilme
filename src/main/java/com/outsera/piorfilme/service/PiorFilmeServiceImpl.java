package com.outsera.piorfilme.service;

import com.outsera.piorfilme.dto.PiorFilmeDTO;
import com.outsera.piorfilme.dto.PiorFilmeWinnerDTO;
import com.outsera.piorfilme.model.Producer;
import com.outsera.piorfilme.repository.ProducerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PiorFilmeServiceImpl implements IPiorFilmeService {

    private final ProducerRepository producerRepository;

    public PiorFilmeServiceImpl(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    @Override
    public PiorFilmeWinnerDTO piorFilme() {
        List<PiorFilmeDTO> min = producerRepository.findMinIntervals();
        List<PiorFilmeDTO> max = findMaxIntervals();
//        List<PiorFilmeDTO> max = producerRepository.findMaxIntervals();
        return new PiorFilmeWinnerDTO(min, max);
    }

    private List<PiorFilmeDTO> findMaxIntervals() {
        List<Producer> producers = producerRepository.findAllDistinctNames();

        List<PiorFilmeDTO> result = new ArrayList<>();

        for (Producer producer : producers) {
            List<Integer> winYears = producerRepository.findWinYears(producer.getId());
            for(int i=0; i < winYears.size()-1; i++) {
                int prev = winYears.get(i);
                int curr = winYears.get(i+1);
                result.add(new PiorFilmeDTO(producer.getName(), curr - prev, prev, curr));
            }
        }

        int maxInterval = result.stream().mapToInt(PiorFilmeDTO::interval)
                .max()
                .orElse(0);

        return result.stream()
                .filter(p -> p.interval() == maxInterval)
                .collect(Collectors.toList());
    }
}

