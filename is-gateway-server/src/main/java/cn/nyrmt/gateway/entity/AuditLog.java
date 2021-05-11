package cn.nyrmt.gateway.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class AuditLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //请求方式 Get/Post/...
    private String requestMethod;
    //请求路径
    private String requestURI;
    //响应状态 404/500/...
    private Integer responseStatus;

    //谁请求的
    @CreatedBy
    @LastModifiedBy
    private String username;
    //请求创建时间

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    //请求修改时间

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyTime;

    public AuditLog() {
    }

    public AuditLog(Long id, String requestMethod, String requestURI, Integer responseStatus, String username, Date createTime, Date modifyTime) {
        this.id = id;
        this.requestMethod = requestMethod;
        this.requestURI = requestURI;
        this.responseStatus = responseStatus;
        this.username = username;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Integer responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
