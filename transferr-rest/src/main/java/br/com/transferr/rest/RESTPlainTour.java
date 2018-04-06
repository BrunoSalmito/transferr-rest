package br.com.transferr.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.transferr.core.exceptions.ValidationException;
import br.com.transferr.core.model.PlainTour;
import br.com.transferr.core.responses.ResponsePlainTour;
import br.com.transferr.core.role.RolePlainTour;
import br.com.transferr.rest.util.RestUtil;




@Component
@Path("/plaintour")
public class RESTPlainTour extends ASuperRestClass<PlainTour>{
	@Autowired
	private RolePlainTour rolePlainTour;
	
	public RESTPlainTour() {
		
	}
	/**
	 * Verifica se tal serviço REST está on-line
	 * @return retorna OK se o servico ta no ar
	 */
	@GET
	@Path("on")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetOn(){
		System.out.println("Server is OK!!!");
		return Response.ok("OK").build();
	}
	
	/**
	 * <p>Obter um medico pelo seu ID</p>
	 * <p>ex url: http://hostserver/rest/medico/1</p>
	 * @param id do tipo long
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGet(@PathParam("id") long id){
		PlainTour entidade=null;
		try {
			entidade= rolePlainTour.find(id);
		} catch (ValidationException e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}

		return Response.ok().entity(entidade).build();
	}
	/**
	 * <p>Salva ou atualiza uma entidade de medico. Se a entidade medico vier com id ele atualiza caso contrario o 
	 * mesmo é inserido como um novo registro</p>
	 * <p>ex url: http://hostserver/rest/medico/save/{JSON do medico}</p>
	 * @param exemplo JSON da entidade medico
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(PlainTour exemplo){
		if(exemplo.getId()>0){
			try {
				rolePlainTour.update(exemplo);
			} catch (ValidationException e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}else{
			try {
				rolePlainTour.insert(exemplo);
			} catch (ValidationException e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}
		return Response.ok().entity(exemplo).build();
	}
	
	
	/**
	 * <p>Deleta um medico pelo seu ID</p>
	 * <p>ex url: http://hostserver/rest/medico/delete/1</p>
	 * <p><strong>ATENÇÃO: Não poderá ser desfeita</strong></p>
	 * @param id do medico a ser deletado
	 */
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") long id){
		try {
			rolePlainTour.delete(id);
		} catch (ValidationException e) {
			return RestUtil.getResponseValidationErro(e);
		}catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return RestUtil.getResponseOK();
	}
	
	@GET
	@Path("bydriver/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetByDriver(@PathParam("id") long id){
		List<PlainTour> plains=null;
		try {
			plains= rolePlainTour.getDriverOpenPlainTour(id);
		} catch (ValidationException e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			e.printStackTrace();
			return RestUtil.getResponseErroInesperado(e);
		}

		return Response.ok().entity(plains).build();
	}

	@POST
	@Path("save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doSave(ResponsePlainTour responsePlainTour){		
		PlainTour plainTour;
		try {
			plainTour = rolePlainTour.insert(responsePlainTour);
		} catch (ValidationException e) {
			return RestUtil.getResponseValidationErro(e);
		}catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().entity(plainTour).build();
	}
	
	@PUT
	@Path("increase/seats/{idPlainTour}/{seats}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doPutIncreaseSeats(@PathParam("idPlainTour") long idPlainTour,@PathParam("seats") int seats  ){		
		PlainTour plainTour;
		try {
			plainTour = rolePlainTour.increseSeats(idPlainTour, seats);
		} catch (ValidationException e) {
			return RestUtil.getResponseValidationErro(e);
		}catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().entity(plainTour).build();
	}
	
	@GET
	@Path("bylocation/{idLocation}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetByLocation(@PathParam("idLocation") long idLocation){
		List<PlainTour> plains=null;
		try {
			plains= rolePlainTour.getByLocation(idLocation);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().entity(plains).build();
	}

}
