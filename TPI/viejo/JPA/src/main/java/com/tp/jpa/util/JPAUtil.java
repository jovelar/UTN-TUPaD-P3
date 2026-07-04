
package com.tp.jpa.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    //Unico para que sea visible a todos
    private static final EntityManagerFactory emf;

    static {
        try {
            // IMPORTANTE: El nombre "miUnidadPersistencia" debe ser IDENTICO
            // al que figura en tu archivo persistence.xml
            emf = Persistence.createEntityManagerFactory("miUnidad");
        } catch (Throwable ex) {
            System.err.println("Error al inicializar EntityManagerFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}