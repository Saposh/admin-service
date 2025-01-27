package com.admin_service.demo.Service;

import com.admin_service.demo.models.AdminActionsLog;
import com.admin_service.demo.models.User;
import com.admin_service.demo.repositories.AdminActionsLogRepository;
import com.admin_service.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminActionsLogService {

    private final UserRepository userRepository;
    private final AdminActionsLogRepository adminActionsLogRepository;

    public AdminActionsLogService(UserRepository userRepository, AdminActionsLogRepository adminActionsLogRepository) {
        this.userRepository = userRepository;
        this.adminActionsLogRepository = adminActionsLogRepository;
    }

    public AdminActionsLog logAction(Long adminId, String actionType, String details) {
        // Загрузить объект User по adminId
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Admin with ID " + adminId + " not found"));

        // Создать лог действия
        AdminActionsLog log = new AdminActionsLog();
        log.setAdmin(admin); // Передаём объект User
        log.setActionType(actionType);
        log.setDetails(details);

        // Сохранить лог в базе данных
        return adminActionsLogRepository.save(log);
    }
}
