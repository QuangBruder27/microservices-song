package htwb.ai.nguyenkbe.lyricservice.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "songlyric")
public class Lyric {

    @Id
    private int songId;
    private String title;
    private String content;

    public Lyric() {
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Lyric(int songId, String title, String content) {
        this.songId = songId;
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Lyric{" +
                "songId=" + songId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public boolean equals(Lyric that) {
        return this.getContent().equals(that.getContent())
                && this.getSongId() == that.getSongId()
                && this.getTitle().equals(that.getTitle());
    }

    public boolean acceptable(){
        return this.getSongId() != 0
                && !this.getTitle().isEmpty()
                && !this.getContent().isEmpty();
    }

}
