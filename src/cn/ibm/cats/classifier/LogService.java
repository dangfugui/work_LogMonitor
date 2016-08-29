package cn.ibm.cats.classifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.security.auth.Destroyable;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import db.HibernateSessionFactory;



public class LogService {
	private Session session;
	private Transaction transaction;
	public LogService(){
		session=HibernateSessionFactory.getSession();
		//transaction=session.beginTransaction();
	}
	public void close(){
		//transaction.commit();
		session.flush();  
        session.clear();  
		HibernateSessionFactory.closeSession();
	}
	public void save(HtmlLog htmlLog){
		List<FailLog> list=new ArrayList<FailLog>();
		for(FailLog f:htmlLog.getFailLogs()){
			f=findSame(f);
			list.add(f);
		}
		htmlLog.setFailLogs(list);
		transaction=session.beginTransaction();
		session.save(htmlLog);
		transaction.commit();
	}
	public FailLog findSame(FailLog failLog){
		transaction=session.beginTransaction();
		Query query = session.createQuery("from FailLog where title= ? and line_number = ?");
		query.setParameter(0, failLog.getTitle());//setParameter("name",value);
		query.setParameter(1, failLog.getLine_number());
		Iterator iterator = query.iterate();
		transaction.commit();
		while (iterator.hasNext()) {
			return (FailLog)iterator.next();
		}
		return failLog;
	}
	

	
}
