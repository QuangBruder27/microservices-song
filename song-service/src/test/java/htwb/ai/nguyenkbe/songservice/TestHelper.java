package htwb.ai.nguyenkbe.songservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import htwb.ai.nguyenkbe.songservice.Entity.Playlist;
import htwb.ai.nguyenkbe.songservice.Entity.Song;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class TestHelper {

    public static String ownerId = "mmuster";

    public static Playlist initPlaylist(){
        Set<Song> songList = new HashSet<>();
        Song s5 = initSong5();
        s5.setId(5);
        songList.add(s5);
        Song s6 = initSong6();
        s6.setId(6);
        songList.add(s6);
        Playlist playlist = new Playlist();
        playlist.setOwnerId(ownerId);
        playlist.setName("New Playlist");
        playlist.setIsPrivate(true);
        playlist.setsongList(songList);
        return playlist;
    }

    public static  Song initSong(){
        return Song.builder().withArtist("Artister").withLabel("The Label").withReleased(2020).withTitle("The new Song").build();
    }

    public static Song initSong5(){
        return Song.builder().withArtist("Starship").withLabel("Grunt/RCA").withReleased(2020)
                .withTitle("We Built This City").withReleased(1985).build();
    }

    public static Song initSong6(){
        return Song.builder().withArtist("Billy Ray Cyrus").withLabel("PolyGram Mercury").withReleased(1992)
                .withTitle("Achy Breaky Heart").withReleased(1985).build();
    }

    public static Song initSong3(){
        return new Song(3,"Muskrat Love","Captain and Tennille","A&M",1976);
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
