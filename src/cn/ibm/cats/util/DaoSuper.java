package cn.ibm.cats.util;

import org.hibernate.Session;
import org.hibernate.Transaction;

import db.HibernateSessionFactory;

public class DaoSuper {
	protected Session session;
	protected Transaction transaction;
	protected DaoSuper(){
		session=HibernateSessionFactory.getSession();
		//transaction=session.beginTransaction();
	}
	protected void close(){
		//transaction.commit();
		session.flush();  
        session.clear();  
		HibernateSessionFactory.closeSession();
	}
}
