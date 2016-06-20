package br.com.anteros.core.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class EmailAuthenticator extends Authenticator
{

    public EmailAuthenticator(String user, String password)
    {
        authentication = new PasswordAuthentication(user, password);
    }

    protected PasswordAuthentication getPasswordAuthentication()
    {
        return authentication;
    }

    private PasswordAuthentication authentication;
}