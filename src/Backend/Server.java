/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import Shared.Galgeleg;
import java.rmi.Naming;
import javax.xml.ws.Endpoint;

/**
 *
 * @author hjorthen
 */
public class Server {
    public static void main(String[] args) throws Exception
    {
        Galgeleg servlet = new GalgeServlet();
        Endpoint.publish("http://[::]:9901/galgeleg", servlet);
        System.out.println("Servlet started");
    }
}
