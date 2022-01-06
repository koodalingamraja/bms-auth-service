package com.budgetfriendly.bms.auth.controller;

import com.budgetfriendly.bms.auth.dto.OauthDTO;
import com.budgetfriendly.bms.auth.service.OauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("oauth")
public class OauthController {

    @Autowired
    private OauthService oauthService;

    public static final String RESPONSE = "response";
    public static final String STATUS = "status";

    @PostMapping("token")
    public ResponseEntity<String> authenticate(@RequestBody OauthDTO oauthDTO) {

        Map<String, Object> responseMap = oauthService.tokenGenerate(oauthDTO);

        if(responseMap!=null) {
            return new ResponseEntity<>(String.valueOf(responseMap.get(RESPONSE)), (HttpStatus) responseMap.get(STATUS));
        }else {
            return new ResponseEntity<>(String.valueOf("Data Not Found"), HttpStatus.OK);
        }


    }
}
