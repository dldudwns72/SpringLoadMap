package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 쿼리 메소드 구현
    List<Member> findByUsername(String username);



    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query("select m from Member m where m.username= :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    // 단순한 값 하나 조회
    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username = :name")
    Member findMembers(@Param("name") String username);

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    List<Member> findListByUsername(String username);//컬렉션
    Member findMemberByUsername(String name); //단건
    Optional<Member> findOptionalByUsername(String name); //단건 Optional

    // 카운트 쿼리는 분리하여 사용하는것이 좋다.
    // @Query(value = "select m from Member m left join m.team t", countQuery = "select count(m.username) from Member m") // 쿼리를 분리하여 Paging 처리 가능
     Page<Member> findByAge(int age, Pageable pageable); //count 쿼리 사용
    // Slice<Member> findByAge(int age, Pageable pageable); //count 쿼리 사용 안함, limit 절에 + 1, getTotalElement, getTotalPages 없음

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    //공통 메서드 오버라이드
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    //JPQL + 엔티티 그래프
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    // 메서드 이름으로 쿼리에서 특히 편리하다.
    // @EntityGraph(attributePaths = {"team"})
    @EntityGraph("Member.all")
    List<Member> findByEntityGraphByUsername(@Param("username") String username);


    // SQL 힌트가 아닌, JPA 구현체에 제공하는 힌트
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    // forCounting : 반환 타입으로 Page 인터페이스를 적용하면 추가로 호출하는 페이징을 위한 count 쿼리도 쿼리 힌트 적용
    @QueryHints(value = { @QueryHint(name = "org.hibernate.readOnly",value = "true")},forCounting = true)
    Page<Member> findReadOnlyByUsername(String name, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String name);

}
