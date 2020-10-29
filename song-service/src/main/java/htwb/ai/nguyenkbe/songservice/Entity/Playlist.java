package htwb.ai.nguyenkbe.songservice.Entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@Entity
@Table(name="playlists")
@XmlRootElement(name="songList")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String ownerId;
    private String name;
    private boolean isPrivate;

    //songs unidirectional owner
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "playlist_song",
            joinColumns = { @JoinColumn(name = "playlist_id", referencedColumnName = "id") },
            inverseJoinColumns = {@JoinColumn(name = "song_id", referencedColumnName = "id") })
    private Set<Song> songList = new HashSet<>();

    public Playlist(){};

    public void removeOwnwerInfo(){
        if (null != ownerId)
            ownerId= "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Set<Song> getsongList() {
        if(songList == null){
            songList = new HashSet<>();
        }
        return songList;
    }


    public void setsongList(Set<Song> thatsongList) {
        if (thatsongList != null){
            for(Song s: thatsongList){
                this.songList.add(s);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return isPrivate == playlist.isPrivate &&
                ownerId.equals(playlist.ownerId) &&
                Objects.equals(name, playlist.name) &&
                Objects.equals(songList, playlist.songList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, name, isPrivate, songList);
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", name='" + name + '\'' +
                ", isPrivate=" + isPrivate +
                ", songList=" + songList +
                '}';
    }
}