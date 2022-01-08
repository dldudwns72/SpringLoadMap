package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
public class MemberExample {

    @Id
    private String id;

    @Column(name = "name")
    private String username;

    public MemberExample() {
    }


}
