package com.hit.hibernate.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.hit.hibernate.model.Student;


public class StudentOldTest {
	public static void main(String[] args) {
		Student s = new Student();
		s.setId(1);
		s.setName("s1");
		s.setAge(1);
		
		Configuration cfg = new Configuration();
		//cfg.configure()不指定参数，默认的是读classPath下面的hibernate.cfg.xml
		SessionFactory sf = cfg.configure().buildSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		session.save(s);
		session.getTransaction().commit();
		session.close();
		sf.close();
	}
}
