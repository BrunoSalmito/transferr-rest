package br.com.transferr.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.transferr.core.exceptions.ValidationException;
import br.com.transferr.core.model.StatisticContact;
import br.com.transferr.core.role.RoleStatisticContact;
import br.com.transferr.rest.util.RestUtil;




@Component
@Path("statistic")
public class RESTStatisticContact extends ASuperRestClass<StatisticContact>{
	@Autowired
	private RoleStatisticContact roleStatisticContact;
	
	public RESTStatisticContact() {
		
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
		StatisticContact entidade=null;
		try {
			entidade= roleStatisticContact.find(id);
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
	public Response save(StatisticContact exemplo){
		if(exemplo.getId()>0){
			try {
				roleStatisticContact.update(exemplo);
			} catch (ValidationException e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}else{
			try {
				roleStatisticContact.insert(exemplo);
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
			roleStatisticContact.delete(id);
		} catch (ValidationException e) {
			return RestUtil.getResponseValidationErro(e);
		}catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().build();
	}
	
	@POST
	@Path("send/contact")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response contactStatistic(final StatisticContact statisticContact){
		try {
			roleStatisticContact.registerIntetionOfContact(statisticContact);
		}catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().build();
	}
	


}
