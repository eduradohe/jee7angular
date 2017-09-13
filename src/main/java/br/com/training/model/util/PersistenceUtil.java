package br.com.training.model.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceUtil {

	private static EntityManagerFactory factory;
	
	public static EntityManager getEntityManager() {
		
		if ( factory == null || !factory.isOpen() ) {
			createEntityManagerFactory();
		}
		
		return factory.createEntityManager();
	}
	
	public static synchronized EntityManagerFactory createEntityManagerFactory() {
		
		if ( factory == null || !factory.isOpen() ) {
			factory = Persistence.createEntityManagerFactory("mobicare");
		}
		
		return factory;
	}
	
	public static void shutdown() {
		
		if ( factory == null || !factory.isOpen() ) {
			return;
		}
		
		factory.close();
	}
}