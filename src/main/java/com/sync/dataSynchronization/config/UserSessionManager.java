package com.sync.dataSynchronization.config;

import com.sync.dataSynchronization.rest.exceptions.BusinessException;
import com.sync.dataSynchronization.service.dto.UserAuthenticateDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope("singleton")
public class UserSessionManager {

    private final Map<Integer, UserAuthenticateDTO> activeSessions;

    public UserSessionManager() {
        activeSessions = new HashMap<>();
    }

    public void createSession(UserAuthenticateDTO userCampaignDetail) {
        if (activeSessions.containsKey(userCampaignDetail.getId())) throw new BusinessException(
            ErrorCode.USER_EXIST_IN_SESSION);
        activeSessions.put(userCampaignDetail.getId(), userCampaignDetail);
    }

    public List<UserAuthenticateDTO> getAllSession() {
        return activeSessions.values().stream().toList();
    }

    public void invalidateSession(Integer userCampaignDetailId) {
        activeSessions.remove(userCampaignDetailId);
    }
}
