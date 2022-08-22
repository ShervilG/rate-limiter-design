package service;

public interface RateLimiter {
    boolean allowRequest();
}
