package com.outsera.piorfilme.service;

import com.outsera.piorfilme.dto.PiorFilmeDTO;
import com.outsera.piorfilme.dto.PiorFilmeWinnerDTO;
import com.outsera.piorfilme.repository.ProducerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PiorFilmeServiceImpl implements IPiorFilmeService {

    private final ProducerRepository producerRepository;

    public PiorFilmeServiceImpl(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    @Override
    public PiorFilmeWinnerDTO piorFilme() {
        List<PiorFilmeDTO> min = producerRepository.findMinIntervals();
        List<PiorFilmeDTO> max = producerRepository.findMaxIntervals();
        return new PiorFilmeWinnerDTO(min, max);
    }
}
