package server.nanum.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "user_group")
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_group_id")
    private Long id;

    @Column(name = "point")
    private Long point;
}
