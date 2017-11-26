package Autentification;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.Scanner;

abstract public class UserManagement {
    /**
     *  Responsabilitate :
     *          Managementul conturilor si configuratiilor userilor.
     *
     *  Mod de functionare :
     *          Se foloseste de username si parola pentru a cripta
     *      un sir de caractere care reprezinta numele fisierului
     *      de configuratie al respectivului utilizator.
     *          Refuza logarile al carol username nu se afla in lista
     *      userilor si inregistrarile care folosesc aceasi user sau
     *      care ar genera aceasi nume de fisier de configurare precum
     *      alt user.
     */

    private static FileWriter globalFileWriter = null;

    private static LinkedList<String> users = new LinkedList<>();

    private static String currUser = null;

    public static void setUserList(){
        if(new File("users.txt").exists()){
            try {
                Scanner scan = new Scanner(new File("users.txt"));
                while (scan.hasNext())
                    users.add(scan.nextLine());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void updateUserList(String user) {
        try {
            if(new File("users.txt").exists()) {
                    Files.write(Paths.get("users.txt"),
                            (user+'\n').getBytes(), StandardOpenOption.APPEND);
            } else{
                FileWriter write = new FileWriter("users.txt");
                write.append(user+"\n");
                write.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String encript(String user, String password){
        int sumaUser = 0;
        int sumaPassword = 0;

        for (byte c : user.getBytes()) {
            sumaUser += (int)c;
        }
        for (byte c : password.getBytes()) {
            sumaPassword += (int)c;
        }

        return Integer.toString(sumaPassword)+Integer.toString(sumaUser);
    }

    public static boolean signUp(String user,String password) {

        if(users.isEmpty() || !users.contains(user)|| !(new File(encript(user, password)+"_config.txt").exists())){

            FileWriter w = null;
            try {
                users.add(user);
                updateUserList(user);
                w = new FileWriter(encript(user, password)+"_config.txt");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                if (w != null) {
                    try {
                        w.flush();
                        w.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            currUser = user;
            return true;
        }
        else
            return false;
    }

    public static boolean logIn(String user, String password){
        if(users.contains(user) && (new File(encript(user, password)+"_config.txt").exists())) {
            currUser = user;
            return true;
        }
        return false;
    }

    public static void logOut() {
        if (globalFileWriter != null) {
            try {
                globalFileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        currUser = null;
    }

    public static String getCurrUser() {
        return currUser;
    }
}

