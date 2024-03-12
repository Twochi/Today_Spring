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

    @Column
    private String memberEmail;

    private String memberId;

    private String memberPwd;

    private String phoneNumber;

    private String memberPhoto;

    private String memberName;
}

