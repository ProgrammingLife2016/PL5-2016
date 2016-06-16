package com.pl.tagc.main;

import com.pl.tagc.tagcwebapp.BackEndAdapter;
import com.pl.tagc.tagcwebapp.RestServer;
import controller.Controller;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Class Main.
 * Instructions:
 * <p>
 * http://localhost:9998/app/index.htm Can be used to access the static file
 * index.htm.
 * <p>
 * http://localhost:9998/api/<apicall> Can be used to make api calls to the
 * server. For a list of api calls please see RestApi.java.
 */
final class Main {

    /**
     * Instantiates a new main.
     */
    private Main() {
    }


    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {

        try {
            Controller controller = new Controller();
            BackEndAdapter.createInstance(controller);
            RestServer restServer = new RestServer();
            restServer.startServer();
            Thread.currentThread().join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}