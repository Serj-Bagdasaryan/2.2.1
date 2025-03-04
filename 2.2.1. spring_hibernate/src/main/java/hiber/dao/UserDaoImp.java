package hiber.dao;

import hiber.model.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.TypedQuery;
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
      String hql3 = "from User u where u.car.model=:model and u.car.series=:series";
      return sessionFactory.getCurrentSession().createQuery(hql3, User.class)
              .setParameter("model", model).setParameter("series", series).getResultList();
   }
}
