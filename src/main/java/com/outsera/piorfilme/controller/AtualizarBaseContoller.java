package com.outsera.piorfilme.controller;

import com.outsera.piorfilme.service.ILoadFilmes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class AtualizarBaseContoller {

    private final ILoadFilmes loadFilmes;

    public AtualizarBaseContoller(ILoadFilmes loadFilmes) {
        this.loadFilmes = loadFilmes;
    }

    @PostMapping("/atualizar-base")
    public ResponseEntity<String> atualizarBase(@RequestParam("file") MultipartFile file) {
        try {
            loadFilmes.reloadData(file);
            return ResponseEntity.ok().body("Base atualizada com sucesso");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Erro ao carregar o arquivo");
        }
    }
}
