package com.encima.api.endpoints;

import org.drools.KnowledgeBase;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.encima.utils.Drools;
import com.encima.utils.FileTools;

public class DroolsFirerResource extends ServerResource {

	@Get("text")
    public void fire() {
		Drools dr = FileTools.deserializeRuleBase();
		KnowledgeBase kb = dr.getKbase();
		kb.newStatefulKnowledgeSession();
	}
}
