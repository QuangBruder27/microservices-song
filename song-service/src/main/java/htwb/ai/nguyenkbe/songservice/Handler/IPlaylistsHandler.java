package htwb.ai.nguyenkbe.songservice.Handler;

import htwb.ai.nguyenkbe.songservice.Entity.Playlist;

import java.util.List;

public interface IPlaylistsHandler {

    /**
     * get the collection of all the playlists of the given user
     * @param owner the index of the user
     * @return the collection of the playlists
     */
    List<Playlist> getUsersPlaylists(String owner);

    /**
     * get the collection of all the public playlists of the given user
     * @param owner the index of the user
     * @return the collection of the playlists
     */
    List<Playlist> getUsersPublicPlaylists(String owner);

    /**
     * get the specified playlist by the given index
     * @param pid the index of the playlist
     * @return the playlist
     */
    Playlist getUserPlaylistById(int pid);

    /**
     * add the speicified playlist in the database
     * @param playlist
     * @return the index of the playlist in the database
     */
    int addPlaylist(Playlist playlist);

    /**
     * update the playlist in the database
     * @param playlist
     * @return true if the specified playlist is updated in the database
     */
    boolean updatePlaylist(Playlist playlist);

    /**
     * remove the playlist in the database
     * @param pid the index of the playlist
     * @return true if the playlist is removed
     */
    boolean deletePlaylist(int pid);

    /**
     * get the index of the owner of the playlist
     * @param id the index of the playlist
     * @return the index of the user
     */
    String getOwnerofPlaylist(int id);
}