package org.example.finalProjectSpring.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.finalProjectSpring.model.enams.Grade;
import org.example.finalProjectSpring.model.enams.Status;

import javax.persistence.*;
import java.util.UUID;

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
    private String userId;

    @Column(name = "course_id", nullable = false, updatable = false)
    private String courseId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grade grade;

    public static UserCourseGrade.UserCourseGradeBuilder builder() {
        return new UserCourseGrade.UserCourseGradeBuilder()
                .id(UUID.randomUUID().toString());
    }
}
