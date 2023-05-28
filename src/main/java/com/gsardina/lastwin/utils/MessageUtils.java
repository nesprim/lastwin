package com.gsardina.lastwin.utils;

public class MessageUtils {
    public static final String OK = "OK";
    public static final String KO = "KO";
    public static final String MESSAGE_OK = "Operazione avvenuta con successo";
    public static final String MESSAGE_KO = "Errori durante l'operazione";
    public static final String SIGNIN_SUCCESSFUL = "Login effettuato con successo";
    public static final String SIGNUP_SUCCESSFUL = "Registrazione effettuata con successo. Riceverete a breve una mail di conferma";
    public static final String BAD_CREDENTIALS = "Credenziali inserite errate";
    public static final String USER_NOT_FOUND_MESSAGE = "User not found with username: ";
    public static final String USERNAME_UNAVAILABLE = "Username non disponibile";
    public static final String INVALID_USERNAME = "Username non valido";
    public static final String INVALID_CONFIRM_CODE = "Codice conferma non valido";
    public static final String ACCOUNT_CONFIRMED = "Account confermato correttamente";
    public static final String ACCOUNT_ALREADY_CONFIRMED = "Account già confermato";
    public static final String EMAIL_ALREADY_USED = "Email già utilizzata";
    public static final String INVALID_ROLE = "Ruolo non valido";
    public static final String CONFIRM_SIGNUP = "Conferma Registrazione";
    public static final String WRONG_CONFIRM_CODE = "Codice Conferma ERRATO";
    public static final String ACCOUNT_NOT_CONFIRMED = "L'account non è stato ancora confermato. Riceverete a breve il codice di conferma alla mail indicata in fase di registrazione";
    public static final String MAIL_BODY_CONFIRM_SIGNUP = """
            Gentile <username>,
            
            per confermare la registrazione del suo account la invitiamo di utilizzare il seguente codice: <code>

            Si prega di non rispondere a tale mail,
            Lastwin""";
}
