package org.example.aws;

public class APILogEntry extends CloudWatchLogEntry {

    private String userAgent;
    private double secondsTaken;
    private String apiUrl;

    public APILogEntry(CloudWatchLogType type, Level level, String message, Object details) {
        super(type, level, message, details);
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public double getSecondsTaken() {
        return secondsTaken;
    }

    public void setSecondsTaken(double secondsTaken) {
        this.secondsTaken = secondsTaken;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(super.toString());
        String s = "APILogEntry{" +
                "userAgent='" + userAgent + '\'' +
                ", secondsTaken=" + secondsTaken +
                ", apiUrl='" + apiUrl + '\'' +
                '}';
        b.append(s);
        return b.toString();
    }
}
