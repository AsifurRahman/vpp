package com.asif.vpp.config.authentication.service;

import com.asif.vpp.config.authentication.model.RefreshToken;
import com.asif.vpp.payload.request.RefreshTokenRequest;
import com.asif.vpp.payload.response.RefreshTokenResponse;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(Long userId);

    RefreshToken verifyRefreshTokenExpiration(RefreshToken refreshToken);

    RefreshTokenResponse tokenRefresh(RefreshTokenRequest request);
}
