package com.dpt.library.system;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
/*
    This class do everything with books in library.
 */
public class BooksManager {
    private Scanner scan = new Scanner(System.in);
    private static List <String> bookAvaiable = new ArrayList<>();
    private static List <String> wishList = new ArrayList<>();

    public void buyBook() {
        try {
            bookAvaiable.clear();
            File file = new File("bookList.txt");
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()) {
                bookAvaiable.add(scan.nextLine());
            }
            scan.close();
        } catch(Exception exception) {
            System.out.println("Problem with access/Reading file \"bookList.txt\"");
            System.out.println(exception);
        }
        int iterator = 1; // for books
        for(String avaiableBook : bookAvaiable) {
            System.out.println(iterator + ". "+ avaiableBook);
            iterator++;
        }
        if(bookAvaiable.size() == 0) {
            System.out.println("No books found in database...");
            return;
        }
        System.out.print("Select correct book to buy: ");
        int bookNumber = scan.nextInt();
        String bookChoose = "";
        if(bookNumber > bookAvaiable.size()) {
            System.out.println("Error. Book not found.");
            return;
        }
        iterator = 1;
        for(String avaiableBook : bookAvaiable) {
            if(bookNumber == iterator) {
                bookChoose = avaiableBook;
                break;
            }
            iterator++;
        }
        try {
            Files.delete(Paths.get("bookList.txt"));
            Files.createFile(Paths.get("bookList.txt"));
            String buffer;
            PrintWriter scanToFile = new PrintWriter("bookList.txt");
            for (int i = 0; i < bookAvaiable.size(); i++) {
                if (bookAvaiable.get(i).contains(bookChoose)) {
                    continue;
                }
                buffer = bookAvaiable.get(i);
                scanToFile.println(buffer);
            }
            scanToFile.close();
        } catch(Exception exception) {
            System.out.println("Unhandled exception error");
            return;
        }
        System.out.println("Bought! " + bookChoose);
    }
    public void addBook() {
        System.out.print("Your wish list: ");
        if(wishList.size() == 0) {
            System.out.println("empty");
        } else {
            for(int i = 0; i < wishList.size(); i++) {
                if(i+1 == wishList.size()) {
                    System.out.print(wishList.get(i) + ".");
                    break;
                }
                System.out.print(wishList.get(i) + ", ");
            }
            System.out.println();
        }
        try {
            bookAvaiable.clear();
            File file = new File("bookList.txt");
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()) {
                bookAvaiable.add(scan.nextLine());
            }
            scan.close();
        } catch(Exception exception) {
            System.out.println("Problem with access/Reading file \"bookList.txt\"");
            System.out.println(exception);
        }
        int iterator = 1; // for books
        for(String avaiableBook : bookAvaiable) {
            System.out.println(iterator + ". "+ avaiableBook);
            iterator++;
        }
        if(bookAvaiable.size() == 0) {
            System.out.println("No books found in database...");
            return;
        }
        System.out.print("Select book to add to your wish list: ");
        int bookNumber = scan.nextInt();
        String bookChoose = "";
        if(bookNumber > bookAvaiable.size()) {
            System.out.println("Error. Book not found.");
            return;
        }
        iterator = 1;
        for(String avaiableBook : bookAvaiable) {
            if(bookNumber == iterator) {
                bookChoose = avaiableBook;
                if(!checkCopy(bookChoose)) {
                    wishList.add(bookChoose);
                    System.out.println("added");
                }
                break;
            }
            iterator++;
        }
        return;
    }
    private boolean checkCopy(String bookChoose) { // check existing copy book
        for(int i = 0; i < wishList.size(); i++) {
            if(bookChoose.equals(wishList.get(i))) {
                System.out.println("You have this book in wish list");
                return true;
            }
        }
        return false;
    }
}