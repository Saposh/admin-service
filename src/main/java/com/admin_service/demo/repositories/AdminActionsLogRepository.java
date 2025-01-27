package com.admin_service.demo.repositories;

import com.admin_service.demo.models.AdminActionsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminActionsLogRepository extends JpaRepository<AdminActionsLog, Long> {
    List<AdminActionsLog> findByAdminId(Long adminId);
}

