package com.hit.hibernate.model;

import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class HibernateIdTest {
//	private static SessionFactory sf1 = null;
	private static SessionFactory sf2 = null;
	
	@BeforeClass
	public static void beforeClass() {
	//	sf1 = new Configuration().configure().buildSessionFactory();
		sf2 = new AnnotationConfiguration().configure().buildSessionFactory();
	}

//	@Test
//	public void testStudentSave() {
//		Student s = new Student();
//		//使用<generator>标签之后，此时id不用自己设置了
//		s.setName("s1");
//		s.setAge(1);
//		s.setSex("female");
//
//		Session session = sf1.getCurrentSession();
//		session.beginTransaction();
//		session.save(s);
//		session.getTransaction().commit();
//	}
	
//	@Test
//	public void testTeacherSave() {
//		Teacher t = new Teacher();
//		//使用<generator>标签之后，此时id不用自己设置了
//		t.setName("s2");
//		t.setTitle("Professor");
//		t.setBirthDate(new Date());
//		t.setCount(3);
//		t.setPrice(1.3);
//		t.setZhiCheng(ZhiCheng.A);
//		
//
//		Session session = sf2.getCurrentSession();
//		session.beginTransaction();
//		session.save(t);
//		session.getTransaction().commit();
//	}
	
	@Test
	public void testSave() {
		Student s = new Student();
		Teacher t = new Teacher();
		//使用<generator>标签之后，此时id不用自己设置了
		s.setName("hello");
		s.setAge(3);
		s.setSex("male");
		s.setBirthDate(new Date());
		
		t.setName("s2");
		t.setTitle("Professor");
		t.setBirthDate(new Date());
		t.setCount(2);
		t.setPrice(1.3);
		t.setZhiCheng(ZhiCheng.B);
		

		Session session = sf2.getCurrentSession();
		session.beginTransaction();
		session.save(t);
		session.save(s);
		//当使用getCurrentSession()方法时，session事务提交之后，session就会自动关闭。
		session.getTransaction().commit();
	}
	
	@Test
	public void testDelete() {
		Teacher t = new Teacher();
		//t.setId(10);
		t.setName("xiao ming");
		
		Session session = sf2.getCurrentSession();
		session.beginTransaction();
		session.save(t);
		//save之后变为persistent状态，内存中有、缓存中有、数据库中有。
		System.out.println(t.getId());
		session.getTransaction().commit();
		//commit之后变为detached状态，内存中有、缓存中没有了、数据库中还有。
		System.out.println(t.getId());
		
//		Session session2 = sf2.getCurrentSession();
//		session2.beginTransaction();
//		session2.delete(t);
//		session2.getTransaction().commit();
	}
	
	@Test
	public void testLoad() {
		
		Session session = sf2.getCurrentSession();
		session.beginTransaction();
		/*
		 * load()是在真正访问对象时，才会把这条查询的SQL语句发出去取出这条记录。
		 * 它是LazyInitialization的。
		 * 若在session提交以后，才访问对象，那么这条SQL语句就发不出去了，并会报LazyInitializationException异常。
		 */
		Teacher t = (Teacher) session.load(Teacher.class, 4);
	//	System.out.println(t.getId()+"-"+t.getName());
		session.getTransaction().commit();
		System.out.println(t.getClass());
		
	}
	
	@Test
	public void testGet() {
		
		Session session = sf2.getCurrentSession();
		session.beginTransaction();
		/*
		 * get()是直接发送SQL语句，取出记录。
		 * 不拖延。
		 */
		Teacher t = (Teacher) session.get(Teacher.class, 2);
	//	System.out.println(t.getId()+"-"+t.getName());
		session.getTransaction().commit();
		System.out.println(t.getClass());	
	}
	
	@Test
	/*
	 * 情况一：update()可以更新一个Detached对象。
	 * 
	 * 会又把它变为persistent对象，数据库里同时也会更新。
	 */
	public void testUpdate1() {
		
		Session session = sf2.getCurrentSession();
		session.beginTransaction();
		Teacher t = (Teacher) session.get(Teacher.class, 2);
		session.getTransaction().commit();
		
		t.setName("Li Hua");
		
		Session session2 = sf2.getCurrentSession();
		session2.beginTransaction();
		session2.update(t);
		session2.getTransaction().commit();
	}
	
	@Test
	/*
	 * 情况二：update()更新transient对象会报错。
	 */
	public void testUpdate2() {
		Teacher t = new Teacher();
		t.setName("Li Hua");
		
		Session session2 = sf2.getCurrentSession();
		session2.beginTransaction();
		session2.update(t);
		session2.getTransaction().commit();
	}
	
	@Test
	/*
	 * 情况三：update()更新自己设定的Id的transient对象可以（数据库中有）。
	 */
	public void testUpdate3() {
		Teacher t = new Teacher();
		t.setId(8);
		t.setName("haha");
		
		Session session2 = sf2.getCurrentSession();
		session2.beginTransaction();
		session2.update(t);
		session2.getTransaction().commit();
	}
	
	@Test
	/*
	 * 情况四：对于persistent对象发生变化后提交时，会自动检查有无更新：
	 *        如果有，则自动发送update()语句，并把所有字段都更新一遍；
	 *        如果没有，则不发update()语句。
	 */
	public void testUpdate4() {
		Session session = sf2.getCurrentSession();
		session.beginTransaction();
		//此时是persistent对象
		Teacher t = (Teacher) session.get(Teacher.class, 5);
		t.setName("DuDU");
		session.getTransaction().commit();
		
	}
	
	@Test
	/*
	 * 在Student.hbm.xml文件中class标签的属性里设置dynamic-update="true",
	 * 此时在同一个session中修改persistent对象，会自动只更新修改了的字段。
	 * 
	 * 但是如果session关闭了，即变为了Detached状态，那么此时再修改然后再【更新】，就会更新所有的字段。
	 * 但是如果session关闭了，即变为了Detached状态，那么此时再修改然后再【合并】，就会只更新修改了的字段，并会先发出select语句。
	 */
	public void testUpdateStudent() {

		Session session = sf2.getCurrentSession();
		session.beginTransaction();
		//此时是persistent对象
		Student s = (Student) session.get(Student.class, 1);
		s.setName("b");
		session.getTransaction().commit();
		
		s.setName("c");
		
		Session session2 = sf2.getCurrentSession();
		session2.beginTransaction();
	//	session2.update(s);
		session2.merge(s);
		session2.getTransaction().commit();
		
	}
	
	@Test
	//使用HQL(EJBQL)
	public void testUpdate5() {

		Session session = sf2.getCurrentSession();
		session.beginTransaction();
		//这里的Student是类，并不是表名。
		Query q = session.createQuery("update Student s set s.name='ssss' where s.id = 1");
		q.executeUpdate();
		session.getTransaction().commit();	
	}
	
	@Test
	//使用HQL(EJBQL)
	public void testSaveOrUpdate() {
		Teacher t = new Teacher();
		t.setName("t1");
		t.setTitle("middle");
		
		Session session = sf2.getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(t);
		session.getTransaction().commit();
		
		t.setName("t2");
		
		Session session2 = sf2.getCurrentSession();
		session2.beginTransaction();
		session2.saveOrUpdate(t);
		session2.getTransaction().commit();
	}
	
	@Test
	public void testClear() {	
		Session session = sf2.getCurrentSession();
		session.beginTransaction();
		Teacher t = (Teacher) session.load(Teacher.class, 2);
		System.out.println(t.getName());
		
		//清空缓存
		session.clear();
		
		Teacher t2 = (Teacher) session.load(Teacher.class, 2);
		System.out.println(t2.getName());	
		session.getTransaction().commit();	
	}
	
	@Test
	public void testFlush() {	
		Session session = sf2.getCurrentSession();
		session.beginTransaction();
		Teacher t = (Teacher) session.load(Teacher.class, 2);
		t.setName("AAAA");
		
		//强制让缓存的内容和数据库的内容做同步，因为默认是在提交时做同步
		session.flush();
		
		t.setName("Aa");

		session.getTransaction().commit();	
	}
	
//	@Test
//	//自动建表
//	public void testSchemaExport() {
//		new SchemaExport(new AnnotationConfiguration().configure()).create(true, true);	
//	}
	
	@AfterClass
	public static void afterClass() {
	//	sf1.close();
		sf2.close();
	}
}
