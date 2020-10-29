package htwb.ai.nguyenkbe.lyricservice;

import htwb.ai.nguyenkbe.lyricservice.Entity.Lyric;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LyricRepository extends MongoRepository<Lyric,String> {
    Lyric findBysongId(int songId);
}
