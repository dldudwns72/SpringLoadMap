package jpabook.jpashop;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class jpaMain {
    public static void main(String[] args) {
        //persistence-unit
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            //팀 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);
            //회원 저장
            Member member = new Member();
            member.setName("member1");
            member.setTeam(team); //단방향 연관관계 설정, 참조 저장
            em.persist(member);

            //조회
            Member findMember = em.find(Member.class, member.getId());
            //참조를 사용해서 연관관계 조회
            Team findTeam = findMember.getTeam();

            // 반대 방향에서의 조회
            Team team1 = em.find(Team.class,team.getId());
            for(Member m : team1.getMembers()){
                System.out.println("member name " + m.getName());
            }

            // 양방향 매핑 시 연관관계의 주인에 값을 입력해야 한다.
            //Team team = new Team();
            //team.setName("TeamA");
            //em.persist(team);
            //Member member = new Member();
            //member.setName("member1");
            //team.getMembers().add(member);
            //연관관계의 주인에 값 설정
            //member.setTeam(team); //**
            //em.persist(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
