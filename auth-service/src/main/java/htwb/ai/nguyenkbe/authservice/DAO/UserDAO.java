package htwb.ai.nguyenkbe.authservice.DAO;

import htwb.ai.nguyenkbe.authservice.Entity.User;
import htwb.ai.nguyenkbe.authservice.Handler.IUserHandler;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.List;

@Service
public class UserDAO implements IUserHandler {

    private static User currentUser;
    private static EntityManagerFactory entityManagerFactory;

    private String persistenceUnit;

    public UserDAO(){
        setpUnit("PU-nguyenkbe");
        createEMF();
    }

    public UserDAO(String unit){
        setpUnit(unit);
        createEMF();
    }

    private void createEMF() {
        entityManagerFactory = (Persistence.createEntityManagerFactory(persistenceUnit));
    }

    static void closeEMF() {
        entityManagerFactory.close();
    }

    public void setpUnit(String pUnit) {
        persistenceUnit = pUnit;
    }


    @Override
    public User getUser(String userId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        User user = null;
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User AS u WHERE u.userId = :userId", User.class);
            query.setParameter("userId", userId);
            if (query.getResultList().size() > 0) {
                user = query.getSingleResult();
            }
        } finally {
            em.close();
        }
        return user;
    }

    /*
    public List<User> getAllUsers(){
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

     */


}