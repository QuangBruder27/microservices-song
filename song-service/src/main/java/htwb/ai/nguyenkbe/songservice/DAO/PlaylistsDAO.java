package htwb.ai.nguyenkbe.songservice.DAO;


import htwb.ai.nguyenkbe.songservice.Entity.Playlist;
import htwb.ai.nguyenkbe.songservice.Handler.IPlaylistsHandler;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistsDAO implements IPlaylistsHandler {

    private EntityManagerFactory entityManagerFactory;

    private String persistenceUnit;

    public PlaylistsDAO() {
        setpUnit("PU-nguyenkbe");
        createEMF();
    }

    public PlaylistsDAO(String unit){
        setpUnit(unit);
        createEMF();
    }

    private void createEMF() {
        entityManagerFactory = (Persistence
                .createEntityManagerFactory(persistenceUnit));
    }

    void closeEMF() {
        entityManagerFactory.close();
    }

    public void setpUnit(String pUnit) {
        persistenceUnit = pUnit;
    }


    public List<Playlist> getUsersPlaylists(String user) {
        if (null == user) return null;
        EntityManager em = entityManagerFactory.createEntityManager();
        List<Playlist> playlists = new ArrayList<>();
        try {
            TypedQuery<Playlist> query = em.createQuery("SELECT pl from Playlist pl WHERE pl.ownerId =:ownerId", Playlist.class);
            query.setParameter("ownerId", user);
            playlists = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return playlists;
    }

    @Override
    public List<Playlist> getUsersPublicPlaylists(String user) {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<Playlist> playlists = null;
        try {
            TypedQuery<Playlist> query = em.createQuery("SELECT pl from Playlist pl WHERE pl.ownerId =:ownerId AND pl.isPrivate = false", Playlist.class);
            query.setParameter("ownerId", user);
            playlists = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return playlists;
    }

    @Override
    public Playlist getUserPlaylistById(int pid) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Playlist playlist = null;
        try {
            playlist = em.find(Playlist.class, pid);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return playlist;
    }

    @Override
    public int addPlaylist(Playlist playlist) {
        if (null == playlist) return 0;
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(playlist);
            em.getTransaction().commit();
            return playlist.getId();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersistenceException("Could not persist entity: " + e.toString());
        }
    }

    @Override
    public boolean updatePlaylist(Playlist playlist) {
        if (null == playlist) return false;
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(playlist);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersistenceException("Could not merge entity: " + e.toString());
        }
    }

    @Override
    public boolean deletePlaylist(int pid) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            Playlist list = em.find(Playlist.class, pid);
            if (list != null) {
                em.getTransaction().begin();
                em.remove(list);
                em.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersistenceException("Could not remove entity: " + e.toString());
        } finally {
            em.close();
        }
        return false;
    }

    public String getOwnerofPlaylist(int id){
        Playlist list = getUserPlaylistById(id);
        if(null == list) return null;
        return list.getOwnerId();
    }

    public List<Playlist> getAllPlaylists() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Playlist> query = entityManager.createQuery("SELECT p FROM Playlist p", Playlist.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
}