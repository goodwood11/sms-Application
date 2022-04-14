package com.example.smsapplication.web.controller;

import com.example.smsapplication.dtos.APIResponse;
import com.example.smsapplication.dtos.MessageDto;
import com.example.smsapplication.sms.SmsService;
import com.example.smsapplication.web.exceptions.MessageApplicationException;
import com.example.smsapplication.web.exceptions.SmsBusinessException;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/API")
public class SmsController {

    private final SmsService smsService;

    @Autowired
    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/outbound/sms")
    public ResponseEntity<?>sendMessage(@RequestBody MessageDto request){

        try{
            smsService.sendSms(request);
            return new ResponseEntity<>(new APIResponse("outbound sms ok", ""), HttpStatus.OK);
        }catch (SmsBusinessException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/inbound/sms")
    public ResponseEntity<?>receiveMessage(@Valid @NotNull  @RequestBody MessageDto request){
        try{
            smsService.receiveSms(request);
            return new ResponseEntity<>(new APIResponse("inbound sms ok", ""), HttpStatus.OK);
        }catch (SmsBusinessException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}