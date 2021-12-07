package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RateDiscountPolicyTest {
    RateDiscountPolicy rateDiscountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP 10% 할인 적용")
    public void isVipTrue(){
        Member member = new Member(1L,"memberVIP", Grade.VIP);

        int discountValue = rateDiscountPolicy.discount(member,10000);

        assertThat(discountValue).isEqualTo(1000);
    }

    @Test
    @DisplayName("Not VIP 10% 할인 미적용")
    public void isVipFalse(){
        Member member = new Member(1L,"memberBasic", Grade.BASIC);

        int discountValue = rateDiscountPolicy.discount(member,10000);

        assertThat(discountValue).isEqualTo(0);
    }

}
