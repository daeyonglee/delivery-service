package org.delivery.api.domain.token.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.domain.token.ifs.TokenHelperIfs;
import org.delivery.api.domain.token.model.TokenDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class TokenService {

	private final TokenHelperIfs tokenHelperIfs;

	public TokenDto issueAccessToken(Long userId) {
		var data = new HashMap<String, Object>();
		data.put("userId", userId);
		return tokenHelperIfs.issueAccessToken(data);
	}

	public TokenDto issueRefreshToken(Long userId) {
		var data = new HashMap<String, Object>();
		data.put("userId", userId);
		return tokenHelperIfs.issueRefreshToken(data);
	}

	public Long validationToken(String token) {
		var map = tokenHelperIfs.validationTokenWithThrow(token);
		var userId = map.get("userId");
		return Long.parseLong(userId.toString());
	}
}
