package cn.nyrmt.user.bean;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

public class ExceptionEntity {
    private String message;

    private int code;

    private String error;

    private String path;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public ExceptionEntity() {
    }

    public ExceptionEntity(String message, int code, String error, String path, Date timestamp) {
        this.message = message;
        this.code = code;
        this.error = error;
        this.path = path;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
