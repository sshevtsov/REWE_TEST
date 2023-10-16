package com.rewe.distributor.web;

import com.rewe.distributor.dto.EmailDto;
import com.rewe.distributor.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * Controller for sending emails
 * */
@RestController
@RequestMapping("email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDto email) {
        emailService.sendEmail(email);
        return ResponseEntity.ok("Email was sent successfully");
    }

    @PostMapping("/send/random")
    public ResponseEntity<String> sendRandomEmail(@RequestParam(name = "topic") String topic) {
        emailService.sendRandomEmail(topic);
        return ResponseEntity.ok("Random email was sent successfully");
    }
}
