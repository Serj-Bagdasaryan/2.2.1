package hiber.dao;

import hiber.model.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   public void add(Car user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public List<User> getUserByModelAndSeries(String model, int series) {
      String hql = "from Car where model=:model and series=:series";
      String hql2 = "from User where car=:car";
      List<User> usersList = new ArrayList<>();

      List<Car> list = sessionFactory.getCurrentSession().createQuery(hql, Car.class)
              .setParameter("model", model).setParameter("series", series).getResultList();

      for (Car l : list) {
         usersList.add(sessionFactory.getCurrentSession().createQuery(hql2, User.class)
                 .setParameter("car", l).uniqueResult());
      }
      return usersList;
   }
}
