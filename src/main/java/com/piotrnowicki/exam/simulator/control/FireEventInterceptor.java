package com.piotrnowicki.exam.simulator.control;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;

import javax.interceptor.InvocationContext;

import com.piotrnowicki.exam.simulator.entity.Question;

/**
 * Interceptor that is responsible for invoking {@link DataModifiedEvent}.
 * 
 * @author Piotr Nowicki
 *
 */
public class FireEventInterceptor {

    @Inject
    Event<DataModifiedEvent> events;
    
    /**
     * Fires a CDI event. It will work only in cases of a single parameter methods are taken under consideration.
     *  
     * @param ctx
     * @return
     * @throws Exception
     */
    @AroundInvoke
    public Object around(InvocationContext ctx) throws Exception {
        Object result = ctx.proceed();
        
        if (ctx.getParameters()[0] instanceof Question) {
            events.fire(new DataModifiedEvent((Question)ctx.getParameters()[0]));
        }

        return result;
    }
}
