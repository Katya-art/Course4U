package org.example.finalProjectSpring.exception;

public class InternalViolationException extends RuntimeException {

    private final InternalViolationType type;

    public InternalViolationException(InternalViolationType type) {
        super(type.getMessage(), null, false, false);
        this.type = type;
    }

    public InternalViolationType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "ViolationException{" +
                "type=" + type +
                '}';
    }
}
