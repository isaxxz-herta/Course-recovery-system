package academicengine.attempt;

public class CourseAttempt implements AttemptController {
    private int attemptNumber;
    private AttemptStatus status;

    public CourseAttempt(int attemptNumber, AttemptStatus status) {
        if (attemptNumber < 1) {
            throw new IllegalArgumentException("attemptNumber must be >= 1");
        }
        if (status == null) {
            throw new IllegalArgumentException("status must not be null");
        }
        this.attemptNumber = attemptNumber;
        this.status = status;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public AttemptStatus getStatus() {
        return status;
    }

    public void setStatus(AttemptStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("status must not be null");
        }
        this.status = status;
    }

    public void advanceAttempt() {
        this.attemptNumber++;
        this.status = AttemptStatus.IN_PROGRESS;
        System.out.println("Attempt advanced to " + attemptNumber);
    }
    
    @Override
    public CourseAttempt startAttempt(int number) {
        return new CourseAttempt(number, AttemptStatus.IN_PROGRESS);
    }

    @Override
    public void advance(CourseAttempt attempt) {
        if (attempt == null) {
            throw new IllegalArgumentException("attempt must not be null");
        }
        attempt.attemptNumber++;
        attempt.status = AttemptStatus.IN_PROGRESS;
    }

    @Override
    public void markPassed(CourseAttempt attempt) {
        if (attempt == null) {
            throw new IllegalArgumentException("attempt must not be null");
        }
        attempt.status = AttemptStatus.PASSED;
    }

    @Override
    public void markFailed(CourseAttempt attempt) {
        if (attempt == null) {
            throw new IllegalArgumentException("attempt must not be null");
        }
        attempt.status = AttemptStatus.FAILED;
    }
}