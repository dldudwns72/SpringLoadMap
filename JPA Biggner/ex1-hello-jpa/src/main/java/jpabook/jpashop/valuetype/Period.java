package jpabook.jpashop.valuetype;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Period {
    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public Period(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isWork(){
        if(startDate.getHour() > endDate.getHour()){
            return true;
        }
        return false;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }


}
