# Java-Core-ATM
The program does the core functionality of an ATM, includung: adding account, changing PINS and Passwords, emailing fraudulent activities, etc.

After creatings a new account in the ATM main method call the add_list() method it return an updted list of accounts. 

Run the Serial.deserialize() method, which returns an array of accounts from the last time the application was used, to get the old accounts.

Run the Serial.serialize() method, which takes one parameter an Account array, to save the accounts to for the next usage.

In order to email you must add the javax.mail.jar file: https://javaee.github.io/javamail/
