package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public Optional<User> findByCarModelAndSeries(String carModel, int series) {
      String hql="from User where car.model=:carModel and car.series=:series";
      TypedQuery<User> query= sessionFactory.getCurrentSession().createQuery(hql,User.class);
      query.setParameter("carModel",carModel);
      query.setParameter("series",series);
      try {
         return  Optional.of(query.getSingleResult());
      }catch (NoResultException e) {
         return Optional.empty();
      }


   }

}
