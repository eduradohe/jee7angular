package br.com.mobicare.view.util;

import java.util.Map;
import java.util.Map.Entry;

import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mobicare.view.services.MobicareApiResponse;

public class ApiUtils {
	
	private static final String API_CONTEXT = "http://localhost:8080/java-pleno-eduardo-turella/";
	
	public static <T> T callService( final String path ) {
		
		return callService( path, null );
	}
	
	public static <T> T callService( final String path, final Map<String,Object[]> parameters  ) {
		
		final Client client = ClientBuilder.newClient();
		WebTarget target = client.target(API_CONTEXT + path);
		
		if ( parameters != null ) {
			for ( final Entry<String, Object[]> entry : parameters.entrySet() ) {
				target = target.queryParam(entry.getKey(), entry.getValue());
			}
		}
		
		final JsonObject response = target.request(MediaType.APPLICATION_JSON).get(JsonObject.class);
		
		return parseJsonToList(response);
	}
	
	private static <T> T parseJsonToList( final JsonObject json ) {
		
		MobicareApiResponse<T> response = null;
		
		final ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			response = objectMapper.readValue(json.toString(), new TypeReference<MobicareApiResponse<T>>(){});
		} catch ( final Throwable t ) {
			throw new RuntimeException("Error parsing JSON to list.", t);
		}
		
		return response.getContent();
	}
	
	public static <T> MobicareApiResponse<T> wrappResponse( T content ) {
		
		final MobicareApiResponse<T> response = new MobicareApiResponse<T>( content );
		return response;
	}
}
