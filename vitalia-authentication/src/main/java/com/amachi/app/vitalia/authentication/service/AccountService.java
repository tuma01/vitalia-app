package com.amachi.app.vitalia.authentication.service;

import com.amachi.app.vitalia.authentication.dto.ActivationRequest;
import com.amachi.app.vitalia.authentication.dto.ChangePasswordRequest;
import com.amachi.app.vitalia.authentication.dto.PasswordResetConfirmationRequest;
import com.amachi.app.vitalia.authentication.dto.PasswordResetRequest;
import com.amachi.app.vitalia.common.dto.UserSummaryDto;

public interface AccountService {
    void activateAccount(ActivationRequest request);
    void requestPasswordReset(PasswordResetRequest request);
    void resetPassword(PasswordResetConfirmationRequest request);
    void changePassword(ChangePasswordRequest request, Long userId);
    UserSummaryDto getProfile(Long userId);
}
