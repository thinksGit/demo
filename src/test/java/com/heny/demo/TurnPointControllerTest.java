package com.heny.demo;

import com.heny.demo.one.controller.TurnPointController;
import com.heny.demo.one.service.TurnPointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TurnPointControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TurnPointService turnPointService;

    @InjectMocks
    private TurnPointController turnPointController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(turnPointController).build();
    }

    @Test
    public void uploadTurnPoint_FileUploadedSuccessfully_ReturnsTrue() throws Exception {
        MockMultipartFile file = new MockMultipartFile("turnPointFile", "file/J1-J8.txt", MediaType.TEXT_PLAIN_VALUE, "test content".getBytes());
        when(turnPointService.uploadTurnPoint(file)).thenReturn(true);

        mockMvc.perform(multipart("/upload")
                .file(file))
                .andExpect(status().isOk());
    }

    @Test
    public void uploadTurnPoint_FileUploadFails_ReturnsFalse() throws Exception {
        MockMultipartFile file = new MockMultipartFile("turnPointFile", "file/J1-J8.txt", MediaType.TEXT_PLAIN_VALUE, "test content".getBytes());
        when(turnPointService.uploadTurnPoint(file)).thenReturn(false);

        mockMvc.perform(multipart("/upload")
                .file(file))
                .andExpect(status().isOk());
    }
}
