package ATM;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by techc on 6/16/2017.
 */
public class ATM {
    // Add the new account to the Array
    private static Account [] add_list(Account account, ArrayList<Account> acon){
        acon.add(account);
        Account [] acc = new Account[acon.size()];
        for (int i = 0; i < acon.size(); i++){
            acc[i] =  acon.get(i);
        }
        return acc;
    }


    public static void main(String[] args) {
        Account [] accounts = null;
        ArrayList<Account> acon = new ArrayList<Account>();

        Account User = new Account("User User", 120.00, "pass", accounts);
        accounts = add_list(User, acon);

        // Serialize the account array to the accounts.txt file for the next usage.
        Serial.serialize(accounts);

        // Run at the beginning of the program to get the previous accounts
        // accounts = Serial.dserialize();

    }
}