package com.dpt.library.system;

import java.util.InputMismatchException;
import java.util.Scanner;
/*------------------*/
import com.dpt.library.system.Users;
import com.dpt.library.system.BooksManager;
/*
    Library main program for manage all application.
 */
public class LibraryManager {

    private final Scanner scan = new Scanner(System.in);
    public Users users = new Users();
    public BooksManager booksManager = new BooksManager();
    public int mainProgram() {
        byte decision = 0;
        int status = 0;
        System.out.println("Welcome in library");
        while(decision != 1 && decision != 2) {
            System.out.print("1.Sign up 2.Log in 3.Check users 4.Exit: ");
            try {
                decision = scan.nextByte();
                if(decision == 1) {
                    status = signUp();
                } else if(decision == 2){
                    System.out.print("Input user name: ");
                    String tempName = scan.next();
                    System.out.print("Input user password: ");
                    String tempPassword = scan.next();
                    try { // try to fix Exception
                        status = users.logIn(tempName, tempPassword);
                    } catch(NullPointerException exception) {
                        System.out.println("[Error with trying to log in]");
                        return 3;
                    }
                } else if(decision == 3){
                    users.checkUsers();
                } else if(decision == 4) {
                    return exit();
                }
            } catch (InputMismatchException exception) {
                System.out.println("Error with inputted argument... Exiting");
                return 2;
            }
            if(status == 3) {
                System.out.println("Account not found in database... returning");
                mainProgram();
            }
            if(status != 0) {
                return 1;
            }
        } //end while loop
        System.out.println("Welcome in library");
        while(decision != 5) {
            System.out.println("Select correct option");
            System.out.println("1.Buy a book 2.Add a book to your wish list 3.Invite a friend 4.Logout / Back to menu 5.Exit");
            System.out.print(": ");
            decision = scan.nextByte();

            switch(decision) {
                 case 1 -> booksManager.buyBook();
                 case 2 -> booksManager.addBook();
                 case 3 -> users.inviteFriend();
                 case 4 -> logout();
                 case 5 -> exit();
                 default -> System.out.println("Option not found");
            }
        }
        return 0;
    }
    private int signUp() {
        try {
            System.out.print("Your name: ");
            String name = scan.next();
            System.out.print("Your password: ");
            String password = scan.next();
            System.out.print("Your nickname: ");
            String nickname= scan.next();
            System.out.print("Your age: ");
            int age = scan.nextInt();
            users = new Users(name, password, nickname, age);
        } catch (Exception exception) {
            System.out.println(exception);
            return 1;
        }
        return 0;
    }
     private void logout() {
        mainProgram();
    }
    private int exit() {
        return 0;
    }
}
