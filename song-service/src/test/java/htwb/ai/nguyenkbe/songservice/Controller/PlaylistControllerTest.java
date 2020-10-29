package htwb.ai.nguyenkbe.songservice.Controller;

import htwb.ai.nguyenkbe.songservice.DAO.PlaylistsDAO;
import htwb.ai.nguyenkbe.songservice.DAO.SongsDAO;
import htwb.ai.nguyenkbe.songservice.Entity.Playlist;
import htwb.ai.nguyenkbe.songservice.Entity.Song;
import htwb.ai.nguyenkbe.songservice.TestHelper;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static htwb.ai.nguyenkbe.songservice.TestHelper.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class PlaylistControllerTest {

    private static MockMvc mockMvc;
    private static String pUnit = "TEST-PU-nguyenkbe";
    String owenerId = "mmuster";
    String currentUser = "mmuster";

    @BeforeAll
    public static void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup (
                new PlaylistController(new SongsDAO(pUnit),new PlaylistsDAO(pUnit)))
                .build();
    }

    @Test
    public void getPlaylistId3ReturnTrue() throws Exception {
        mockMvc.perform(get("/songLists/1")
                .header("currentUserId", currentUser)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getPlaylistId10ReturnNotfound() throws Exception {
        mockMvc.perform(get("/songLists/10")
                .header("currentUserId", currentUser)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getPlaylistofUserReturnTrue() throws Exception {
        mockMvc.perform(get("/songLists?userid="+owenerId)
                .header("currentUserId", currentUser)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getPlaylistofUserReturnNotfound() throws Exception {
        mockMvc.perform(get("/songLists?userid="+owenerId+"1")
                .header("currentUserId", currentUser)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getPlaylistofAnotherUserReturn403() throws Exception {
        mockMvc.perform(get("/songLists/3")
                .header("currentUserId", currentUser)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    public void postPlaylistWithoutSonginPayloadShouldReturn404() throws Exception {
        //Playlist playlist = TestHelper.initPlaylist();
        Playlist playlist = new Playlist();
        System.out.println("This is playlist"+playlist);
        mockMvc.perform(MockMvcRequestBuilders.post("/songLists")
                .header("currentUserId", currentUser)
                .content(asJsonString(playlist))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    public void postPlaylistShouldReturn201() throws Exception {
        Playlist playlist = TestHelper.initPlaylist();
        mockMvc.perform(MockMvcRequestBuilders.post("/songLists")
                .header("currentUserId", currentUser)
                .content(asJsonString(playlist))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void postPlaylistWithNonfoundSongShouldReturn400() throws Exception {
        Playlist playlist = TestHelper.initPlaylist();
        Song song3 = initSong3();
        song3.setTitle("Song 3");
        Set<Song> songList = new HashSet<>();
        songList.add(song3);
        playlist.setsongList(songList);
        mockMvc.perform(MockMvcRequestBuilders.post("/songLists")
                .header("currentUserId", currentUser)
                .content(asJsonString(playlist))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putPlaylistWithoutSonginPayloadShouldReturn404() throws Exception {
        Playlist playlist = TestHelper.initPlaylist();
        playlist.setId(0);
        mockMvc.perform(MockMvcRequestBuilders.put("/songLists")
                .header("currentUserId", currentUser)
                .content(asJsonString(playlist))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void putPlaylistShouldReturn200() throws Exception {
        Playlist playlist = TestHelper.initPlaylist();
        playlist.setId(1);
        mockMvc.perform(MockMvcRequestBuilders.put("/songLists")
                .header("currentUserId", currentUser)
                .content(asJsonString(playlist))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void putPlaylistFromAnotherUserReturn405() throws Exception {
        Playlist playlist = TestHelper.initPlaylist();
        playlist.setId(3);
        mockMvc.perform(MockMvcRequestBuilders.put("/songLists")
                .header("currentUserId", currentUser)
                .content(asJsonString(playlist))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void putPlaylistWithNonfoundSongShouldReturn400() throws Exception {
        Playlist playlist = TestHelper.initPlaylist();
        Song song3 = initSong3();
        song3.setTitle("Song 3");
        Set<Song> songList = new HashSet<>();
        songList.add(song3);
        playlist.setsongList(songList);
        mockMvc.perform(MockMvcRequestBuilders.put("/songLists")
                .header("currentUserId", currentUser)
                .content(asJsonString(playlist))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    @Test
    public void deletePlaylistShouldSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/songLists/1")
                .header("currentUserId", currentUser))
                .andExpect(status().is(204));
    }

    @Test
    public void deletePlaylistShouldReturn403() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/songLists/4")
                .header("currentUserId", currentUser))
                .andExpect(status().is(403));
    }

    @Test
    public void deletePlaylistWrondIDShouldReturn404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/songLists/100")
                .header("currentUserId", currentUser))
                .andExpect(status().is(404));
    }




}