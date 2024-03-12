package com.example.today.member.dto;


import jakarta.validation.constraints.Email;

import lombok.*;

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

    private String memberPhoto;

    private String memberName;


}
