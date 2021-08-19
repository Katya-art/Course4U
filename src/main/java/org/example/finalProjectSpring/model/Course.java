package org.example.finalProjectSpring.model;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

/**
 * Simple JavaBean domain object that represents a Course.
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "theme")
    private String theme;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "number_of_students")
    private int numberOfStudents;

    @Transient
    private String teacherName;

    @ManyToOne
    @JoinTable(name = "course_teacher", joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private User teacher;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "course_students_marks", joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<User> students;

    @ManyToMany(fetch = FetchType.EAGER)
    @MapKeyJoinColumn(name = "student_id")
    @JoinTable(name = "course_students_marks", joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "mark_id"))
    private Map<User, Mark> studentsMarks;

    @ManyToOne
    @JoinTable(name = "course_conditions", joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "condition_id"))
    private Condition condition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public Set<User> getStudents() {
        return students;
    }

    public void setStudents(Set<User> students) {
        this.students = students;
    }

    public Map<User, Mark> getStudentsMarks() {
        return studentsMarks;
    }

    public void setStudentsMarks(Map<User, Mark> studentsMarks) {
        this.studentsMarks = studentsMarks;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
