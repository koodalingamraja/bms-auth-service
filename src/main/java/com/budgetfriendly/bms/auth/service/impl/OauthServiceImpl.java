package com.budgetfriendly.bms.auth.service.impl;

import com.budgetfriendly.bms.auth.constant.AuthConstant;
import com.budgetfriendly.bms.auth.dto.*;
import com.budgetfriendly.bms.auth.entity.MasterRole;
import com.budgetfriendly.bms.auth.entity.OauthTokenDetail;
import com.budgetfriendly.bms.auth.entity.UserRoleMapping;
import com.budgetfriendly.bms.auth.entity.Users;
import com.budgetfriendly.bms.auth.repository.OauthTokenRepository;
import com.budgetfriendly.bms.auth.repository.UserRoleMappingRepository;
import com.budgetfriendly.bms.auth.repository.UsersRepository;
import com.budgetfriendly.bms.auth.service.OauthService;
import com.budgetfriendly.bms.auth.utill.OauthUtill;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.jsonwebtoken.SignatureAlgorithm;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.annotation.PostConstruct;

@Service
public class OauthServiceImpl implements OauthService {

    @Value("${subject}")
    private String subject;

    @Value("${issuer}")
    private String issuer;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private OauthTokenRepository oauthTokenRepository;

    @Autowired
    private UserRoleMappingRepository userRoleMappingRepository;

    @Autowired
    private OauthUtill oauthUtill;

    private RSAPrivateKey pk;

    public OauthServiceImpl(){
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    @PostConstruct
    public void init() {
        try {
            Resource resource = new ClassPathResource(AuthConstant.OAUTH);
            byte[] bdata = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String privateKey = new String(bdata, StandardCharsets.UTF_8);
            byte[] keyBytes = org.apache.commons.codec.binary.Base64.decodeBase64(privateKey);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
            this.pk = (RSAPrivateKey) kf.generatePrivate(privateKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> tokenGenerate(OauthDTO oauthDTO) {

        Map<String, Object> responseMap = new HashMap<String, Object>();

        try{

            JSONObject jsonRes = new JSONObject();

            if (validateRequestParams(oauthDTO)) {

                String userName = "";

                userName = oauthDTO.getUserName();

                Users user = usersRepository.findByUserName(userName);

                if (user == null) {

                    jsonRes.put("message", "Invalid User Credentials");
                    jsonRes.put("status", HttpStatus.UNAUTHORIZED);
                    responseMap.put("response", jsonRes);
                    return responseMap;

                }else{

                    if(user.getStatus() != true){
                        jsonRes.put("message", "Invalid User Credentials");
                        jsonRes.put("status", HttpStatus.UNAUTHORIZED);
                        responseMap.put("response", jsonRes);
                        return responseMap;
                    }
                }

                UserRoleMapping usersRoleMapping = userRoleMappingRepository.findByUsersId(user.getId());


                boolean isAuthenticated = false;

                if(userName.equalsIgnoreCase(user.getUserName())){
                   isAuthenticated = true;
                }

                if (isAuthenticated) {

                    try {
                        jwtTokenGenerators(user,usersRoleMapping.getMasterRole(),responseMap);
                    } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                        e.printStackTrace();
                    }
                    responseMap.put("status", HttpStatus.OK);
                    return responseMap;

                }

            }else{
                jsonRes.put("message", "Invalid User Credentials");
                jsonRes.put("status", HttpStatus.UNAUTHORIZED);
                responseMap.put("response", jsonRes);
                return responseMap;
            }



        }catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseMap;
    }

    public boolean validateRequestParams(OauthDTO oauthDTO){

       if(oauthDTO != null){
          return !StringUtils.isEmpty(oauthDTO.getUserName());
       }else{
           return false;
       }

    }

    public void jwtTokenGenerators(Users user, MasterRole masterRole, Map<String, Object> responseMap) throws NoSuchAlgorithmException, NoSuchProviderException{

        JSONObject jsonRes = new JSONObject();

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUserName());
        userDTO.setEmail(user.getEmail());
        userDTO.setMobile(user.getMobile());
        userDTO.setDob(user.getDob());
        userDTO.setGender(user.getGender());
        userDTO.setStatus(user.getStatus());

        MasterCityDTO cityDTO = new MasterCityDTO();
        cityDTO.setId(user.getMasterCity().getId());
        cityDTO.setCityName(user.getMasterCity().getCityName());
        cityDTO.setCityCode(user.getMasterCity().getCityCode());
        cityDTO.setStatus(user.getMasterCity().getStatus());
        cityDTO.setCreatedAt(user.getMasterCity().getCreatedAt());
        userDTO.setMasterCityDTO(cityDTO);

        MasterStateDTO stateDTO = new MasterStateDTO();
        stateDTO.setId(user.getMasterState().getId());
        stateDTO.setStateName(user.getMasterState().getStateName());
        stateDTO.setStateCode(user.getMasterState().getStateCode());
        stateDTO.setStatus(user.getMasterState().getStatus());
        stateDTO.setCreatedAt(user.getMasterState().getCreatedAt());
        userDTO.getMasterCityDTO().setMasterStateDTO(stateDTO);

        MasterRoleDTO masterRoleDTO = new MasterRoleDTO();
        masterRoleDTO.setId(masterRole.getId());
        masterRoleDTO.setRoleName(masterRole.getRoleName());
        masterRoleDTO.setRoleDescription(masterRole.getRoleDescription());
        masterRoleDTO.setStatus(masterRole.getStatus());
        masterRoleDTO.setCreatedAt(masterRole.getCreatedAt());
        userDTO.setMasterRoleDTO(masterRoleDTO);

        Map<String, Object> userInfo = new ObjectMapper().convertValue(userDTO, Map.class);

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;

        int tokenExpiryMins= 30;

        long expDate = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(tokenExpiryMins);

        userInfo.put("exp_str", expDate);

        JwtBuilder builder = Jwts.builder().setClaims(userInfo)
                .setHeaderParam("typ", "JWT")
                .setId(String.valueOf(user.getId()))
                .setExpiration(new Date(expDate))
                .setIssuedAt(new Date())
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, pk);

        String accessToken = builder.compact();

        JwtBuilder jwtRefreshBuilder = Jwts.builder().setId(String.valueOf(user.getId()))
                .setHeaderParam("typ", "JWT")
                .setSubject(subject)
                .setExpiration(Date.from(ZonedDateTime.now().plusMonths(1).toInstant()))
                .setIssuedAt(new Date())
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, pk);

        String jwtRefreshToken = jwtRefreshBuilder.compact();

        boolean vFlag = saveOrUpadteToken(user, accessToken, jwtRefreshToken);

        int curAttempt = 0;

        if (vFlag) {

            jsonRes.put("access_token", "Bearer " + accessToken);
            jsonRes.put("refresh_token", "Bearer " + jwtRefreshToken);
            jsonRes.put("token_type", "Bearer");
            jsonRes.put("status", HttpStatus.OK);
            jsonRes.put("current_attempt",
                    "The has " + (5 - curAttempt) + " remaining attempts to login into the system ");
            responseMap.put("response", jsonRes);
            responseMap.put("message", "success");

        } else {

            jsonRes.put("message", "User has been temporaly locked for continus five wrong attempt");
            jsonRes.put("status", HttpStatus.LOCKED);
            responseMap.put("response", jsonRes);
            responseMap.put("message", "fail");

        }


    }


    public boolean saveOrUpadteToken(Users user, String accessToken, String jwtRefreshToken){

        OauthTokenDetail oauthTokenDetail = null;
        int tokenExpiryMins= 30;
        boolean bflag = false;

        List<OauthTokenDetail> extTokenDetList = oauthTokenRepository.findFirstByUsersIdOrderByCreatedAtDesc(user.getId());

        if (extTokenDetList != null && extTokenDetList.size() > 0) {

            for (OauthTokenDetail tokenDet : extTokenDetList) {

                int totAttempt = tokenDet.getTotAttempt() != null ? tokenDet.getTotAttempt() : 0;
                long diffMinutes = (new Date().getTime() - tokenDet.getCreatedAt().getTime()) / (60 * 1000);

                if (diffMinutes < tokenExpiryMins) {

                    if (totAttempt < 5) {
                        oauthTokenDetail = new OauthTokenDetail();
//                        totAttempt = loginFlag ? totAttempt += 1 : totAttempt;
                        oauthTokenDetail.setId(tokenDet.getId());
                        oauthTokenDetail.setAccessToken(accessToken);
                        oauthTokenDetail.setRefreshToken(jwtRefreshToken);
                        oauthTokenDetail.setUsers(user);
                        oauthTokenDetail.setTotAttempt(1);
                        oauthTokenDetail.setCreatedAt(tokenDet.getCreatedAt());

                        oauthTokenRepository.save(oauthTokenDetail);
                        bflag = true;
                    }else {

                        usersRepository.updateBlockUser(user.getId());
                        bflag = false;

                    }

                }else {

                    oauthTokenDetail = new OauthTokenDetail();
                    oauthTokenDetail.setAccessToken(accessToken);
                    oauthTokenDetail.setRefreshToken(jwtRefreshToken);
                    oauthTokenDetail.setUsers(user);
                    oauthTokenDetail.setTotAttempt(1);
                    oauthTokenDetail.setCreatedAt(new Date());

                    oauthTokenRepository.save(oauthTokenDetail);
                    bflag = true;

                }

            }

        }else {

            oauthTokenDetail = new OauthTokenDetail();
            oauthTokenDetail.setAccessToken(accessToken);
            oauthTokenDetail.setRefreshToken(jwtRefreshToken);
            oauthTokenDetail.setUsers(user);
            oauthTokenDetail.setTotAttempt(1);
            oauthTokenDetail.setCreatedAt(new Date());

            oauthTokenRepository.save(oauthTokenDetail);
            bflag = true;
        }
        return bflag;

    }
}
