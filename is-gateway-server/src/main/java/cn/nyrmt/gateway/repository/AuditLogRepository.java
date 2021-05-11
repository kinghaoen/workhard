package cn.nyrmt.gateway.repository;

import cn.nyrmt.gateway.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog,Long> {
}
