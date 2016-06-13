package com.hit.hibernate.model;

/*
 * ������JDBC��һ����׼��Mysql��Driver������һ��ʵ��һ����
 * JPA��Java Persistence API��Ҳ��һ����׼��Hibernate��������һ��ʵ�֡�
 */

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
//@SequenceGenerator(name="teacherSEQ",sequenceName="teacherSEQ_DB")

/*@TableGenerator(
		name="Teacher_GEN",
		table="GENERATOR_TABLE",
		pkColumnName="pk_key",
		valueColumnName="pk_value",
		pkColumnValue="Teacher",
		allocationSize=1
)*/

@Table(name="_Teacher")
public class Teacher {

	private int id;
	private String name;
	private String title;
	private String yourWifeName;
	private Date birthDate;
	private ZhiCheng zhiCheng;
	private int count;
	private double price;
	private double allPrice;

	@Id
	@GeneratedValue
	/*
	 * ��Ĭ�ϵĻ�����AUTO.
	 * 
	 * 
	 * ע��Annotation��д@GeneratedValue����AUTO���൱��XML��дnative.���Զ�ѡ��
	 * ���ԣ�
	 *     ��Oracle�У�����sequence��
	 *     ��Mysql�У�����Auto_incement��
	 *     ��SQL Server�У�����identity.
	 */
	
	//@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="teacherSEQ")
	
	//@GeneratedValue(strategy=GenerationType.TABLE,generator="Teacher_GEN")
	@Column(name="_id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="_name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Transient
	public String getYourWifeName() {
		return yourWifeName;
	}
	public void setYourWifeName(String yourWifeName) {
		this.yourWifeName = yourWifeName;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	/*
	 * ��ֻ�뱣�����ڵ������ղ��֡�
	 * 
	 * ӳ��������ʱ�����ͣ�ָ��ʱ�侫��.
	 * value="TemporalType.DATE"
	 * value="TemporalType.TIME"
	 * value="TemporalType.TIMESTAMP"
	 * value="",����ʡ�ԣ�ֱ��д�����TemporalType.DATE
	 */
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	@Enumerated(EnumType.STRING)
	/*
	 * �����EnumType.STRING�������ݿ��е��ֶ����;���varchar���ͣ�
	 * �����EnumType.ORDINAL�������ݿ��е��ֶ����;���Integer���͡�
	 */
	public ZhiCheng getZhiCheng() {
		return zhiCheng;
	}
	public void setZhiCheng(ZhiCheng zhiCheng) {
		this.zhiCheng = zhiCheng;
	}
	
	@Column(name="_count")
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Column(name="_price")
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	@Column(name="_allPrice")
	/*
	 * �����е����Ժͱ��е��ֶβ���Ӧʱ�������.
	 */
	public double getAllPrice() {
		return this.price*this.count;
	}
	
	//��ʹû���õ�����allPrice������ȻҪд�����Լ�����get��set��������Ȼ��������Ժ�ӳ�䲻��Ӧ�Ĵ���
	public void setAllPrice(double allPrice) {
		this.allPrice = allPrice;
	}
}
