package airport.server.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author natalija
 */
@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    @Column
    protected String userName;

    @Column
    protected String firstName;

    @Column
    protected String lastName;

    @Column
    protected String password;

    @Column
    protected boolean active;

    @Column
    protected String roles;

    public User() {
    }

    public User(User user) {
        this.id = user.id;
        this.userName = user.userName;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.password = user.password;
        this.active = user.active;
        this.roles = user.roles;
    }
    public User(int id, String userName, String firstName, String lastName, String password, boolean active, String roles) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }
}
