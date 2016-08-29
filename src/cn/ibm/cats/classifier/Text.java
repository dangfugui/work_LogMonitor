package cn.ibm.cats.classifier;

import java.io.File;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import db.HibernateSessionFactory;

public class Text {
	private Session session;
	private Transaction transaction;
	//@Before
	public void before(){
		session=HibernateSessionFactory.getSession();
		transaction=session.beginTransaction();
	}
	//@After
	public void after(){
		transaction.commit();
		 session.flush();  
         session.clear();  
		HibernateSessionFactory.closeSession();
		System.out.println("after");
	}
	@Test
	public void testShemaExport(){
		//创建hibernate对象
		Configuration config=new Configuration().configure();
		//创建服务对象
		ServiceRegistry serviceRegistry=new ServiceRegistryBuilder().
				applySettings(config.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory=config.buildSessionFactory(serviceRegistry);
		SchemaExport export=new SchemaExport(config);
		export.create(true,true);
	}
	
	@Test
	public void textsave(){
		FailLog failLog=new FailLog();
		failLog.setTime("time1");
		HtmlLog htmlLog=new HtmlLog("dd");
		htmlLog.getFailLogs().add(failLog);
		//session.save(failLog);
		session.save(htmlLog);
	}
	//@Test
	public void textLoad(){
		Session session=HibernateSessionFactory.getSession();
		Transaction transaction=session.beginTransaction();
		
	}
	@Test
	public void testExtractHtml(){
		ExtractHtml extractHtml=new ExtractHtml();
		String path0="C:\\Data\\My Code\\Eclipse\\MyUserData\\logs\\db2admin.scripts.admin.a_alter_alc_7c.AD7CAA9P2015102916370463746\\rational_ft_logframe.html";
		HtmlLog htmlLog=extractHtml.extract(new HtmlLog(path0));
		//session.save(htmlLog);
		List<FailLog> f = htmlLog.getFailLogs();
		for (int i = 0; i < f.size(); i++) {
			System.out.println(f.get(i).toString());
		}
		System.out.println(htmlLog.getFailLogs().size());
		
	}
	//解析html 文件
	@Test
	public void testExtractHtmls() throws InterruptedException{
		ExtractHtml extractHtml=new ExtractHtml();
		String path="C:/Data/My Code/Eclipse/MyUserData/logs";
		List<HtmlLog> htmllogs = extractHtml.extracts(path);
		LogService logService =new LogService();
		 for (int i = 0; i < htmllogs.size(); i++) {
			logService.save(htmllogs.get(i));
		}
		 logService.close();
		 System.out.println("end");
	}
}
