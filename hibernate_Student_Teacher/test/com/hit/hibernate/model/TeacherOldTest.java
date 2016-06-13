package com.hit.hibernate.model;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import com.hit.hibernate.model.Teacher;


public class TeacherOldTest {
	public static void main(String[] args) {
		Teacher t = new Teacher();
		t.setId(1);
		t.setName("t1");
		t.setTitle("middle");
		
		Configuration cfg = new AnnotationConfiguration();
		//cfg.configure()不指定参数，默认的是读classPath下面的hibernate.cfg.xml
		SessionFactory sf = cfg.configure().buildSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		session.save(t);
		session.getTransaction().commit();
		session.close();
		sf.close();
	}
}
