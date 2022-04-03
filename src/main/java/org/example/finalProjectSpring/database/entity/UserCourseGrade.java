package org.example.finalProjectSpring.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.finalProjectSpring.model.Grade;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users_courses_grades")
public class UserCourseGrade {

    @Id
    @Column(updatable = false, unique = true, nullable = false)
    private String id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private String user_id;

    @Column(name = "course_id", nullable = false, updatable = false)
    private String course_id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grade grade;
}
