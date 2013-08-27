package com.encima.api.endpoints;

import java.util.Collection;

import org.drools.definition.KnowledgePackage;
import org.restlet.data.MediaType;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.encima.utils.APITools;
import com.encima.utils.Drools;
import com.encima.utils.FileTools;

public class DroolsCloserResource extends ServerResource {

	@Get("json")
	public JacksonRepresentation<String> close() {
		APITools.addAccessToHeader(getResponse());
		Drools dr = FileTools.deserializeRuleBase();
		Collection<KnowledgePackage> knowledge = dr.getKbase().getKnowledgePackages();
		for(KnowledgePackage know : knowledge) {
			dr.getKbase().removeKnowledgePackage(know.getName());
		}
		dr.setStatus("UNLOADED");
		FileTools.serializeRuleBase(dr);
		return new JacksonRepresentation<String>(MediaType.APPLICATION_JSON, "({\"responseData\":\"Drools KB Stored. All rules removed\"})");
	}
}
