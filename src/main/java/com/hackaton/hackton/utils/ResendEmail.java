package com.hackaton.hackton.utils;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.Attachment;
import com.resend.services.emails.model.SendEmailRequest;
import com.resend.services.emails.model.SendEmailResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Log4j2
public class ResendEmail {

  @Value("${resend.api-key}")
  private String apiKey;

  public void sendEmail(String fileAttachment, String attachName, String email) {
    try {
      Resend resend = new Resend(apiKey);

      String fileContent = Utils.encodeFileToBase64(fileAttachment);

      Attachment att = Attachment.builder().fileName(attachName).content(fileContent).build();

      SendEmailRequest sendEmailRequest =
          SendEmailRequest.builder()
              .from("clockin@resend.dev")
              .to(email)
              .subject("Report")
              .attachments(att)
              .html("<p>Relatório de Ponto Mensal</p>")
              .build();

      SendEmailResponse data = resend.emails().send(sendEmailRequest);
    } catch (ResendException e) {
      log.error("Error Send email");
      throw new RuntimeException(e);
    } catch (IOException e) {
      log.error("Error open file");
      throw new RuntimeException(e);
    }
  }
}
