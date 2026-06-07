package CRS;

public enum AuthStatus {
    SUCCESS("Authentication successful"),
    INVALID_CREDENTIALS("Invalid username or password"),
    ACCOUNT_LOCKED("Account is locked"),
    ACCOUNT_INACTIVE("Account is deactivated"),
    USER_NOT_FOUND("Username not found"),
    MAX_ATTEMPTS_EXCEEDED("Maximum login attempts exceeded");
    
    private final String message;
    
    AuthStatus(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    
    @Override
    public String toString() {
        return message;
    }
}