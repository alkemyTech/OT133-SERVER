package com.alkemy.ong.service;

import java.io.IOException;

public interface MailService {

    void sendTextEmail(String mailTo, String mailSubject, String mailContent) throws IOException;

}
