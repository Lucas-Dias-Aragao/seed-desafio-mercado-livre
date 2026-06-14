package com.dev.eficiente.desafio.marketplace.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void enviarEmail(final String emailDestinatario, final String assuntoEmail, final String corpoEmail) {

        String email = """
                Destinatário: %s
                
                Assunto: %s
                
                Mensagem: %s
                """.formatted(emailDestinatario, assuntoEmail, corpoEmail);

        System.out.println(email);
    }
}
