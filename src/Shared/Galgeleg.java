/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

import java.io.Serializable;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author hjorthen
 */
@WebService
public interface Galgeleg extends java.rmi.Remote {
    public class GuessResult implements Serializable{
        public static enum Status implements Serializable {
            Running, 
            Lost, 
            Won
        }
        public int wrongGuesses;
        public boolean correctGuess;
        public String wordProgress;
        public Status status;
    }
   @WebMethod  GuessResult guessWord(String word, int authToken) throws java.rmi.RemoteException;
   @WebMethod  int login(String username, String password) throws java.rmi.RemoteException;
}
