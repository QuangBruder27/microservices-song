package htwb.ai.nguyenkbe.lyricservice.DAO;

import htwb.ai.nguyenkbe.lyricservice.Entity.Lyric;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.constraints.AssertTrue;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LyricDAOTest {


    @Autowired
    LyricDAO lyricDAO;

    Lyric initLyric(){
        Lyric lyric = new Lyric();
        lyric.setSongId(12);
        lyric.setContent("This is a test");
        lyric.setTitle("Title for Test");
        return lyric;
    }

    @Test
    void findAll() {
        lyricDAO.findAll();
    }

    @Test
    void findLyricById() {
        System.out.println(lyricDAO.findLyricById(1));
    }

    @Test
    void addLyric() {
        lyricDAO.addLyric(initLyric());

    }

    @Test
    void updateLyric() {
        Lyric lyric = initLyric();
        lyric.setSongId(12);
        lyric.setTitle("update Title");
        assertTrue(lyricDAO.updateLyric(lyric));
    }


    @Test
    void isExisting() {
        assertTrue(lyricDAO.isExisting(2));
    }


    @Test
    void deleteLyric() {
        assertTrue(lyricDAO.deleteLyric(12));
    }
}