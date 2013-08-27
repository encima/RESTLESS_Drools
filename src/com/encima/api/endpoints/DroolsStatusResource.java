package com.encima.api.endpoints;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.encima.utils.APITools;
import com.encima.utils.Drools;
import com.encima.utils.DroolsTools;
import com.encima.utils.FileTools;

public class DroolsStatusResource extends ServerResource {
	
	@Post("json")
    public JacksonRepresentation<String> start() {
		Drools dr = FileTools.deserializeRuleBase();
       return new JacksonRepresentation<String>(MediaType.APPLICATION_JSON, String.format("({\"Drools Status\":\"%s\"})", dr.getStatus()));
    }
}
