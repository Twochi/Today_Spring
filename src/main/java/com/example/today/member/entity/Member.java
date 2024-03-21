package com.example.today.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
@Entity
public class Member {

    @Id
    @Column(name = "MEMBER_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberCode;

    @Column(name = "MEMBER_EMAIL")
    private String memberEmail;

    @Column(name = "MEMBER_ID")
    private String memberId;

    @Column(name = "MEMBER_PWD")
    private String memberPwd;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "MEMBER_PHOTO")
    private String userImg;

    @Column(name = "MEMBER_NAME")
    private String memberName;

    @Column(name = "MEMBER_GENDER")
    private String gender;
}

