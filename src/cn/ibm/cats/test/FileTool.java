package cn.ibm.cats.test;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.Test;

import cn.ibm.cats.classifier.FailLog;
import cn.ibm.cats.classifier.HtmlLog;


public class FileTool {
	/**
	 * 根据错误id 显示log本地路径
	 * 
	 */
	@Test
	public void openHtmlLogByLogId() {
		int id;
		Scanner scan=new Scanner(System.in);
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session=sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		while(scan.hasNext()){
			id=scan.nextInt();
			FailLog failLog  = (FailLog) session.get(FailLog.class, id);
			//System.out.println(failLog);
			/*for(HtmlLog htmllog:failLog.getHtmlLogs()){
				System.out.println(htmllog.getPath());
			}*/
		}
		transaction.commit();
		session.close();
	}
	@Test
	public void test(){
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session=sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery("from HtmlLog");
		List<HtmlLog> list = query.list();
		for (HtmlLog htmlLog : list) {
			List<FailLog> faillogs = htmlLog.getFailLogs();
			if(faillogs.size()>=1){
				System.out.println(faillogs.get(faillogs.size()-1).getTitle());
			}else {
				System.out.println(faillogs.size()+htmlLog.getPath());
				  
			}
		}
	}
}
