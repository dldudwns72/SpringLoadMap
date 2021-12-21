package hello.advanced.trace;

import lombok.Data;

// Log의 상태정보 저장 객체
public class TraceStatus {

    private TraceId traceId;
    private Long startTimeMs;
    private String message;

    public TraceStatus(TraceId traceId, Long startTimeMs, String message) {
        this.traceId = traceId;
        this.startTimeMs = startTimeMs;
        this.message = message;
    }
    public Long getStartTimeMs() {
        return startTimeMs;
    }
    public String getMessage() {
        return message;
    }
    public TraceId getTraceId() {
        return traceId;
    }


}
