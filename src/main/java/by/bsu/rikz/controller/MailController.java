package by.bsu.rikz.controller;

import by.bsu.rikz.entity.TestInfoContext;
import by.bsu.rikz.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by USER on 14.12.2016.
 */
@RestController
public class MailController {

    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/sendNotification")
    public ResponseEntity<?> sendNotification(@RequestBody TestInfoContext assignment) {
        mailService.sendNotification(assignment);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sendResult")
    public ResponseEntity<?> sendResult(@RequestBody TestInfoContext assignment) {
        mailService.sendResult(assignment);
        return ResponseEntity.ok().build();
    }
}
