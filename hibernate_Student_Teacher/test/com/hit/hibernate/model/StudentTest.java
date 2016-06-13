package com.hit.hibernate.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class StudentTest {
	private static SessionFactory sf = null;;
	
	@BeforeClass
	public static void beforeClass() {
		sf = new Configuration().configure().buildSessionFactory();
	}

	@Test
	public void testSave() {
		Student s = new Student();
		s.setId(1);
		s.setName("s1");
		s.setAge(1);
		s.setSex("female");

		Session session = sf.getCurrentSession();
		session.beginTransaction();
		session.save(s);
		session.getTransaction().commit();

	}
	
	@AfterClass
	public static void afterClass() {
		sf.close();
	}
}
