package htwb.ai.nguyenkbe.songservice.Controller;

import htwb.ai.nguyenkbe.songservice.Entity.Song;
import htwb.ai.nguyenkbe.songservice.Handler.ISongsHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;


@RestController
@RequestMapping(value="/songs")
public class SongController {

    private ISongsHandler iSongsHandler;

    public SongController(ISongsHandler iSongsHandler) {
        System.out.println("Constructor 1");
        this.iSongsHandler = iSongsHandler;
    }

    /**
     * GET /songs
     * @return the collection of all the songs in the database
     */
    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public Collection<Song> getAllSongs() {
        System.out.println("func get all  Songs");
        Collection<Song> result = iSongsHandler.getAllSongs();
        return result;
    }

    /**
     * GET /songs/id
     * @param id the index of the song
     * @return ResponseEntity with the song in body if successful
     * @throws IOException
     */
    @GetMapping(value="/{id}")
    public ResponseEntity getSongbyId(
            @PathVariable(value="id") Integer id) throws IOException {
        Song song = iSongsHandler.getSong(id);
        if (song != null) {
            return new ResponseEntity<Song>(song, HttpStatus.OK);
        }
        return ResponseEntity.notFound().eTag("Song not found").build();
    }

    /**
     * POST /songs/
     * Add the new song in the database
     * @param song the new song in payload
     * @return ResponseEntity with the location of the new song
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addsong(@RequestBody Song song) {
        System.out.println("post mapping func");
        if (null != song) {
            if(null == song.getTitle() || song.getTitle().equals("")){
                return ResponseEntity.unprocessableEntity().body("Song title is missing");
            }
            Song newSong = iSongsHandler.addSong(song);
            if (null != newSong) {
                return ResponseEntity.created(URI.create("/songs/" + newSong.getId())).build();
            } else {
                return ResponseEntity.badRequest().body("Adding fails");
            }
        } else {
            return ResponseEntity.badRequest().body("Wrong song format");
        }

    }

    /**
     * PUT  /songs/id
     * update the song in the database
     * @param id the index of the song
     * @param song the new song in payload
     * @return ResponseEntity with the index of the song in body
     */
    @PutMapping(value="/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateSong( @PathVariable(value="id") Integer id, @RequestBody Song song) {
        if (null != song) {
            if (0 == song.getId()) {
                return ResponseEntity.badRequest().body("id in payload missing");
            }
            if(id != song.getId()){
                return ResponseEntity.badRequest().body("ID mismatch");
            }
            if (null == song.getTitle() || song.getTitle().isEmpty()) {
                return ResponseEntity.badRequest().body("title not found");
            }
            song.setId(id);
            if (iSongsHandler.updateSong(song)) {
                return ResponseEntity.ok().body("Song was successfully updated. id: "+song.getId());
            } else {
                return ResponseEntity.notFound().build(); // song not found
            }
        } else {
            return ResponseEntity.badRequest().body("Wrong song format");
        }
    }

    /**
     * DELETE /songs/id
     * remove the song in the database
     * @param id the index of the song
     * @return ResponseEntity, the status isOK if the song is removed.
     */
    @DeleteMapping(value="/{id}")
    public ResponseEntity deleteSong(@PathVariable("id") Integer id) {
        if (iSongsHandler.deleteSong(id)) {
            return ResponseEntity.ok().body("Deleted.");
        } else {
            return ResponseEntity.notFound().eTag("Song not found with id "+id).build();
        }
    }


}
