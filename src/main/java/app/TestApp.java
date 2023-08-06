package app;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import core.util.HibernateUtil;
import web.member.pojo.Member;

public class TestApp {
	public static void main(String[] args) {
		
		TestApp app = new TestApp();
//		Member member = new Member();
//		member.setUsername("root");
//		member.setPassword("12345678");
//		member.setNickname("lee");
//		
//		
//		app.insert(member);
//		System.out.println(member.getId());
		
		
		//test case
		
//		member.setId(1);
//		member.setPass(true);
//		
//		System.out.println(app.updateById(member));
		
//		System.out.println(app.selectById(2).getNickname());
		
		
		
//		app.selectAll().forEach(member -> System.out.println(member.getNickname()));
		
		//or
		
//		for(Member member : app.selectAll()) {
//			System.out.println(member.getNickname());
//		}
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		



		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);

		//	from MEMBER		
		Root<Member> root = criteriaQuery.from(Member.class);
		
		//	where USERNAME = ? and PASSWORD = ?		
		criteriaQuery.where(criteriaBuilder.and(
				criteriaBuilder.equal(root.get("username"), "admin"),
				criteriaBuilder.equal(root.get("password"), "P@ssW0rd")
				));
		
		//		select USERNAME, NICKNAME
		criteriaQuery.multiselect(root.get("username"),root.get("nickname"));
		
		// order by ID
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
		
		Member member = session.createQuery(criteriaQuery).uniqueResult();
		System.out.println(member.getNickname());
	};
	
	public Integer insert(Member member) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			session.persist(member);
			transaction.commit();
			return member.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		} 
		
	};
	
	public Integer deleteById(Integer id) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			Member member = session.get(Member.class, id);
			session.remove(member);
			transaction.commit();
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
			return -1;
		} 
		
	};
	
	
	public Integer updateById(Member newMember) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			Member oldmember = session.get(Member.class, newMember.getId());
			final Boolean pass = newMember.getPass();
			if (pass != null ) {
				oldmember.setPass(pass);
			}
			final Integer roldId = newMember.getRoleId();
			if (roldId != null ) {
				oldmember.setRoleId(roldId);
			}
			transaction.commit();
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
			
		} 
		return -1;
	};
	
	
	public Member selectById(Integer id) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			Transaction transaction = session.beginTransaction();
			Member member = session.get(Member.class, id);
			transaction.commit();
			return member;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		} 
		
	};
	
	public List<Member> selectAll() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		try {
			Transaction transaction = session.beginTransaction();
//			Query<Member> query = session.createQuery("FROM Member", Member.class);
			Query<Member> query = session.createQuery(
					"SELECT new web.member.pojo.Member(username, nickname) FROM Member", Member.class);
			
			List<Member> list = query.getResultList();
			transaction.commit();
			return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		} 
		
	};

};
