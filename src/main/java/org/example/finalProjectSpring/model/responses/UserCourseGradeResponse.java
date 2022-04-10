package org.example.finalProjectSpring.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.finalProjectSpring.database.entity.User;
import org.example.finalProjectSpring.model.enams.Grade;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCourseGradeResponse {

    private User student;
    private Grade grade;

}
