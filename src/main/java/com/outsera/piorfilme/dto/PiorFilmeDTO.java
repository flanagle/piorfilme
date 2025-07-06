package com.outsera.piorfilme.dto;

import java.util.List;

public record PiorFilmeDTO(
        String producer,
        Integer interval,
        Integer previousWin,
        Integer followingWin
) {}
