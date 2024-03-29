package com.example.today.member.service;

import com.example.today.common.RedisUtil;
import com.example.today.member.dto.MemberDTO;
import com.example.today.member.entity.Member;
import com.example.today.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JavaMailSender mailSender;

    private final RedisUtil redisUtil;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final MemberRepository memberRepository;
    private int authNumber;




    //임의의 6자리 양수를 반환합니다.
    public void makeRandomNumber() {
        Random r = new Random();
        String randomNumber = "";
        for(int i = 0; i < 6; i++) {
            randomNumber += Integer.toString(r.nextInt(10));
        }

        authNumber = Integer.parseInt(randomNumber);
    }

    // 랜덤한 JWT SecretKey 생성
    private static byte[] generateRandomBytes(int length) throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return randomBytes;
    }


    public String joinEmail(String email) {

        System.out.println("email" + email);

        makeRandomNumber();

        String setFrom = "2giwon0213@gmail.com"; // email-config에 설정한 자신의 이메일 주소를 입력
        String toMail = email;
        String title = "회원 가입 인증 이메일 입니다."; // 이메일 제목
        String content =
                "<div style=\"text-align: center; background-color: #f5f5f5; padding: 20px; border-radius: 10px;\">" +
                        "<h1 style=\"color: #3498db;\">'오늘 하루'에 방문해주셔서 감사합니다!</h1>" +
                        "<p style=\"font-size: 18px; color: #666;\">인증 번호는 <strong style=\"color: #e74c3c; font-size: 24px;\">"
                        + authNumber + "</strong>입니다.</p>" +
                        "<p style=\"font-size: 18px; color: #666;\">인증번호를 제대로 입력해주세요.</p>" +
                        "<br>" +
//                        "<div style=\"background-color: #3498db; color: #fff; padding: 10px; border-radius: 5px;\">" +
//                        "<p style=\"font-size: 16px;\">이메일은 보안을 위해 빠르게 사용되지만, 보안을 위해 장기간 저장하지 마세요.</p>" +
                        "</div>" +
                        "</div>";
        mailSend(setFrom, toMail, title, content);

        return Integer.toString(authNumber);

    }

    public void mailSend(String setFrom, String toMail, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();//JavaMailSender 객체를 사용하여 MimeMessage 객체를 생성
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");//이메일 메시지와 관련된 설정을 수행합니다.
            // true를 전달하여 multipart 형식의 메시지를 지원하고, "utf-8"을 전달하여 문자 인코딩을 설정
            helper.setFrom(setFrom);//이메일의 발신자 주소 설정
            helper.setTo(toMail);//이메일의 수신자 주소 설정
            helper.setSubject(title);//이메일의 제목을 설정
            helper.setText(content,true);//이메일의 내용 설정 두 번째 매개 변수에 true를 설정하여 html 설정으로한다.
            mailSender.send(message);
        } catch (MessagingException e) {//이메일 서버에 연결할 수 없거나, 잘못된 이메일 주소를 사용하거나, 인증 오류가 발생하는 등 오류
            // 이러한 경우 MessagingException이 발생
            e.printStackTrace();//e.printStackTrace()는 예외를 기본 오류 스트림에 출력하는 메서드
        }

        redisUtil.setDataExpire(Integer.toString(authNumber),toMail,60*5L);


    }

    public Boolean checkAuthNum(String email,String authNum) {

        if(redisUtil.getData(authNum)==null){
            return false;
        }
        else if(redisUtil.getData(authNum).equals(email)){
            return true;
        }
        else{
            return false;
        }
    }

    @Transactional
    public void signUp(MemberDTO memberDTO, MultipartFile memberPhoto) throws Exception {

        int keyLength = 64;

        String imageName = UUID.randomUUID().toString().replace("-", "");

        // 안전한 랜덤 바이트 생성
        byte[] keyBytes = generateRandomBytes(keyLength);

        // Base64로 인코딩하여 JWT 시크릿 키 생성
        String jwtKey = Base64.getEncoder().encodeToString(keyBytes);

        memberDTO.setUserSecretKey(jwtKey);


        if(memberDTO.getGender().equals("female")) {

            memberDTO.setGender("F");
        } else if(memberDTO.getGender().equals("male")) {

            memberDTO.setGender("M");
        }

        memberDTO.setMemberPhoto(memberPhoto);

        memberDTO.setMemberPwd(passwordEncoder.encode(memberDTO.getMemberPwd())); // 비밀번호 인코딩

        Member member = modelMapper.map(memberDTO, Member.class);

        memberRepository.save(member);


    }
}
