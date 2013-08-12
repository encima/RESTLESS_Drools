package com.encima.api.endpoints;

import java.util.Collection;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.definition.KnowledgePackage;
import org.drools.runtime.StatefulKnowledgeSession;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.encima.utils.DBTools;
import com.encima.utils.Drools;
import com.encima.utils.DroolsTools;

public class DroolsRunnerResource extends ServerResource{
    
	@Get("text")
    public String start() {
	   Drools dr = DroolsTools.initDrools();
//	   DBTools.loadDBRules("drools", "root", "root", 2, dr.getKbase());
		   
       System.out.println("Rules loaded from DB");		   

       String name = getQueryValue("name");
       return "Drools started";
    }
}
