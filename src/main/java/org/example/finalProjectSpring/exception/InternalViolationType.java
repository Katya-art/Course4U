package org.example.finalProjectSpring.exception;

public enum InternalViolationType {

    //User
    USER_NOT_FOUND_EXCEPTION(1001, "User not found"),
    USER_ALREADY_EXISTS_EXCEPTION(1002, "User already exists"),

    //Course
    COURSE_NOT_FOUND_EXCEPTION(2001, "Course not found"),
    COURSE_ALREADY_EXISTS_EXCEPTION(2002, "Course already exists"),

    //User's course grade
    USER_COURSE_GRADE_NOT_FOUND_EXCEPTION(3001, "User's course grade not found"),
    USER_COURSE_GRADE_ALREADY_EXCEPTION(3002, "User's course grade already exists");

    private final int code;
    private final String message;

    InternalViolationType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ViolationType{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
