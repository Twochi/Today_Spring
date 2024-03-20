package com.example.today.member.controller;

import com.example.today.common.ResponseDTO;
import com.example.today.member.dto.EmailCheckDTO;
import com.example.today.member.dto.MemberDTO;
import com.example.today.member.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Member;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/email")
    public ResponseEntity<ResponseDTO> emailAuthentication(@RequestBody MemberDTO memberDTO) {

        System.out.println("이메일 인증 이메일 :"+ memberDTO);

        authService.joinEmail(memberDTO.getMemberEmail());

        return ResponseEntity.ok()
                            .body(ResponseDTO.builder()
                                    .status(HttpStatus.valueOf(HttpStatus.CREATED.value()))
                                    .message("success")
                                    .build());



    }

    @PostMapping("/authNumCheck")
    public ResponseEntity<ResponseDTO> emailAuthNumCheck(@RequestBody EmailCheckDTO emailCheckDTO) {


        Boolean Checked = authService.checkAuthNum(emailCheckDTO.getEmail() ,emailCheckDTO.getAuthNum());



        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.valueOf(HttpStatus.CREATED.value()))
                        .message("success")
                        .data(Checked)
                        .build());
    }

    @PostMapping("/signUp")
    public ResponseEntity<ResponseDTO> signUp(@ModelAttribute MemberDTO memberDTO ,
                                                @RequestParam("memberPhoto") MultipartFile memberPhoto) {

        System.out.println(memberDTO);
        System.out.println(memberPhoto);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.valueOf(HttpStatus.CREATED.value()))
                        .message("success")
                        .build());
    }






}
