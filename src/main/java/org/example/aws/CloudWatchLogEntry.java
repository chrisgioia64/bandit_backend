package org.example.aws;

public class CloudWatchLogEntry {

    public enum Level {
        WARNING, INFO, DEBUG
    }

    public enum CloudWatchLogType {
        API,
        STARTUP
    }

    private CloudWatchLogType type;
    private final Level level;
    private final String message;
    private final Object details;

    public CloudWatchLogEntry(CloudWatchLogType type,
                              Level level, String message,
                              Object details) {
        this.type = type;
        this.level = level;
        this.message = message;
        this.details = details;
    }

    public void setType(CloudWatchLogType type) {
        this.type = type;
    }

    public CloudWatchLogType getType() {
        return type;
    }

    public Level getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public Object getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "CloudWatchLogEntry{" +
                "type=" + type +
                ", level=" + level +
                ", message='" + message + '\'' +
                ", details=" + details +
                '}';
    }
}
