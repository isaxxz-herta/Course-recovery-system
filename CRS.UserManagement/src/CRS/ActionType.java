package CRS;

public enum ActionType {
    LOGIN("LOGIN"),
    LOGOUT("LOGOUT"),
    LOGIN_FAILED("LOGIN_FAILED"),
    PASSWORD_RESET("PASSWORD_RESET"),
    PASSWORD_CHANGED("PASSWORD_CHANGED"),
    PASSWORD_RECOVERY("PASSWORD_RECOVERY"),
    ACCOUNT_LOCKED("ACCOUNT_LOCKED"),
    ACCOUNT_UNLOCKED("ACCOUNT_UNLOCKED"),
    USER_CREATED("USER_CREATED"),
    USER_UPDATED("USER_UPDATED"),
    USER_DEACTIVATED("DEACTIVATED"),
    RESET_ATTEMPTS("RESET_ATTEMPTS");
    
    private final String value;
    
    ActionType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return value;
    }
}