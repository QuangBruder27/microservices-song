package htwb.ai.nguyenkbe.lyricservice.Handler;

import htwb.ai.nguyenkbe.lyricservice.Entity.Lyric;

import java.util.List;

public interface ILyricHandler {
    /**
     * Find all objects of Lyric in DB
     * @return the list of Lyric
     */
    List<Lyric> findAll();

    /**
     * get the lyric by the given index
     * @param songId index of the lyric
     * @return the lyric
     */
    Lyric findLyricById(int songId);

    /**
     * Appends a new lyric to the database
     * @param lyric the given lyric
     * @return the lyric
     */
    Lyric addLyric(Lyric lyric);

    /**
     * remove the lyric in the database
     * @param songId the index of the lyric to be removed
     * @return true if this database contained the  specified lyric
     */
    boolean deleteLyric(int songId);

    /**
     *
     * @param songId the index of lyric
     * @return true if this database contained the specified lyric
     */
    boolean isExisting(int songId);

    /**
     *
     * @param lyric
     * @return true if the lyric in the database is updated
     */
    boolean updateLyric(Lyric lyric);
}
