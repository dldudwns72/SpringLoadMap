package jpabook.jpashop.domain;

import jpabook.jpashop.valuetype.Address;
import jpabook.jpashop.valuetype.Period;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Embedded
    private Period workPeriod;

    @Embedded
    private Address homeAddrss;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
