package com.masterswork.mail.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {


    @GetMapping
    public ResponseEntity<?> listUploadedFiles() {
        return ResponseEntity.noContent().build();
    }
}
