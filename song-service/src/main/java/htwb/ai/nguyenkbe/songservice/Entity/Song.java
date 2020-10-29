package htwb.ai.nguyenkbe.songservice.Entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@Entity
@Table(name="songs")
@XmlRootElement(name = "song")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String artist;
    private String label;
    private int released;


    /**
     * Creates builder to build User.
     * @return created builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder{
        private Integer id;
        private String title;
        private String artist;
        private String label;
        private int released;

        private Builder(){}

        public Builder withTitle(String title){
            if(null != title) this.title = title;
            return  this;
        }
        public Builder withArtist(String artist){
            if(null != artist) this.artist = artist;
            return this;
        }
        public Builder withLabel(String label){
            if(label != null) this.label = label;
            return this;
        }
        public Builder withReleased(int released){
            if(0 != released) this.released = released;
            return this;
        }

        public Song build(){
            return new Song(this);
        }
    }

    public Song() {
    }

    public Song(int sid, String stitle, String sartist, String slabel, int sreleased){
        id = sid;
        title = stitle;
        artist = sartist;
        label = slabel;
        released = sreleased;
    }

    public Song(Builder builder){
        this.id = builder.id;
        this.title = builder.title;
        this.artist = builder.artist;
        this.label = builder.label;
        this.released = builder.released;
    }


    public int getId() {
        if (null == id) return 0;
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getLabel() {
        return label;
    }

    public int getReleased() {
        return released;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setReleased(int released) {
        this.released = released;
    }

    @Override
    public String toString() {
        return "Song [id=" + id + ", title:" + title + ", artist:" + artist + ", label:" + label + ", released:"
                + released + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return released == song.released &&
                title.equals(song.title) &&
                artist.equals(song.artist) &&
                label.equals(song.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, artist, label, released);
    }
}

