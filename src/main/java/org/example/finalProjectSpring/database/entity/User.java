package org.example.finalProjectSpring.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.finalProjectSpring.database.entity.Course;
import org.example.finalProjectSpring.model.Role;
import org.example.finalProjectSpring.model.Status;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * Simple JavaBean domain object that represents a User.
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @Column(updatable = false, unique = true, nullable = false)
    private String id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    //only for users with role TEACHER
    @OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER)
    private List<Course> assignedCourses;

//    //only for users with role STUDENT
//    @ManyToMany(mappedBy = "students", fetch = FetchType.EAGER)
//    private List<Course> enrolledCourses;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public static UserBuilder builder() {
        return new UserBuilder().id(UUID.randomUUID().toString());
    }

}