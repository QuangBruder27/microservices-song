package htwb.ai.nguyenkbe.songservice.DAO;

import htwb.ai.nguyenkbe.songservice.Entity.Song;
import htwb.ai.nguyenkbe.songservice.Handler.ISongsHandler;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.List;

@Service
public class SongsDAO implements ISongsHandler {

    EntityManagerFactory entityManagerFactory;

    private String persistenceUnit;

    public SongsDAO(){
        setpUnit("PU-nguyenkbe");
        createEMF();
    }

    public SongsDAO(String unit){
        setpUnit(unit);
        createEMF();
    }

    private void createEMF() {
        entityManagerFactory = (Persistence
                .createEntityManagerFactory(persistenceUnit));
    }

    public void setpUnit(String pUnit) {
        persistenceUnit = pUnit;
    }


    @Override
    public Song addSong(Song song) {
        if (null == song.getTitle()) {
            return null;
        }
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Song anewSong = Song.builder()
                    .withArtist(song.getArtist())
                    .withLabel(song.getLabel())
                    .withReleased(song.getReleased())
                    .withTitle(song.getTitle())
                    .build();
            entityManager.persist(anewSong);
            transaction.commit();
            return anewSong;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fehler beim hinzufügen: " + e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Error: " + e.toString());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Song> getAllSongs() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Song> query = entityManager.createQuery("SELECT s FROM Song s", Song.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Song getSong(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Song song = null;
        try {
            song = entityManager.find(Song.class, id);
        } finally {
            entityManager.close();
        }
        return song;
    }

    @Override
    public boolean updateSong(Song song) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            if (null == em.find(Song.class, song.getId())) {
                return false;
            }
        }catch (Exception e){
            return false;
        }

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(song);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            throw new PersistenceException("Entity problems: " + e.toString());
        } finally {
            em.close();
        }
    }

    @Override
    public boolean deleteSong(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Song song = entityManager.find(Song.class, id);
            if (null == song) {
                return false;
            }
            entityManager.remove(song);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fehler beim Löschen: " + e.getMessage());
            transaction.rollback();
            throw new PersistenceException("Error: " + e.toString());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public int getSongId(Song song) {
        EntityManager em = entityManagerFactory.createEntityManager();
        int result = 0;
        if (null == song) return result;
        List<Song> list = null;
        try {
            TypedQuery<Song> query = em.createQuery("SELECT s from Song s WHERE s.title =:title", Song.class);
            query.setParameter("title", song.getTitle());
            list = query.getResultList();
            result = list.get(0).getId();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return result;
    }


    void closeEMF() {
        entityManagerFactory.close();
    }
}



