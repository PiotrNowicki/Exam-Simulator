package com.piotrnowicki.exam.simulator.control;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Various resources that can be used with CDI's <code>@Inject</code>.
 * 
 * @author Piotr Nowicki
 * 
 */
public class Resources {

    /**
     * Produces default {@link EntityManager} which acts like a synonym of <code>@PersistenceContext</code>.
     */
    @Produces
    @PersistenceContext
    private EntityManager em;

    /**
     * <p>
     * Produces JUL {@link Logger}.
     * </p>
     * <p>
     * It eases the use of the Logger because you don't have to define the logger name - it's automatically inferred from the
     * injection point.
     * </p>
     * 
     * @param injectionPoint
     * 
     * @return Injection-point specific {@link Logger}
     */
    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

    /**
     * Produces {@link FacesContext} mainly for the JSF managed beans use.
     * 
     * @return Request-scoped {@link FacesContext}
     */
    @Produces
    @RequestScoped
    public FacesContext produceFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}
