package htwb.ai.nguyenkbe.lyricservice.Controller;

import htwb.ai.nguyenkbe.lyricservice.Handler.ILyricHandler;
import htwb.ai.nguyenkbe.lyricservice.Entity.Lyric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lyric")
public class LyricController {

   // @Autowired
    private ILyricHandler handler;

    @Autowired
    public LyricController(ILyricHandler iLyricHandler){
        handler = iLyricHandler;
    }


    /**
     * GET /lyric
     *
     * @return the collection of the lyric
     */
    @GetMapping()
    public List<Lyric> getAll(){
        return handler.findAll();
    }


    /**
     * GET /lyric/id
     * @param id the index of the lyric
     * @return ResponseEntity with the list of the specified lyric in body if successful
     * @throws IOException
     */
    @GetMapping(value="/{songId}")
    public ResponseEntity<Lyric> getLyricbyId(
            @PathVariable(value="songId") int id) throws IOException {
        ResponseEntity<String> result;
        Lyric lyric = handler.findLyricById(id);
       if (lyric != null) return  new ResponseEntity<Lyric>(lyric, HttpStatus.OK);
        return ResponseEntity.notFound().eTag("Lyric not found").build();
    }

    /**
     * POST /lyric
     * Add the new lyric
     * @param lyric the new lyric in Payload
     * @return ResponseEntity with the location of new lyric in body if successful.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addLyric(@RequestBody Lyric lyric) {
        System.out.println("post mapping func");
        if (null == lyric || !lyric.acceptable()) {
            System.out.println("wrong format");
            return ResponseEntity.badRequest().body("Wrong lyric format");
        }
        if(handler.findLyricById(lyric.getSongId()) != null) {
            System.out.println("alreay exists");
            return ResponseEntity.status(409).body("this song id already exists.");
        } else {
            Lyric newLyric = handler.addLyric(lyric);
            if (null != newLyric) {
                System.out.println("succes");
                return ResponseEntity.created(URI.create("/lyric/" + newLyric.getSongId())).build();
            } else {
                System.out.println("add fails");
                return ResponseEntity.badRequest().body("Adding fails");
            }
        }
    }

    /**
     * PUT  /lyric/id
     * @param id the index of the lyric
     * @param lyric the new lyric in Payload
     * @return ResponseEntity with the index of the lyric in body if successful
     */
    @PutMapping(value="/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateLyric( @PathVariable(value="id") Integer id, @RequestBody Lyric lyric) {
        System.out.println("id = "+ id);
        if (null != lyric && lyric.acceptable()) {
            if(id != lyric.getSongId()){
                return ResponseEntity.badRequest().body("ID mismatch");
            }
            if(!handler.isExisting(id)){
                return ResponseEntity.notFound().eTag("Lyric not found").build();
            }
            if (handler.updateLyric(lyric)) {
                return ResponseEntity.ok().body("Lyric was successfully updated. id: "+lyric.getSongId());
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.badRequest().body("Wrong lyric format");
        }
    }

    /**
     * DELETE /lyrirc/id
     * remove the specified lyric
     * @param id
     * @return ResponseEntity
     */
    @DeleteMapping(value="/{id}")
    public ResponseEntity deleteLyric(@PathVariable("id") Integer id) {
        System.out.println("delete Lyric func");
        if (handler.deleteLyric(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}
