package com.rewe.distributor.service;

import com.rewe.distributor.dto.EmailDto;

/*
 * Email sending service
 * */
public interface EmailService {
    /**
     * Send email
     * @param  email {@link EmailDto}
     */
    void sendEmail(EmailDto email);
    /**
     * Send random email for topic
     * @param topic
     */
    void sendRandomEmail(String topic);
}
