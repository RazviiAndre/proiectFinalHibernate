package controller;

import DAO.Player;
import DAO.Score;
import DAO.User;
import org.hibernate.*;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class DBApp {

    private SessionFactory sessionFactory;

    public DBApp() {
//        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
//        MetadataSources sources = new MetadataSources(registry);
        //        //fie mapam in fisierul de configurare, fie adnotam aici
//        //sources.addAnnotatedClass(User.class);
//        sources.addAnnotatedClass(User.class);
//        sources.addAnnotatedClass(Player.class);
//        sources.addAnnotatedClass(Game.class);
//        sources.addAnnotatedClass(Score.class);
//
//        Metadata metadata = sources.getMetadataBuilder().build();
//        sessionFactory = metadata.getSessionFactoryBuilder().build();
        //old style
         sessionFactory = new Configuration().configure().buildSessionFactory();
    }


//    Register

    public void insertUser(String username, String password, String email, String firstName, String lastName, int phone) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = new User(username, password);
        session.persist(user);
        transaction.commit();
        session.close();

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        Query query = session.createQuery("SELECT u FROM User u WHERE u.username= :u").setParameter("u", username);
        user = (User) query.getSingleResult();
        Player player = new Player(firstName, lastName, email, phone, user);
        session.persist(player);
        transaction.commit();
        session.close();

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
//
//        Query query1 = session.createQuery("SELECT id FROM User WHERE username= :id").setParameter("id",username);
//        Object userID = query1.getSingleResult().toString();
//
//
//        Query query2 = session.createQuery("SELECT username FROM User WHERE username= :username").setParameter("username",username);
//        Object userName = query2.getSingleResult().toString();
//        String x = getScore((String) userName);
//        int y = Integer.parseInt(x);
//
//        Score score = new Score(z ,y);

        Score score = new Score(0);
        session.persist(score);
        transaction.commit();
        session.close();
    }
    public boolean validateUsername(String name) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createQuery("SELECT username FROM User WHERE username= :u").setParameter("u", name);
            Object userName = query.getSingleResult();
            transaction.commit();
            session.close();
            if (userName.equals(name)) {
                return true;
            } else {
                return false;
            }
        } catch (NoResultException e) {
            session.close();
            return false;
        }

    }
    public boolean validateEmail(String email) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createQuery("SELECT email FROM Player WHERE email= :e").setParameter("e", email);
            Object e = query.getSingleResult();
            transaction.commit();
            session.close();
            if (e.equals(email)) {
                return true;
            } else {
                return false;
            }
        } catch (NoResultException e) {
            session.close();
            return false;

        }
    }
//    Login
    public boolean validatePassword(String password , String username) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createQuery("SELECT password FROM User WHERE username= :p").setParameter("p", password);
            Query query1 = session.createQuery("SELECT username FROM User Where username= :u").setParameter("u",username);
            Object user = query1.getSingleResult();
            Object pass = query.getSingleResult();
            transaction.commit();
            session.close();
            if (password.equals(pass) && user.equals(username)) {
                System.out.println("Ai fost logat !");
                session.close();
                return true;
            } else {
                System.out.println("Nu te-ai logat !");
                session.close();
                return false;
            }

        } catch (NoResultException e) {
            System.out.println("EXCEPTIE ### validatePassword ### Controller ### Cont-uri cu username sau password indentice(a ramas de facut) ### ");
            session.close();
            return false;
        }
    }

//      Display details
    public String getEmail(String username){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query query2 = session.createQuery("SELECT id FROM User WHERE username= :u").setParameter("u", username);
            Object userNameID = query2.getSingleResult();

            User user = session.find(User.class, userNameID);
            int userID = user.getId();
            Query query1 = session.createQuery("SELECT id FROM User WHERE id= :s").setParameter("s",userID);
            Object s = query1.getSingleResult();

            Query query = session.createQuery("SELECT email FROM Player WHERE id= :e").setParameter("e",s);
            Object e = query.getSingleResult();

            transaction.commit();
            session.close();

            return e.toString();

        } catch (NoResultException e) {
            session.close();
            return "EXCEPTIE ### getEmail ### Controller";

        }
    }
    public String getScore(String username) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {

            Query query = session.createQuery("SELECT id FROM User WHERE username= :u").setParameter("u", username);
            Object userNameID = query.getSingleResult();

            Query query1 = session.createQuery("SELECT value FROM Score WHERE id= :s").setParameter("s",userNameID);
            Object e = query1.getSingleResult();


            transaction.commit();
            session.close();

            return e.toString();

        } catch (NoResultException e) {
            session.close();
            return  "### getScore ### DbApp ###";

        }
    }


//  Score methods
    public void updateScore(int playerID, int score) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Score score1 = session.find(Score.class, playerID);
        score1.setValue(score);
        transaction.commit();
        session.close();
    }
    public void insertScore(String username){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String i = getScoreID(username);


    }
    public String getScoreID(String username){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {

            Query query = session.createQuery("SELECT id FROM User WHERE username= :u").setParameter("u", username);
            Object userNameID = query.getSingleResult();

            Query query1 = session.createQuery("SELECT id FROM Score WHERE id= :s").setParameter("s",userNameID);
            Object e = query1.getSingleResult();


            transaction.commit();
            session.close();

            return e.toString();

        } catch (NoResultException e) {
            session.close();
            return  "### getScoreID ### Controller ###";

        }
    }



}



//    public void getListUserForRegisterSession(String name) {
//        Session session = sessionFactory.openSession();
//        Transaction tx = null;
//
//        try {
//            tx = session.beginTransaction();
//            String sql = "SELECT * FROM User ";
//            SQLQuery query = session.createSQLQuery(sql);
//            query.addEntity(User.class);
//
//            List employees = query.list();
//            for (Iterator iterator = employees.iterator(); iterator.hasNext(); ) {
//                User user = (User) iterator.next();
//                if (name.equals(user.getUsername())) {
//                    System.out.println("Utilizatorul exista");
//                }
//            }
//            tx.commit();
//        } catch (HibernateException e) {
//            if (tx != null) tx.rollback();
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//    }


//    public void exempluDeGetFromDB(String name) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//
//        Query query = session.createQuery("SELECT username FROM User WHERE username= :u").setParameter("u", name);
//        Object user = query.getSingleResult();
//        System.out.println(user);
//
//        transaction.commit();
//        session.close();
//    }

//    public void insertScore(Player player) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        Score score = new Score();
//        score.setValue(0);
//        session.persist(score);
//        transaction.commit();
//        session.close();
//
//        this.updateScore(player, score);
//    }

//    public void addScoreValue(Score score, int value) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        Score user = session.find(Score.class, score.getId());
//        user.setValue(value);
//        transaction.commit();
//        session.close();
//    }
//
//    public void updateScore(Player player, Score score) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        Player user = session.find(Player.class, player.getId());
//        user.setScore(score);
//        transaction.commit();
//        session.close();
//    }
//
//    public void addScore(Player player, int value) {
//        this.addScoreValue(player.getScore(), value);
//        this.updateScore(player, player.getScore());
//    }
//
//    public void deleteUser() {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        Player user = session.find(Player.class, 2);
//        session.delete(user);
//        transaction.commit();
//        session.close();
//    }
//
//
//    public void checkOneToOne() {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        Player user = session.find(Player.class, 1);
//        System.out.println("Address city: " + user.getAddress().getCity());
//
//        transaction.commit();
//        session.close();
//
//    }

//    public void checkOneToMany() {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        User user = session.find(User.class, 1);
//        for(User u: user.getUsernames()) {
//            System.out.println("Usernames : " + u.getUsername());
//        }
//        transaction.commit();
//        session.close();
//    }





