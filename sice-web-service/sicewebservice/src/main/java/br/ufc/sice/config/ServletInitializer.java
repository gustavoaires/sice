package br.ufc.sice.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.util.ClassUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;


public class ServletInitializer extends AbstractDispatcherServletInitializer {

	@Override
	protected WebApplicationContext createServletApplicationContext() {
		System.out.println("ServletInitializer --- createServletApplicationContext");
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.scan(ClassUtils.getPackageName(getClass()));
		return context;
	}

	@Override
	protected String[] getServletMappings() {
		System.out.println("ServletInitializer --- getServletMappings");
		return new String[] { "/" };
	}

	@Override
	protected WebApplicationContext createRootApplicationContext() {
		System.out.println("ServletInitializer --- createRootApplicationContext");
		return null;
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		System.out.println("ServletInitializer --- onStartup");
		super.onStartup(servletContext);
		DelegatingFilterProxy filter = new DelegatingFilterProxy("springSecurityFilterChain");
		filter.setContextAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher");
		servletContext.addFilter("springSecurityFilterChain", filter).addMappingForUrlPatterns(null, false, "/*");
	}
	
}