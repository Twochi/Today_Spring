package com.example.today.member.dto;


import jakarta.validation.constraints.Email;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberDTO {

    private Long memberCode;

    @Email
    private String memberEmail;

    private String memberId;

    private String memberPwd;

    private String phoneNumber;

    private String memberName;

    private String gender;

    private String userSecretKey;

    private String userImg;

}
