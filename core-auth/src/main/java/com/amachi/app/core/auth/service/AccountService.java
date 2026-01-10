package com.amachi.app.core.auth.service;

import com.amachi.app.core.auth.dto.ActivationRequest;
import com.amachi.app.core.auth.dto.ChangePasswordRequest;
import com.amachi.app.core.auth.dto.PasswordResetConfirmationRequest;
import com.amachi.app.core.auth.dto.PasswordResetRequest;
import com.amachi.app.core.common.dto.UserSummaryDto;

public interface AccountService {
    void activateAccount(ActivationRequest request);

    void requestPasswordReset(PasswordResetRequest request);

    void resetPassword(PasswordResetConfirmationRequest request);

    void changePassword(ChangePasswordRequest request, Long userId);

    void deleteAccount(Long userId);

    UserSummaryDto getProfile(Long userId);
}
