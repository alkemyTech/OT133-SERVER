package com.alkemy.ong.repository.service;

import java.io.IOException;

public interface MailService {

    void sendTextEmail(String mailTo, String mailSubject, String mailContent) throws IOException;

}
