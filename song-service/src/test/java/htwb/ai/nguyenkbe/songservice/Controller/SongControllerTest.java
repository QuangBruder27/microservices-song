package htwb.ai.nguyenkbe.songservice.Controller;

import htwb.ai.nguyenkbe.songservice.DAO.SongsDAO;
import htwb.ai.nguyenkbe.songservice.Entity.Song;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static htwb.ai.nguyenkbe.songservice.TestHelper.asJsonString;
import static htwb.ai.nguyenkbe.songservice.TestHelper.initSong3;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SongControllerTest {

    private static MockMvc mockMvc;

    @BeforeAll
    public static void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup (
                new SongController(new SongsDAO("TEST-PU-nguyenkbe"))).build();
    }

    @Test
    public void getAllSongs() throws Exception {
        mockMvc.perform(get("/songs"))
                .andExpect(status().isOk());
    }

    @Test
    public void getSongId3ReturnTrue() throws Exception {
        mockMvc.perform(get("/songs/3")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(initSong3())));
    }

    @Test
    public void postSongShouldReturn201() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/songs")
                .content(asJsonString(initSong3()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
    }

    @Test
    public void postSongwithOutTitleShouldReturn422() throws Exception {
        Song s = initSong3();
        s.setTitle("");
        mockMvc.perform(MockMvcRequestBuilders.post("/songs")
                .content(asJsonString(s))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(422));
    }

    @Test
    public void postSongwithWrongFormatShouldReturn400() throws Exception {
        Song s = initSong3();
        s.setTitle("");
        mockMvc.perform(MockMvcRequestBuilders.post("/songs")
                .content(asJsonString(null))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }


    @Test
    public void putSongShouldReturn200() throws Exception {
        Song s = initSong3();
        s.setId(9);
        mockMvc.perform(MockMvcRequestBuilders.put("/songs/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(s)))
                .andExpect(status().is(200));
    }

    @Test
    public void putSongWithouPayloadIdReturn400() throws Exception {
        Song s = initSong3();
        s.setId(0);
        mockMvc.perform(MockMvcRequestBuilders.put("/songs/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(s)))
                .andExpect(status().is(400));
    }

    @Test
    public void putSongWithouTitleReturn400() throws Exception {
        Song s = initSong3();
        s.setTitle("");
        mockMvc.perform(MockMvcRequestBuilders.put("/songs/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(s)))
                .andExpect(status().is(400));
    }


    @Test
    public void putSongShouldWithIdmismatchReturn400() throws Exception {
        Song s = initSong3();
        s.setId(4);
        mockMvc.perform(MockMvcRequestBuilders.put("/songs/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(s)))
                .andExpect(status().is(400));
    }


    @Test
    public void putSongReturnNotFound() throws Exception {
        Song s = initSong3();
        s.setId(15);
        mockMvc.perform(MockMvcRequestBuilders.put("/songs/15")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(s)))
                .andExpect(status().is(404));
    }

    @Test
    public void getSongId500ReturnNotFound() throws Exception {
        mockMvc.perform(get("/songs/500"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteSongWrondIDShouldReturn404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/songs/500"))
                .andExpect(status().is(404));
    }

    /*
    @Test
    public void deleteSongReturn201() throws Exception {
        setUp();
        mockMvc.perform(MockMvcRequestBuilders.delete("/songs/1"))
                .andExpect(status().isOk());
    }


     */





}