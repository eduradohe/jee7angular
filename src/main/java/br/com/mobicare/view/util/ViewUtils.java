package br.com.mobicare.view.util;

import java.util.List;

import javax.json.JsonArray;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ViewUtils {

	public static <T> List<T> parseJsonToList( final JsonArray json ) {
		
		List<T> ts = null;
		
		final ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			ts = objectMapper.readValue(json.toString(), new TypeReference<List<T>>(){});
		} catch ( final Throwable t ) {
			throw new RuntimeException("Error parsing JSON to list.", t);
		}
		
		return ts;
	}
}
