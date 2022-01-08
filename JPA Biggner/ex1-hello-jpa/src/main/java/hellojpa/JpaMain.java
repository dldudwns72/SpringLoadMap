package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        //persistence-unit
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 멤버 Insert
            //Member member = new Member();
            //member.setId(2L);
            //member.setName("HelloB");
            //em.persist(member);

            // JPQL을 통한 쿼리를 통하여 데이터를 가져오기
            List<MemberExample> result = em.createQuery("select m from Member as m", MemberExample.class).getResultList();
//            for(Member member : result){
//                System.out.println("member.name " + member.getName());
//            }

            // 멤버 Read
            //Member findMember = em.find(Member.class , 1L);
            //System.out.println("findMember.id = " + findMember.getId());
            //System.out.println("findMember.name = " + findMember.getName());

            // 멤버 Update
            //Member findMember = em.find(Member.class , 1L);
            //findMember.setName("HelloJPA");

            // 멤버 Delete
            //Member findMember = em.find(Member.class , 1L);
            //em.remove(findMember);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
