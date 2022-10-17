package com.masterswork.mail.service.impl;

import com.masterswork.mail.api.dto.mail.EmailResponseDTO;
import com.masterswork.mail.api.dto.mail.EmailSendDTO;
import com.masterswork.mail.api.dto.mail.EmailSendFromTemplateDTO;
import com.masterswork.mail.client.AccountClient;
import com.masterswork.mail.client.StorageClient;
import com.masterswork.mail.client.dto.account.AppUserResponseDTO;
import com.masterswork.mail.model.Email;
import com.masterswork.mail.model.Template;
import com.masterswork.mail.repository.EmailRepository;
import com.masterswork.mail.repository.TemplateRepository;
import com.masterswork.mail.service.EmailSenderService;
import com.masterswork.mail.service.EmailService;
import com.masterswork.mail.service.exception.TemplateNotFoundException;
import com.masterswork.mail.service.mapper.EmailMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;
    private final EmailMapper emailMapper;
    private final EmailSenderService emailSenderService;

    private final AccountClient accountClient;
    private final StorageClient storageClient;

    private final SpringTemplateEngine thymeleafTemplateEngine;
    private final TemplateRepository templateRepository;


    @Override
    public EmailResponseDTO prepareAndSend(EmailSendDTO emailSendDTO) {
        AppUserResponseDTO userCurrentUser = accountClient.getCurrentUser();
        AppUserResponseDTO userSendTo = accountClient.getUserById(emailSendDTO.getToId());
        Resource[] attachments = Optional.ofNullable(emailSendDTO.getAttachmentsIds()).stream()
                .flatMap(List::stream)
                .map(storageClient::loadById)
                .toArray(Resource[]::new);

        String body = emailSendDTO.getBody();
        String subject = emailSendDTO.getSubject();
        String fromEmail = userCurrentUser.getEmail();
        String toEmail = userSendTo.getEmail();

        return send(fromEmail, toEmail, subject, body, attachments);
    }

    @Override
    public EmailResponseDTO prepareAndSendFromTemplate(Long templateId, EmailSendFromTemplateDTO emailSendDTO) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new TemplateNotFoundException("Could not find template with Id: " + templateId));

        AppUserResponseDTO userCurrentUser = accountClient.getCurrentUser();
        AppUserResponseDTO userSendTo = accountClient.getUserById(emailSendDTO.getToId());
        Resource[] attachments = Optional.ofNullable(emailSendDTO.getAttachmentsIds()).stream()
                .flatMap(List::stream)
                .map(storageClient::loadById)
                .toArray(Resource[]::new);


        Context thymeleafContext = new Context();
        Map<String, Object> parameters = Optional.ofNullable(emailSendDTO.getParameters()).orElseGet(HashMap::new);
        parameters.put("from", userCurrentUser);
        parameters.put("to", userSendTo);
        thymeleafContext.setVariables(parameters);

        String body = thymeleafTemplateEngine.process(template.getThymleafTemplateName(), thymeleafContext);
        String subject = template.getSubject();
        String fromEmail = userCurrentUser.getEmail();
        String toEmail = userSendTo.getEmail();

        return send(fromEmail, toEmail, subject, body, attachments);
    }

    @Override
    public Page<EmailResponseDTO> getAllEmailsByUser(String username, Pageable pageable) {
        return emailRepository.findAllByCreatedBy(username, pageable)
                .map(emailMapper::toDTO);
    }

    @Override
    public Page<EmailResponseDTO> getAllEmails(Pageable pageable) {
        return emailRepository.findAll(pageable)
                .map(emailMapper::toDTO);
    }

    private EmailResponseDTO send(String from, String to, String subject, String body, Resource[] attachments) {
        Email email = new Email(subject, Instant.now(), from, to);
        EmailResponseDTO dto = emailMapper.toDTO(emailRepository.save(email));

        emailSenderService.sendEmail(from, to, subject, body, attachments);
        return dto;
    }
}
