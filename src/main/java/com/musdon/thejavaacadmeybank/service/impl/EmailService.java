package com.musdon.thejavaacadmeybank.service.impl;

import com.musdon.thejavaacadmeybank.dto.EmailDetails;

public interface EmailService {

    void sendEmailAlert(EmailDetails emailDetails);
    void sendEmailWithAttachment(EmailDetails emailDetails);
}
