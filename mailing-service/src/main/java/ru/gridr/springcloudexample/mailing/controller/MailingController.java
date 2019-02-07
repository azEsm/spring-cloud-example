package ru.gridr.springcloudexample.mailing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gridr.springcloudexample.person.PersonDto;
import ru.gridr.springcloudexample.person.PersonService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MailingController {
    private final Logger log = LoggerFactory.getLogger(MailingController.class);

    private final PersonService personService;

    @Autowired
    MailingController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Send emails to all users
     *
     * @return usernames to whom messages were sent
     */
    @GetMapping(path = "send")
    public List<String> sendEmails() {
        List<PersonDto> persons = personService.allPersons();
        String messageTemplate = loadMessageTemplate();
        List<String> usersList = new ArrayList<>();
        for (PersonDto person : persons) {
            boolean success = sendEmail(person.getEmail(), person.getName(), messageTemplate);
            if (success) {
                usersList.add(person.getName());
            }
        }
        return usersList;
    }

    private boolean sendEmail(String email, String name, String messageTemplate) {
        String message = String.format(messageTemplate, name);
        log.info("Email successfully sent.\nAddress:{};\nMessage:{}", email, message);
        return true;
    }

    private String loadMessageTemplate() {
        return "Hello, %s";
    }
}
