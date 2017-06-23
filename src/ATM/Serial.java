package ATM;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by techc on 6/21/2017.
 */
public class Serial {
    protected static void serialize(Account [] account){
        File file = new File("Account.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(account);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected static Account[] dserialize() {
        File file = new File("Account.txt");
        Account [] accounts = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            accounts = (Account []) ois.readObject();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(("").getBytes());

            return accounts;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return accounts;
    }
}
