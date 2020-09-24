package com.dpt.library.system;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;

/*
    This class do everything with users in library.
 */
public class Users {
    private Scanner scan = new Scanner(System.in);
    public static List <String> name = new ArrayList<>();
    public static List <String> password = new ArrayList<>();
    public static List <String> nickname = new ArrayList<>();
    public static List <Integer> age = new ArrayList<>();

    public Users() { // for use config class

    }

    public Users(String name, String password, String nickname, int age) {
        this.name.add(name);
        this.password.add(password);
        this.nickname.add(nickname);
        this.age.add(age);
        System.out.println("Registered!");
    }

    public void checkUsers() { // for list all users in database
        if(this.name.size() == 0) {
            System.out.println("No users found in database... Create a new one");
        }
        for(int i = 0; i < this.name.size(); i++) {
            System.out.println("User number: " + (i+1));
            System.out.println("User name: " + name.get(i));
            System.out.println("User password: " + password.get(i));
            System.out.println("User nickname: " + nickname.get(i));
            System.out.println("User age: " + age.get(i));
        }
    }

    public int logIn(String name, String password) {
        for(int i = 0; i < this.name.size(); i++) {
            if((name.contains(this.name.get(i))) && (password.contains(this.password.get(i)))) {
                System.out.println("Logged!");
                return 0;
            }
        }
        return 3;
    }
    public boolean saveUsers() {
        try {
            if(Files.exists(Paths.get("users.dpt"))) {
                Files.delete(Paths.get("users.dpt"));
            }
            Files.createFile(Paths.get("users.dpt"));
            File file = new File("users.dpt");
            PrintWriter printScan = new PrintWriter(file);
            System.out.println("name size = " + name.size());
            for(int i = 0; i < name.size(); i++) {
                printScan.println(name.get(i));
                printScan.println(password.get(i));
                printScan.println(nickname.get(i));
                printScan.println(age.get(i));
                System.out.println(nickname.get(i));
            }
            printScan.close();
            return true;
        } catch(IOException exception) {
            System.out.println("Problem with access / save to file \"users.dpt\"");
        }
        return false;
    }
    public boolean restoreUsers() {
        if(!Files.exists(Paths.get("users.dpt"))) {
            System.out.println("File \"users.dpt\" not found, but starting program...");
            return true;
        }
        try {
            File file = new File("users.dpt");
            Scanner scanFile = new Scanner(file);
            while(scanFile.hasNextLine()){
                this.name.add(scanFile.nextLine());
                this.password.add(scanFile.nextLine());
                this.nickname.add(scanFile.nextLine());
                this.age.add(scanFile.nextInt());
                scanFile.nextLine(); // for flush stream
            }
        } catch(NoSuchElementException exception) {
            System.out.println("file \"users.dpt\" is broken. Delete file completely and create a new one.");
            return false;
        } catch(Exception exception) {
            System.out.println("Undefined exception. Exiting...");
            return false;
        }
        return true;
    }
    public void inviteFriend() {
        System.out.print("Enter friend name: ");
        String name = scan.next();
        System.out.print("Enter friend age: ");
        int age = scan.nextInt();
        System.out.print("Enter friend email: ");
        scan.nextLine();
        String mail = scan.nextLine();
        System.out.println("Invite will be send");
        return;
    }
}
