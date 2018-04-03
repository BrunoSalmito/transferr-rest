package br.com.transferr.rest.providers;

import java.text.SimpleDateFormat;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;


@Provider
@Produces(MediaType.APPLICATION_JSON)  
@Consumes(MediaType.APPLICATION_JSON) 
public class JacksonConfigProvider implements ContextResolver<ObjectMapper>  
{  
   private ObjectMapper objectMapper;  
  
  
   public JacksonConfigProvider() throws Exception  
   {  
	  System.out.println("----------------------------------------");
	  System.out.println("Starting jackson configuration provider.");
	  System.out.println("----------------------------------------");
	  objectMapper = new ObjectMapper();
      objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));  
      objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
	  
   }  
 
   public ObjectMapper getContext(Class<?> objectType)  
   {  
      return objectMapper;  
   }  
}  