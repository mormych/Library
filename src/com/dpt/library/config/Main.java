package com.dpt.library.config;

import java.io.IOException;
import java.util.Scanner;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;
/*-----------------------*/
import com.dpt.library.system.BooksManager;
import com.dpt.library.system.LibraryManager;
import com.dpt.library.system.Users;
/*
    Library system configurator. The first takes off, after starting the program.
    This program do everything with saves/restores.
    Configures applications and restores control to <LibraryManager>
 */
public class Main {

    private Scanner scan = new Scanner(System.in);
    private static Users users = new Users();
    private File file = new File("bookList.txt");

    public static void main(String[] args) {
        System.out.println("Welcome in library system");
        Main main = new Main();
        boolean complete = main.checkConf(); // check configuration status
        if(complete) {
            System.out.println("Conf is ok");
        } else {
            System.out.println("Error with conf. Exiting...");
            return;
        }
        if(main.checkBookList()) {
            System.out.println("file \"bookList.txt\" exist");
        } else {
            System.out.println("Create file \"bookList.txt\" and put something books to it");
            return;
        }
        boolean usersBackup = users.restoreUsers();
        if(usersBackup) {
            if(Files.exists(Paths.get("users.dpt"))) {
                System.out.println("Restored users");
            }
        } else {
            return;
        }
        LibraryManager libraryManager = new LibraryManager();
        int status = libraryManager.mainProgram(); // Start library after conf is ok

        switch (status) {
            case 0:
                System.out.println("[completed correctly]");
                System.out.println("Conf is saved. The rest of files is -> " + (main.saveConf()));
                break;
            case 1:
                System.out.println("[completed incorrectly. Bad sign up]");
                break;
            case 2:
                System.out.println("[completed incorrectly. Bad argument]");
                break;
            case 3:
                System.out.println("[completed incorrectly. Log in Error]");
                break;
        }
    }
    private boolean checkConf() {
        try {
            if(!Files.exists(Paths.get("config.dpt"))) {
                Files.createFile(Paths.get("config.dpt"));
            } else {
                String config = "ver 1.0 2020";
                byte[] bytes = config.getBytes(); // convert text to bytes
                Files.write(Paths.get("config.dpt"),bytes);
                System.out.println(Files.readAllLines(Paths.get("config.dpt")));
            }
            return true;
        } catch(IOException exception ) {
            System.out.println("Critical error. " + exception);
        }
        return false;
    }
    private boolean checkBookList() {
        if(Files.exists(Paths.get("bookList.txt")) && file.length() != 0) {
            return true;
        }
        return false;
    }
    private String saveConf() {
        File file = new File("users.dpt");
        if(users.saveUsers()) {
            if(file.length() == 0) {
                return "[Saved failed. Empty file \"users.dpt\"]";
            }
            return "[saved]";
        }
        return "[unsaved]";
    }
}
