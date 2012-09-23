package com.piotrnowicki.exam.simulator.control;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Resources {

	@Produces
	@PersistenceContext
	private EntityManager em;

	@Produces
	public Logger produceLogger(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass()
				.getName());
	}

	@Produces
	@RequestScoped
	public FacesContext produceFacesContext() {
		return FacesContext.getCurrentInstance();
	}
}
