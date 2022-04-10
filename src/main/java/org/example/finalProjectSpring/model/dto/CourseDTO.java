package org.example.finalProjectSpring.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {

    @NonNull
    private String courseName;

    @NonNull
    private String courseTheme;

    @NonNull
    @Min(0)
    private Long duration;

    @NonNull
    private String teacherName;

}
