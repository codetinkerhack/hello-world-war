package com.codetinkerhack;

import com.codetinkerhack.persistence.Storage;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import java.io.IOException;

public class ServletContextInitializer implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Storage storage = null;
        try {
            storage = new Storage();
            servletContextEvent.getServletContext().setAttribute("storage", storage);
        } catch (Exception e) {
            servletContextEvent.getServletContext().log("ERROR: " + e.getCause().toString());
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Storage storage = (Storage) servletContextEvent.getServletContext().getAttribute("storage");
        try {
            storage.close();
        } catch (IOException e) {
            servletContextEvent.getServletContext().log("ERROR: " + e.getCause().toString());
        }
    }
}
