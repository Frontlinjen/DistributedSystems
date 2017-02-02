/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frontend;

import Shared.Galgeleg;
import Shared.Galgeleg.GuessResult;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 *
 * @author hjorthen
 */
public class Client {

    public static void main(String[] args) {
        Galgeleg api;
        try {
            api = (Galgeleg) Naming.lookup("rmi://localhost/galgeleg");
            Scanner sc = new Scanner(System.in);

            int token = 0;
            System.out.println("Login");
            do {
                System.out.print("Username: ");
                String usr = sc.nextLine();
                System.out.print("\nPassword: ");
                String pwd = sc.nextLine();
                System.out.println("Attempting to login..");
                token = api.login(usr, pwd);
                if (token == 0) {
                    System.out.println("Login failed. Try again.");
                }
            } while (token == 0);
            System.out.println("Login success!");
            GuessResult status;
            String currentGuess;
            StringBuilder guessedLetters = new StringBuilder();
            do {
                System.out.print("Guess a letter: ");
                currentGuess = sc.next();
                status = api.guessWord(currentGuess, token);
                System.out.println(ASCII.lifes[status.wrongGuesses]);
                System.out.println(status.wordProgress);
                guessedLetters.append(currentGuess);
                System.out.println("Gussed letters: " + guessedLetters);
            } while (status.status == GuessResult.Status.Running);
            System.out.println("\n\n");
            switch(status.status)
            {
                case Lost:
                {
                    System.out.println("Sorry, you lost!");
                    break;
                }
                case Won:
                {
                    System.out.println("Congratz, you won!");
                }
                
            }
            System.out.println("After " + guessedLetters.length() + " guesses.");
        } catch (NotBoundException ex) {
            System.out.println(ex.getMessage());
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
