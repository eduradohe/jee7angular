package br.com.mobicare.model.dao;

import java.util.Set;

import org.reflections.Reflections;

import br.com.mobicare.model.entities.Persistable;

public class DaoFactory<P extends Persistable> {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Dao<P> getDao( Class<P> persistableClass ) {
		
		final Reflections reflections = new Reflections("br.com.mobicare.model.dao");
		final Set<Class<? extends Dao>> classes = reflections.getSubTypesOf(Dao.class);
		
		Dao<P> dao = null;
		
		for ( final Class<? extends Dao> clazz: classes ) {
			
			if ( persistableClass.getName().equals( clazz.getMethods()[0].getReturnType().getName() ) ) {
				
				try {
					dao = clazz.newInstance();
				} catch (Throwable t) {
					throw new RuntimeException("An error ocurred while trying to get the required DAO.", t);
				}
				
				break;
			}
		}
		
		return dao;
	}
}