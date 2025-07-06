package com.outsera.piorfilme.controller;

import com.outsera.piorfilme.dto.PiorFilmeWinnerDTO;
import com.outsera.piorfilme.service.PiorFilmeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PiorFilmeController {

    @Autowired
    private PiorFilmeServiceImpl piorFilmeService;

    @GetMapping("/pior-filme")
    public ResponseEntity<PiorFilmeWinnerDTO> piorFilme() {
        return ResponseEntity.ok().body(piorFilmeService.piorFilme());
    }
}
