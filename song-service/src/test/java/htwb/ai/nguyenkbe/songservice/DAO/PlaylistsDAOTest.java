package htwb.ai.nguyenkbe.songservice.DAO;

import htwb.ai.nguyenkbe.songservice.Entity.Playlist;
import htwb.ai.nguyenkbe.songservice.Entity.Song;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static htwb.ai.nguyenkbe.songservice.TestHelper.initPlaylist;
import static htwb.ai.nguyenkbe.songservice.TestHelper.ownerId;
import static org.junit.jupiter.api.Assertions.*;

public class PlaylistsDAOTest {

    private static PlaylistsDAO playlistsDAO;

    @BeforeAll
    public static void setUpDao() {
        playlistsDAO = new PlaylistsDAO ("TEST-PU-nguyenkbe");
    }

    @Test
    public void getUsersPlaylistsShouldSucces(){
        List<Playlist> listofPlaylist = playlistsDAO.getUsersPlaylists(ownerId);
        assertTrue(!listofPlaylist.isEmpty());
    }

    @Test
    public void getUsersPlaylistsWithWrongUserReturnNull(){
        String wrongUser = "wronguser";
        List<Playlist> listofPlaylist = playlistsDAO.getUsersPlaylists(wrongUser);
        assertTrue(listofPlaylist.isEmpty());
    }

    @Test
    public void getUsersPublicPlaylistsShouldSucces(){
        List<Playlist> listofPlaylist = playlistsDAO.getUsersPublicPlaylists(ownerId);
        assertTrue(!listofPlaylist.isEmpty());
    }

    @Test
    public void getUserPlaylistsByIdShouldSucces(){
        Playlist playlist = playlistsDAO.getUserPlaylistById(1);
        assertTrue(playlist != null);
    }

    @Test
    public void getUserPlaylistsByWrongIdReturnNull(){
        Playlist playlist = playlistsDAO.getUserPlaylistById(100);
        assertTrue(playlist == null);
    }


    @Test
    public void addPlaylistShouldSuccess(){
        assertTrue(playlistsDAO.addPlaylist(initPlaylist()) != 0);
        for (Playlist p: playlistsDAO.getAllPlaylists()){
            System.out.println(p);
        }
    }

    @Test
    public void deletePlaylistShouldSuccess(){
        assertTrue(playlistsDAO.deletePlaylist(5));
        for (Playlist p: playlistsDAO.getAllPlaylists()){
            System.out.println(p);
        }
    }

    @Test
    public void deletePlaylistWithWrondIdReturnFalse(){
        assertTrue(!playlistsDAO.deletePlaylist(50));
        for (Playlist p: playlistsDAO.getAllPlaylists()){
            System.out.println(p);
        }
    }

    @Test
    public void testcloseEMF(){
        PlaylistsDAO playlistsDAO2 = new PlaylistsDAO ("TEST-PU-nguyenkbe");
        playlistsDAO2.closeEMF();
    }



}