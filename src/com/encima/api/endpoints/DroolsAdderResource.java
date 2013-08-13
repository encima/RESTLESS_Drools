package com.encima.api.endpoints;

import java.util.concurrent.ConcurrentMap;

import org.restlet.Message;
import org.restlet.data.Form;
import org.restlet.engine.header.Header;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Options;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.encima.utils.DroolsTools;
import com.encima.utils.FileTools;

public class DroolsAdderResource extends ServerResource {

	 private static final String HEADERS_KEY = "org.restlet.http.headers";
	
	@Post
	public void addRule(Representation entity){
		Form form = new Form(entity);
		System.out.println(form.getNames());
		String path = form.getFirstValue("file");
		String contents = FileTools.readFileAsString("/home/encima/development/node/khas/" + path);
		DroolsTools.addRule(contents);
	}
	
	@Get
	public void add() {
		String file = getQueryValue("file");
		String contents = FileTools.readFileAsString("/home/encima/development/node/khas/" + file);
		DroolsTools.addRule(contents);
	}
	
}
