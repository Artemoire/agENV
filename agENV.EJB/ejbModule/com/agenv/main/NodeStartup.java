package com.agenv.main;

import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.CDI;

import com.agenv.model.Agent;
import com.agenv.model.AgentType;

@Startup
@Singleton
public class NodeStartup {

	List<AgentType> localAgents;
	
	@PostConstruct
	void init() {
		Iterator<Bean<?>> beanterator = CDI.current().getBeanManager().getBeans(Agent.class).iterator();
		
		while(beanterator.hasNext());
//			beanterator.next().getBeanClass().getName()
	}

}
