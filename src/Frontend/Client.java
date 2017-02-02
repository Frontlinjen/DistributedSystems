/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frontend;

import Shared.Galgeleg;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hjorthen
 */
public class Client {
    public static void main(String[] args)
    {
        Galgeleg api;
        try {
            api = (Galgeleg)Naming.lookup("rmi://localhost/galgeleg");
            Scanner sc = new Scanner(System.in); 
            
            int token = 0;
            System.out.println("Login");
            do{
                System.out.print("Username: ");
                String usr = sc.nextLine();
                System.out.print("\nPassword: ");
                String pwd = sc.nextLine();
                token = api.login(usr, pwd);
                if(token == 0){
                    System.out.println("Login failed. Try again.");
                }
            } while(token != 0);
            
            
        } catch (NotBoundException ex) {
            System.out.println(ex.getMessage());
        } catch (MalformedURLException ex) {
            System.out.println(ex.getMessage());
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
