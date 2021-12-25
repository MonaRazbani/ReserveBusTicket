package dao;

import models.Dto.UserDto;
import models.roles.Manager;
import models.roles.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import javax.xml.transform.Transformer;
import java.util.List;

public class UserDao extends DataBaseAccess {
    public void saveNewUser(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }
    public void updateUser(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(user);
        transaction.commit();
        session.close();
    }


    public User findByNationalCode(String nationalCode) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<User> query = session.createQuery("FROM User user WHERE user.nationalCode=:nationalCode ");
        query.setParameter("nationalCode", nationalCode);
        User result = query.getSingleResult();
        transaction.commit();
        session.close();
        return result;
    }
    public UserDto findUserDtoWithUser (User user){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(User.class, "user") ;
        criteria.add(Restrictions.eq("user.nationalCode",user.getNationalCode()));
        criteria.setProjection(Projections.projectionList()
                .add(Projections.property("user.firstName"))
                .add(Projections.property("user.LastName"))
                .add(Projections.property("user.nationalCode"))
                .add(Projections.property("user.phoneNumber")));
        criteria.setResultTransformer(Transformers.aliasToBean(UserDto.class));
         UserDto result = (UserDto) criteria.uniqueResult();
        transaction.commit();
        session.close();
        return result;

    }
}

