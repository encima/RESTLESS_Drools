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

public class DroolsRunnerResource extends ServerResource{
    
	@Post("json")
    public JacksonRepresentation<String> start() {
		APITools.addAccessToHeader(getResponse());
	   Drools dr = DroolsTools.initDrools();
		   
       System.out.println("Knowledge Base Loaded");		   
       CharSequence ret = "({\"responseData\":\"hello, world (from the cloud!)\"})";
       JacksonRepresentation<String> r = new JacksonRepresentation<String>(ret.toString());
       JSONObject json = new JSONObject();
       try {
		json.put("Status", "Drools Started");
//		return json.toString();
       } catch (JSONException e) {
		e.printStackTrace();
       }
       return new JacksonRepresentation<String>(MediaType.APPLICATION_JSON, "({\"responseData\":\"Drools Started\"})");
    }
}
