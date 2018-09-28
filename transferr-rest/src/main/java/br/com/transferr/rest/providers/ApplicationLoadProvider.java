package br.com.transferr.rest.providers;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.com.transferr.core.exceptions.ValidationException;
import br.com.transferr.core.role.RoleParametros;


/**
 * Servlet implementation class WebRestApplicationContext
 */
@WebServlet(value="/application/load/provider", loadOnStartup=2)
public class ApplicationLoadProvider extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	private RoleParametros roleParametros;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("***   STARTING APPLICATION PAYLOAD   ***");
		System.out.println("---------------------------------------------------------------------------");

		ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		
		iniciarParametrosDoSistema(context);
		
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("******   PAYLOAD IS COMPLETED     ********");
		System.out.println("---------------------------------------------------------------------------");
		
	}
	
	
	private void iniciarParametrosDoSistema(ApplicationContext context) {
		System.out.print("Starting initial system's parameters payload.");
		roleParametros = context.getBean(RoleParametros.class);
		try {
			roleParametros.insertAndLoadMainParameters();
		} catch (ValidationException e) {
			e.printStackTrace();
			System.err.println("Fail while trying to insert initial parameters.");
		}
		System.out.println("................................................ DONE.");
	}

}
