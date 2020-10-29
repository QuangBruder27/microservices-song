package htwb.ai.nguyenkbe.lyricservice.DAO;

import htwb.ai.nguyenkbe.lyricservice.Entity.Lyric;
import htwb.ai.nguyenkbe.lyricservice.Handler.ILyricHandler;
import htwb.ai.nguyenkbe.lyricservice.LyricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LyricDAO implements ILyricHandler {

    @Autowired
    private LyricRepository lyricRepository;

    /*
    @Autowired
    public LyricDAO(LyricRepository repo){
        this.lyricRepository = repo;
    }

     */

    @Override
    public List<Lyric> findAll() {
        System.out.println("Lyric DAO");
        return lyricRepository.findAll();
    }

    @Override
    public Lyric findLyricById(int songId) {
        return lyricRepository.findBysongId(songId);
    }

    @Override
    public Lyric addLyric(Lyric lyric) {
        System.out.println("Add lyric func");
        return lyricRepository.save(lyric);
    }

    @Override
    public boolean deleteLyric(int songId) {
        System.out.println("delete Lyric function");
        if (!this.isExisting(songId)) {
            System.out.println(" lyric not found");
            return false;
        }
        lyricRepository.delete(this.findLyricById(songId));
        return true;
    }

    @Override
    public boolean isExisting(int songId) {
        return findLyricById(songId) != null;
    }

    @Override
    public boolean updateLyric(Lyric lyric) {
        lyricRepository.save(lyric);
        Lyric newLyric = lyricRepository.findBysongId(lyric.getSongId());
        return newLyric.equals(lyric);
    }

}
