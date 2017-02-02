/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;

/**
 *
 * @author hjorthen
 */
public interface Galgeleg extends java.rmi.Remote {
    boolean guessWord(String word, String authToken) throws java.rmi.RemoteException;
    int login(String username, String password) throws java.rmi.RemoteException;
}
