package com.example.today.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
@Entity
@SequenceGenerator(
        name = "MEMBER_CODE_GENERATOR",
        sequenceName = "SEQ_TBL_MEMBER_MEMBER_CODE",
        initialValue = 1,
        allocationSize = 1
)
public class memberEntity {

    @Id
    @Column(name = "MEMBER_CODE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "")
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
    private String memberPhoto;

    @Column(name = "MEMBER_NAME")
    private String memberName;
}

