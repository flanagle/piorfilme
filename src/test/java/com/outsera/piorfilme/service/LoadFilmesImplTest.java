package com.outsera.piorfilme.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoadFilmesImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void reloadData() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "movielist.csv", "text/csv",
                new ClassPathResource("movielist.csv").getInputStream()
        );

        mockMvc.perform(multipart("/api/atualizar-base").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("atualizada com sucesso")));
    }

    @Test
    void testGetPioresFiles() throws Exception {
        mockMvc.perform(get("/api/pior-filme"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.min[0].producer").value("Joel Silver"))
                .andExpect(jsonPath("$.min[0].interval").value(1))
                .andExpect(jsonPath("$.min[0].previousWin").value(1990))
                .andExpect(jsonPath("$.min[0].followingWin").value(1991))
                .andExpect(jsonPath("$.max[0].producer").value("Buzz Feitshans"))
                .andExpect(jsonPath("$.max[0].interval").value(9))
                .andExpect(jsonPath("$.max[0].previousWin").value(1985))
                .andExpect(jsonPath("$.max[0].followingWin").value(1994));
    }
}