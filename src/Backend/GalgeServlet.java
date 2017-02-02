/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import Shared.Galgeleg;
import galgeleg.Galgelogik;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Random;


/**
 *
 * @author hjorthen
 */
public class GalgeServlet extends UnicastRemoteObject implements Galgeleg {
    private HashMap<Integer, Galgelogik> logins = new HashMap<Integer, Galgelogik>();
    private Brugeradmin connection;
    Random random = new Random(System.currentTimeMillis());

    public GalgeServlet() throws Exception {
        this.connection = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
    }
    
    @Override
    public boolean guessWord(String word, String authToken) throws RemoteException {
        Galgelogik currentUser;
        if(authToken != null && (currentUser = logins.get(authToken)) != null)
        {
            currentUser.gætBogstav(word);
            return currentUser.erSidsteBogstavKorrekt();
        }
        throw new RemoteException("Invalid token");
    }

    @Override
    public int login(String username, String password) throws RemoteException {
          Bruger br = connection.hentBruger(username, password);
          if(br != null)
          {
              int token = getToken(br.brugernavn);
              logins.put(token, new Galgelogik());
              return token;
          }
          throw new RemoteException("Invalid login");
    }
    private int getToken(String name)
    {
       
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            salt.append((char)random.nextInt());
        }
        return (name + salt).hashCode();
    }
    
}
