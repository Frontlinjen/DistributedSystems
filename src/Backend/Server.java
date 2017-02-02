/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import Shared.Galgeleg;
import java.rmi.Naming;

/**
 *
 * @author hjorthen
 */
public class Server {
    public static void main(String[] args) throws Exception
    {
        java.rmi.registry.LocateRegistry.createRegistry(1024);
        Galgeleg servlet = new GalgeServlet();
        Naming.rebind("rmi://localhost/galgeleg", servlet);
        System.out.println("Servlet started");
    }
}
