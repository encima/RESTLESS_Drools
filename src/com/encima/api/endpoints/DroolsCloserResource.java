package com.encima.api.endpoints;

import java.util.Collection;

import org.drools.definition.KnowledgePackage;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.encima.utils.Drools;
import com.encima.utils.FileTools;

public class DroolsCloserResource extends ServerResource {

	@Get
	public void close() {
		Drools dr = FileTools.deserializeRuleBase();
		Collection<KnowledgePackage> knowledge = dr.getKbase().getKnowledgePackages();
		for(KnowledgePackage know : knowledge) {
			dr.getKbase().removeKnowledgePackage(know.getName());
		}
		FileTools.serializeRuleBase(dr);
	}
}
