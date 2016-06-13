package com.hit.hibernate.model;

/*
 * 类似于JDBC是一个标准，Mysql的Driver是它的一个实现一样；
 * JPA（Java Persistence API）也是一个标准，Hibernate就是它的一个实现。
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
	 * 它默认的机制是AUTO.
	 * 
	 * 
	 * 注解Annotation中写@GeneratedValue就是AUTO，相当于XML中写native.（自动选择）
	 * 所以：
	 *     在Oracle中，就是sequence；
	 *     在Mysql中，就是Auto_incement；
	 *     在SQL Server中，就是identity.
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
	 * 【只想保留日期的年月日部分】
	 * 
	 * 映射日期与时间类型，指定时间精度.
	 * value="TemporalType.DATE"
	 * value="TemporalType.TIME"
	 * value="TemporalType.TIMESTAMP"
	 * value="",可以省略，直接写后面的TemporalType.DATE
	 */
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	@Enumerated(EnumType.STRING)
	/*
	 * 如果是EnumType.STRING，在数据库中的字段类型就是varchar类型；
	 * 如果是EnumType.ORDINAL，在数据库中的字段类型就是Integer类型。
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
	 * 当类中的属性和表中的字段不对应时，会出错.
	 */
	public double getAllPrice() {
		return this.price*this.count;
	}
	
	//即使没有用到属性allPrice，但仍然要写属性以及他的get和set方法，不然会出现属性和映射不对应的错误。
	public void setAllPrice(double allPrice) {
		this.allPrice = allPrice;
	}
}
