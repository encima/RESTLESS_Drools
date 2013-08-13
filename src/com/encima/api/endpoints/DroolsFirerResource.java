package com.encima.api.endpoints;

import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.encima.Message;
import com.encima.utils.Drools;
import com.encima.utils.FileTools;

public class DroolsFirerResource extends ServerResource {

	@Get("text")
    public void fire() {
		Drools dr = FileTools.deserializeRuleBase();
		KnowledgeBase kb = dr.getKbase();
		StatefulKnowledgeSession ksession = kb.newStatefulKnowledgeSession();
		Message msg = new Message();
	    msg.setType("Test");
	    ksession.insert(msg);
	    ksession.fireAllRules();
	}
	
	@Post
	public void postFire() {
//		REceive and add object to rule base here.
	}
}
