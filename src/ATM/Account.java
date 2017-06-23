package ATM;


import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.util.*;

public class Account implements Serializable{
    private int ID;
    private double balance;
    private String name;
    private String accountType;
    private String creditCardNumber;
    private int pin;
    private String username;
    private String password;
    private String email;

    //Constructor
    protected Account(String Name, double Balance, String Password, Account [] accounts) {
        this.setName(Name);
        this.setID();
        this.setBalance(Balance);
        this.setPassword(Password);
        this.setCreditCardNumber(accounts);

        // Set accountType and pin, and verifies they meet the requirements
        boolean condition_Username = false;
        do {
            System.out.print("Username: ");
            Scanner scanner = new Scanner(System.in);
            String USERNAME = scanner.next();
            if (accounts != null) {
                for (Account account : accounts) {
                    if (account.getUsername().equals(USERNAME)) {
                        condition_Username = false;
                        System.out.println("Username taken");
                        break;
                    }
                    else{
                        this.setUsername(USERNAME);
                        condition_Username = true;
                    }
                }
            }
            else{
                condition_Username = true;
                this.setUsername(USERNAME);
            }
        }
        while (!condition_Username);

        boolean condition_EMAIL = false;
        boolean checking = false;
        do {
            System.out.print("What is your email address: ");
            Scanner scanner = new Scanner(System.in);
            String EMAIL = scanner.next();
            try {
                InternetAddress emailAddr = new InternetAddress(EMAIL);
                emailAddr.validate();
                checking = false;
                this.setEmail(EMAIL);
            } catch (AddressException ex) {
                checking = true;

            }
            if (checking == true){
                condition_EMAIL = false;
            }else{
                condition_EMAIL = true;
            }
        }
        while (!condition_EMAIL);

        boolean condition_AT = false;
        do {
            System.out.print("What kind of account would you like to create: ");
            Scanner scanner = new Scanner(System.in);
            String AT = scanner.next();
            if (AT.equals("Checking") || AT.equals("Savings")) {
                condition_AT = true;
                this.setAccountType(AT);
            }
        }
        while (!condition_AT);
        boolean condition_PIN = false;
        do {
            System.out.print("What do you want your pin to be: ");
            Scanner scanner = new Scanner(System.in);
            String PIN = scanner.next();
            if (PIN.length() == 4) {
                condition_PIN = true;
                this.setPin(Integer.parseInt(PIN));
            }
        }
        while (!condition_PIN);

    }


    // Getters
    protected int getID(){
        return ID;
    }
    protected double getBalance(){
        return balance;
    }
    protected String getName(){
        return name;
    }
    protected String getAccountType(){
        return accountType;
    }
    protected String getCreditCardNumber(){
        return creditCardNumber;
    }
    protected int getPin(){
        return pin;
    }
    protected String getPassword() {
        return password;
    }
    protected String getUsername() {
        return username;
    }
    protected String getEmail(){
        return email;
    }



    // Setters
    private void setID(){
        Random rand = new Random();
        this.ID = rand.nextInt(100000000) + 1;
    }
    private void setName(String n){
        this.name = n;
    }
    protected void setBalance(double bal){
        this.balance = bal;
    }
    protected void setAccountType(String at){
        this.accountType = at;
    }
    private void setCreditCardNumber(Account [] accounts){
        boolean condition_CREDT = false;
        do {
            long digits = (long) (Math.random() * 10000000000000L);
            this.creditCardNumber = Long.toString(8600000000000000L + digits);
            String CREDIT_NUMBER = this.getCreditCardNumber();
            if (accounts != null) {
                for (int i = 0;i < accounts.length; i++) {
                    if (CREDIT_NUMBER.equals(accounts[i].getCreditCardNumber())) {
                        break;
                    }
                    else if( (i == (accounts.length - 1)) && (!CREDIT_NUMBER.equals(accounts[i].getCreditCardNumber()))){
                        condition_CREDT = true;
                        this.creditCardNumber = CREDIT_NUMBER;
                    }

                }
            }
            else{
                condition_CREDT = true;
            }
        }
        while (!condition_CREDT);
    }
    protected String newCreditCardNumber(Account [] accounts) {
        boolean condition = false;
        do{
            long digits = (long) (Math.random() * 10000000000000L);
            String new_number = Long.toString(8600000000000000L + digits);
            for (int i = 0;i < accounts.length; i++) {
                if (new_number.equals(accounts[i].getCreditCardNumber())) {
                    break;
                }
                else if ( (i == (accounts.length - 1) && (!new_number.equals(accounts[i].getCreditCardNumber())))){
                    condition = true;
                    this.creditCardNumber = new_number;
                }
            }

        } while (!condition);
        return this.creditCardNumber;
    }
    protected void setPin(int p){
        this.pin = p;
    }
    private void setPassword(String password) {
        this.password = password;
    }
    private void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email){
        this.email = email;
    }

    // Methods
    protected void withdrawal(float amount){
        this.setBalance(this.getBalance() - amount);;
    }
    protected void deposit(float amount){
        this.setBalance(this.getBalance() + amount);
    }
    protected void changePin(int newPin){
        if (String.valueOf(newPin).length() == 4) {
            this.setPin(newPin);
        }
        fraud();
        sendMail();
    }
    protected void cardStolen(Account [] accounts, String Username, String Password) {
        if (this.login(Username, Password, accounts)){
            newCreditCardNumber(accounts);
            fraud();
            sendMail();
        }
        else{
            System.out.println("Incorrect username/password combination");
        }


    }
    protected void changePassword(Account [] accounts, String Username, String old_Password, String new_Password) {
        if (this.login(Username, old_Password, accounts)){
            this.password = new_Password;
            fraud();
            sendMail();
        }

    }

    protected boolean login(String username, String password, Account [] accounts){
        Boolean condition = null;
        for (Account account: accounts){
            if (account.getUsername().equals(username))
                if ((account.getPassword().equals(password))) {
                    condition = true;
                    break;
                } else {
                    condition = false;
                    fraud();
                    sendMail();
                    break;

                }
            else {
                condition = false;
            }
        }
        return condition;
    }
    private static void fraud(){
        System.out.println("Fraudulent activity");

    }
    private void sendMail() {
        final String username = "username@gmail.com";
        final String password = "password";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("username@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(this.getEmail()));
            message.setSubject("Bank");
            message.setText("Dear " +  this.getName() + ","
                    + "\n\n\tYou are receiving this email, because their was potential fraudulent activity on your Bank account. " +
                    "If you are responsible for this activity please disregard this email. If you aren't please contact Bank." +
                    "\n\nContact us, " +
                    "\nJalen Hardy CEO," +
                    "\ntechcenter97@gmail.com");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}

