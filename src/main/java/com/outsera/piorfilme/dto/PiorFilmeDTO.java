package com.outsera.piorfilme.dto;

public record PiorFilmeDTO(
        String producer,
        Integer interval,
        Integer previousWin,
        Integer followingWin
) {}
