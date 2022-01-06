package com.budgetfriendly.bms.auth.service;

import com.budgetfriendly.bms.auth.dto.OauthDTO;

import java.util.Map;

public interface OauthService {

    Map<String , Object> tokenGenerate(OauthDTO oauthDTO);
}
