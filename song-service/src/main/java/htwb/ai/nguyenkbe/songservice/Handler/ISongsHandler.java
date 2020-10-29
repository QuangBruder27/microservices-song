package htwb.ai.nguyenkbe.songservice.Handler;

import htwb.ai.nguyenkbe.songservice.Entity.Song;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ISongsHandler {

    /**
     * Add the new song in the database
     * @param song without the index
     * @return the new song with the index
     */
    Song addSong(Song song);

    /**
     * get all songs in the database
     * @return a collection of the songs.
     */
    List<Song> getAllSongs() ;

    /**
     * get the specified song
     * @param id the index of the specified song
     * @return the song
     */
    Song getSong(int id) ;

    /**
     * update the sond in the database
     * @param song
     * @return true if the song is updated
     */
    boolean updateSong(Song song) ;

    /**
     * remove the song in the database
     * @param id the index of the specified song
     * @return true if the song is removed
     */
    boolean deleteSong(int id) ;

    /**
     * get the index of the song
     * @param song
     * @return the index of the specified song
     */
    int getSongId(Song song);
}
