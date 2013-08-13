package com.encima.api;

import java.io.IOException;
import java.util.logging.Level;
import org.apache.log4j.Logger;


import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;


import org.restlet.representation.Representation;
import org.restlet.representation.Variant;



import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;


import org.w3c.dom.Document;

import org.w3c.dom.Element;

public class ClientAccessPolicy extends ServerResource {
	
	private static final String LOGGER_NAME = "org.mortbay.log";
        private static Logger logger = Logger.getLogger(ClientAccessPolicy.class);


	@Get("xml")
	public Representation accessAllowed(Variant variant) {
		
		logger.info("client access");

		if (MediaType.TEXT_XML.equals(variant.getMediaType())) {

			try {

				DomRepresentation representation = new DomRepresentation(

				MediaType.TEXT_XML);

				// Generate a DOM document representing the list of

				// items.

				Document d = representation.getDocument();

				Element accesspolicy = d.createElement("cross-domain-policy");

				d.appendChild(accesspolicy);

				Element crossdomainaccess = d
						.createElement("allow-access-from");
				crossdomainaccess.setAttribute("domain", "127.0.0.1");
				crossdomainaccess.setAttribute("secure", "false");
		

				accesspolicy.appendChild(crossdomainaccess);

				d.normalizeDocument();

				// Returns the XML representation of this document.

				return representation;

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

		return null;

	}

}