/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import brugerautorisation.transport.soap.Brugeradmin;
import Shared.Galgeleg;
import brugerautorisation.data.Bruger;
import galgeleg.Galgelogik;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Random;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;


/**
 *
 * @author hjorthen
 */
@WebService(endpointInterface = "Shared.Galgeleg")
public class GalgeServlet implements Galgeleg {
    private HashMap<Integer, Galgelogik> logins = new HashMap<Integer, Galgelogik>();
    private Brugeradmin connection;
    Random random = new Random(System.currentTimeMillis());

    public GalgeServlet() throws Exception {
        URL url = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
        QName qname = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
	Service service = Service.create(url, qname);
	 this.connection = service.getPort(Brugeradmin.class);
        
    }
    
    @Override
    public GuessResult guessWord(String word, int authToken) throws RemoteException {
        Galgelogik currentUser;
        if(authToken != 0 && (currentUser = logins.get(authToken)) != null)
        {
            currentUser.gætBogstav(word);
            GuessResult result = new GuessResult();
            result.correctGuess = currentUser.erSidsteBogstavKorrekt();
            result.wordProgress = currentUser.getSynligtOrd();
            result.wrongGuesses = currentUser.getAntalForkerteBogstaver();
            if(currentUser.erSpilletTabt())
            {
                result.status = GuessResult.Status.Lost;
            }
            else if(currentUser.erSpilletVundet())
            {
                result.status = GuessResult.Status.Won;
            }
            else
            {
                result.status = GuessResult.Status.Running;
            }
            return result;
        }
        throw new RemoteException("Invalid token");
    }

    
    private int getToken(String name)
    {
       
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            salt.append((char)random.nextInt());
        }
        return (name + salt).hashCode();
    }

    @Override
    public int login(String username, String password) throws RemoteException {
          System.out.println(username + " has logged in..");
          Bruger br;
                br = connection.hentBruger(username, password);
          
          
          if(br != null)
          {
              int token = getToken(br.brugernavn);
              System.out.println("Generated token for user: " + token);
              logins.put(token, new Galgelogik());
              return token;
          }
          System.out.println("No such user");
          return 0;
    }
    
}
