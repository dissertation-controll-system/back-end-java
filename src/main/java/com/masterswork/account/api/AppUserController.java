package com.masterswork.account.api;

import com.masterswork.account.api.dto.appuser.AppUserCreateDTO;
import com.masterswork.account.api.dto.appuser.AppUserResponseDTO;
import com.masterswork.account.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/app-users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @PostMapping
    public ResponseEntity<AppUserResponseDTO> createAppUser(@Valid @RequestBody AppUserCreateDTO appUserCreateDTO) {
        return ResponseEntity.ok(appUserService.createAppUser(appUserCreateDTO));
    }

    @GetMapping("/types")
    public ResponseEntity<Set<String>> getAllUserTypes() {
        return ResponseEntity.ok(appUserService.getAllUserTypes());
    }
}
