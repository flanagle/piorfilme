package com.outsera.piorfilme.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ILoadFilmes {

    void loadFilmes() throws IOException;

    void reloadData(MultipartFile file) throws IOException;

    void deleteFilmes();
}
