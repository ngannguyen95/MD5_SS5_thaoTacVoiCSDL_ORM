package rikkei.serviceImpl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import rikkei.model.entity.Customer;
import rikkei.model.entity.Song;
import rikkei.service.ISongService;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
public class SongServiceIpm implements ISongService {
    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;
    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.conf.xml")
                    .buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }
    @Override
    public List<Song> findALl() {
        String queryStr ="select s from Song as s";
        TypedQuery<Song> query = entityManager.createQuery(queryStr,Song.class);
        return query.getResultList();
    }

    @Override
    public void save(Song song) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            if (song.getId()!=0){
                Song song1 = findById(song.getId());
                song1.setSongName(song.getSongName());
                song1.setAuthor(song.getAuthor());
                song1.setCatalog(song.getCatalog());
                song1.setPathFile(song.getPathFile());
            }
            session.saveOrUpdate(song);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.isActive();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void delete(int id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.delete(findById(id));
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.isActive();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Song findById(int id) {
        String queryStr ="select s from Song as s where s.id =:id";
        TypedQuery<Song> query = entityManager.createQuery(queryStr,Song.class);
        query.setParameter("id",id);
        return query.getSingleResult();
    }
}
