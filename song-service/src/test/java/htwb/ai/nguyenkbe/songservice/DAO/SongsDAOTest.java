package htwb.ai.nguyenkbe.songservice.DAO;

import htwb.ai.nguyenkbe.songservice.Entity.Song;
import htwb.ai.nguyenkbe.songservice.TestHelper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class SongsDAOTest {

    private static SongsDAO songsDAO;

    @BeforeAll
    public static void setUpDao() {
        songsDAO = new SongsDAO ("TEST-PU-nguyenkbe");
    }

    @Test
    public void getWrongSongbyIdShouldFail(){
        Song gettingSong = songsDAO.getSong(50);
        assertTrue(gettingSong == null);
    }

    @Test
    public void addSongWithoutTitleShouldFail(){
        int defaultNumber = songsDAO.getAllSongs().size();
        songsDAO.addSong(new Song());
        assertTrue(songsDAO.getAllSongs().size()==defaultNumber);
    }

    @Test
    public void addSongShouldSucces(){
        Song newSong = songsDAO.addSong(TestHelper.initSong());
        List<Song> songList = songsDAO.getAllSongs();
        assertTrue(songList.contains(newSong));
    }

    @Test
    public void getSongbyIdShouldSucces(){
        Song gettingSong = songsDAO.getSong(5);
        List<Song> songList = songsDAO.getAllSongs();
        assertTrue(songList.get(4).equals(gettingSong));
    }

    @Test
    public void updateSongShouldSucces(){
        Song newSong = TestHelper.initSong();
        newSong.setId(8);
        Assert.assertTrue(songsDAO.updateSong(newSong));
        List<Song> songList = songsDAO.getAllSongs();
        assertTrue(songList.get(7).equals(newSong));
    }

    @Test
    public void deleteSongShouldSucces(){
        int defaultNumber = songsDAO.getAllSongs().size();
        songsDAO.deleteSong(10);
        List<Song> list = songsDAO.getAllSongs();
        Assert.assertTrue( list.size()== defaultNumber-1);
        Assert.assertTrue(songsDAO.getSong(10) == null);
    }

    @Test
    public void getIdFromSongShouldSucces(){
        int id = songsDAO.getSongId(TestHelper.initSong5());
        assertTrue(id == 5);
    }

    @Test
    public void testCloseEMF(){
        SongsDAO songsDAO2 = new SongsDAO ("TEST-PU-nguyenkbe");
        songsDAO2.closeEMF();
    }
}