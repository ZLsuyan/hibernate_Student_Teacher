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
//		//ʹ��<generator>��ǩ֮�󣬴�ʱid�����Լ�������
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
//		//ʹ��<generator>��ǩ֮�󣬴�ʱid�����Լ�������
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
		//ʹ��<generator>��ǩ֮�󣬴�ʱid�����Լ�������
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
		//��ʹ��getCurrentSession()����ʱ��session�����ύ֮��session�ͻ��Զ��رա�
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
		//save֮���Ϊpersistent״̬���ڴ����С��������С����ݿ����С�
		System.out.println(t.getId());
		session.getTransaction().commit();
		//commit֮���Ϊdetached״̬���ڴ����С�������û���ˡ����ݿ��л��С�
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
		 * load()�����������ʶ���ʱ���Ż��������ѯ��SQL��䷢��ȥȡ��������¼��
		 * ����LazyInitialization�ġ�
		 * ����session�ύ�Ժ󣬲ŷ��ʶ�����ô����SQL���ͷ�����ȥ�ˣ����ᱨLazyInitializationException�쳣��
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
		 * get()��ֱ�ӷ���SQL��䣬ȡ����¼��
		 * �����ӡ�
		 */
		Teacher t = (Teacher) session.get(Teacher.class, 2);
	//	System.out.println(t.getId()+"-"+t.getName());
		session.getTransaction().commit();
		System.out.println(t.getClass());	
	}
	
	@Test
	/*
	 * ���һ��update()���Ը���һ��Detached����
	 * 
	 * ���ְ�����Ϊpersistent�������ݿ���ͬʱҲ����¡�
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
	 * �������update()����transient����ᱨ��
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
	 * �������update()�����Լ��趨��Id��transient������ԣ����ݿ����У���
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
	 * ����ģ�����persistent�������仯���ύʱ�����Զ�������޸��£�
	 *        ����У����Զ�����update()��䣬���������ֶζ�����һ�飻
	 *        ���û�У��򲻷�update()��䡣
	 */
	public void testUpdate4() {
		Session session = sf2.getCurrentSession();
		session.beginTransaction();
		//��ʱ��persistent����
		Teacher t = (Teacher) session.get(Teacher.class, 5);
		t.setName("DuDU");
		session.getTransaction().commit();
		
	}
	
	@Test
	/*
	 * ��Student.hbm.xml�ļ���class��ǩ������������dynamic-update="true",
	 * ��ʱ��ͬһ��session���޸�persistent���󣬻��Զ�ֻ�����޸��˵��ֶΡ�
	 * 
	 * �������session�ر��ˣ�����Ϊ��Detached״̬����ô��ʱ���޸�Ȼ���١����¡����ͻ�������е��ֶΡ�
	 * �������session�ر��ˣ�����Ϊ��Detached״̬����ô��ʱ���޸�Ȼ���١��ϲ������ͻ�ֻ�����޸��˵��ֶΣ������ȷ���select��䡣
	 */
	public void testUpdateStudent() {

		Session session = sf2.getCurrentSession();
		session.beginTransaction();
		//��ʱ��persistent����
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
	//ʹ��HQL(EJBQL)
	public void testUpdate5() {

		Session session = sf2.getCurrentSession();
		session.beginTransaction();
		//�����Student���࣬�����Ǳ�����
		Query q = session.createQuery("update Student s set s.name='ssss' where s.id = 1");
		q.executeUpdate();
		session.getTransaction().commit();	
	}
	
	@Test
	//ʹ��HQL(EJBQL)
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
		
		//��ջ���
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
		
		//ǿ���û�������ݺ����ݿ��������ͬ������ΪĬ�������ύʱ��ͬ��
		session.flush();
		
		t.setName("Aa");

		session.getTransaction().commit();	
	}
	
//	@Test
//	//�Զ�����
//	public void testSchemaExport() {
//		new SchemaExport(new AnnotationConfiguration().configure()).create(true, true);	
//	}
	
	@AfterClass
	public static void afterClass() {
	//	sf1.close();
		sf2.close();
	}
}
