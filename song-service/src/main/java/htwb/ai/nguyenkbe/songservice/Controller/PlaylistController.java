package htwb.ai.nguyenkbe.songservice.Controller;


import htwb.ai.nguyenkbe.songservice.Entity.Playlist;
import htwb.ai.nguyenkbe.songservice.Entity.Song;
import htwb.ai.nguyenkbe.songservice.Handler.IPlaylistsHandler;
import htwb.ai.nguyenkbe.songservice.Handler.ISongsHandler;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value="/songLists")
public class PlaylistController {

    private ISongsHandler songsHandler;
    private IPlaylistsHandler playlistsHandler;

    public PlaylistController(ISongsHandler sHandler, IPlaylistsHandler plHandler){
        playlistsHandler = plHandler;
        songsHandler = sHandler;
    }

    /**
     * GET /songLists?userId=ownerid
     * Get the collection of all the songs of the user
     * @param currentUserId the index of the current user
     * @param ownerId the index of the given user
     * @return ResponseEntity with the collections of the playlist in body if successful.
     */
    @GetMapping(produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity getUserPlaylists(@RequestHeader("currentUserId") String currentUserId,@RequestParam("userid") String ownerId) {
        List<Playlist> listOfPlaylist;
        boolean userAndOwnerIdentical = currentUserId.equals(ownerId);
        if (userAndOwnerIdentical) {
            listOfPlaylist = playlistsHandler.getUsersPlaylists(ownerId);
        } else {
            listOfPlaylist = playlistsHandler.getUsersPublicPlaylists(ownerId);
        }

        if (null == listOfPlaylist || listOfPlaylist.isEmpty()) {
            return ResponseEntity.notFound().eTag("Playlist for this user not found").build();
        } else {
            for(Playlist list : listOfPlaylist) {
                list.removeOwnwerInfo();
            }
            return ResponseEntity.ok(listOfPlaylist);
        }
    }

    /**
     * GET /songLists/listId
     * get the playlist by the index of the playlist
     * @param currentUserId the index of the current user
     * @param listId the index of the playlist
     * @return ResponseEntity with the playlist in body if successful
     */
    @GetMapping (value = "/{listId}", produces= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity getPlaylist(@RequestHeader("currentUserId") String currentUserId, @PathVariable("listId") int listId) {
        Playlist playlist = playlistsHandler.getUserPlaylistById(listId);
        if (null == playlist ) {
            return ResponseEntity.notFound().eTag("Playlist not found").build();
        }
        String ownerId =  playlist.getOwnerId();

        if ( ownerId.equals(currentUserId) || !playlist.isPrivate()) {
            playlist.removeOwnwerInfo();
            System.out.println("Return a Playlist");
            return ResponseEntity.ok(playlist);
        } else {
            return ResponseEntity.status(403).eTag("Private Playlist of another User").build();
        }
    }


    /**
     * POST /songLists
     * @param currentUserId the index of the current user
     * @param request HttpServletRequest
     * @param playlist the playlist in payload
     * @return ResponseEntity with the location of the playlist
     */
    @PostMapping (consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity postPlaylist(@RequestHeader("currentUserId") String currentUserId, HttpServletRequest request,@RequestBody Playlist playlist) {
        String uri = request.getRequestURI();
        int existedSongsInDB = 0;
        Collection<Song> allSongs = songsHandler.getAllSongs();
        if (null == allSongs || playlist.getsongList().size() == 0) {
            return ResponseEntity.notFound().eTag("No song found in DB or Payload").build();
        }
        String nameOfExistedSong = "";
        //Search for Songs in DB
        for (Song song : playlist.getsongList()) {
            for (Song songDB : allSongs) {
                // check if song in DB
                if (songDB.getTitle().equals(song.getTitle())){
                    existedSongsInDB++;
                    nameOfExistedSong += songDB.getTitle();
                    break;
                }
            }
        }
        // if all song in payload exist in DB
        if (existedSongsInDB == playlist.getsongList().size()) {
            // Add songId  by xml payload if needed
            for (Song s: playlist.getsongList()){
                if (0 == s.getId()){
                    s.setId(songsHandler.getSongId(s));
                }
            }
            playlist.setOwnerId(currentUserId);
            int locationOfSong = playlistsHandler.addPlaylist(playlist);
            URI resultURI = URI.create(uri+"/"+locationOfSong);
            return ResponseEntity.created(resultURI).eTag("user: "+playlist.getOwnerId()).build();
        }
        return ResponseEntity.badRequest().eTag("This song is not in the DB").build();
    }

    /**
     * PUT /songLists
     * update the playlist in the database
     * @param currentUserId the index of the current user
     * @param request HttpServletRequest
     * @param playlist the playlist
     * @return ResponseEntity
     */
    @PutMapping (consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity putPlaylist(@RequestHeader("currentUserId") String currentUserId,HttpServletRequest request,@RequestBody Playlist playlist) {
        if(playlist.getId() == 0) return ResponseEntity.noContent().eTag("playlist id not found").build();
        String payloadUserId = playlistsHandler.getOwnerofPlaylist(playlist.getId());
        if(!payloadUserId.equals(currentUserId))
            return  ResponseEntity.status(405).eTag("Only owners are allowed to make their own lists\n" +
                    "update. payload: "+payloadUserId+", current: "+currentUserId).build();
        String uri = request.getRequestURI();
        int existedSongsInDB = 0;
        Collection<Song> allSongs = songsHandler.getAllSongs();
        if (null == allSongs || playlist.getsongList().size() == 0) {
            return ResponseEntity.notFound().eTag("No song found in DB or in Payload").build();
        }

        String nameOfExistedSong = "";
        //Search for Songs in DB
        for (Song song : playlist.getsongList()) {
            for (Song songDB : allSongs) {
                // check if song in DB
                if (songDB.getTitle().equals(song.getTitle())){
                    existedSongsInDB++;
                    nameOfExistedSong += songDB.getTitle();
                    break;
                }
            }
        }
        // if all song in payload exist in DB
        if (existedSongsInDB == playlist.getsongList().size()) {
            // Add songId  by xml payload if needed
            for (Song s: playlist.getsongList()){
                if (0 == s.getId()){
                    s.setId(songsHandler.getSongId(s));
                }
            }
            playlist.setOwnerId(currentUserId);

            boolean result = playlistsHandler.updatePlaylist(playlist);
            if (result) {
                return ResponseEntity.ok("updated");
            } else {
                return ResponseEntity.badRequest().eTag("error").build();
            }
        }
        return ResponseEntity.badRequest().eTag("This song is not in the DB").build();
    }

    /**
     * DELETE /songLists/listId
     * remove the playlist in the database
     * @param currentUserId the index of the current user
     * @param listId the index of the playlist
     * @return ResponseEntity, status 204 if successful.
     */
    @DeleteMapping(value = "/{listId}")
    public ResponseEntity deletePlaylist(@RequestHeader("currentUserId") String currentUserId, @PathVariable("listId") int listId) {
        Playlist playlist = playlistsHandler.getUserPlaylistById(listId);
        if (playlist == null) {
            return ResponseEntity.notFound().eTag("Playlist not found.").build();
        }
        boolean userAndOwnerIdentical = playlist.getOwnerId().equals(currentUserId);
        if (userAndOwnerIdentical) {
            if (playlistsHandler.deletePlaylist(listId)) {
                return ResponseEntity.status(204).build();
            } else {
                return ResponseEntity.status(400).eTag("Couldn't delete Playlist").build();
            }
        } else {
            return ResponseEntity.status(403).build();
        }
    }

}