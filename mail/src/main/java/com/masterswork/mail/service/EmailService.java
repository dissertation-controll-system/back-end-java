package com.masterswork.mail.service;

import com.masterswork.mail.api.dto.mail.EmailResponseDTO;
import com.masterswork.mail.api.dto.mail.EmailSendDTO;
import com.masterswork.mail.api.dto.mail.EmailSendFromTemplateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmailService {

    EmailResponseDTO prepareAndSend(EmailSendDTO emailSendDTO);

    EmailResponseDTO prepareAndSendFromTemplate(Long templateId, EmailSendFromTemplateDTO emailSendDTO);

    Page<EmailResponseDTO> getAllEmailsByUser(String username, Pageable pageable);

    Page<EmailResponseDTO> getAllEmails(Pageable pageable);
}
