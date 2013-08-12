package com.encima.api.endpoints;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.encima.utils.DroolsTools;
import com.encima.utils.FileTools;

public class DroolsAdderResource extends ServerResource {

	@Post
	public void addRule(Representation entity){
		Form form = new Form(entity);
		System.out.println(form.getNames());
		String path = form.getFirstValue("file");
		String contents = FileTools.readFileAsString(path);
		DroolsTools.addRule(contents);
	}
}
