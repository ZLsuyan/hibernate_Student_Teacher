package com.hit.hibernate.model;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class TeacherTest {
	private static SessionFactory sf = null;
	
	@BeforeClass
	public static void beforeClass(){
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
	}
	
	@Test
	public void testTeacherSave(){
		Teacher t = new Teacher();

		t.setName("t1");
		t.setTitle("middle");
		t.setBirthDate(new Date());
		t.setZhiCheng(ZhiCheng.A);
		t.setCount(2);
		t.setPrice(1.2);
		
		Session session = sf.getCurrentSession();
		session.beginTransaction();
		session.save(t);
		session.getTransaction().commit();
	}
	
	/*public static void main(String[] args) {
		beforeClass();
	}*/
	
	@AfterClass
	public static void afterClass(){
		sf.close();
	} 
}
