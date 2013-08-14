package com.encima.api.endpoints;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;
import org.restlet.data.MediaType;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.encima.Message;
import com.encima.dwc.DarwinCore;
import com.encima.dwc.Identification;
import com.encima.dwc.ImageSet;
import com.encima.dwc.Occurrence;
import com.encima.utils.APITools;
import com.encima.utils.Drools;
import com.encima.utils.FileTools;

public class DroolsFirerResource extends ServerResource {

	@Post("json")
    public JacksonRepresentation<String> fire() {
		APITools.addAccessToHeader(getResponse());
		Drools dr = FileTools.deserializeRuleBase();
		KnowledgeBase kb = dr.getKbase();
		StatefulKnowledgeSession ksession = kb.newStatefulKnowledgeSession();
		Message msg = new Message();
	    msg.setType("Test");
	    ksession.insert(msg);
	    ksession.insert(addDWC());
	    ksession.fireAllRules();
	    return new JacksonRepresentation<String>(MediaType.APPLICATION_JSON, "({\"responseData\":\"Drools Fired\"})");
	}
	
	public DarwinCore addDWC() {
		Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.YEAR, 2012);
	    cal.set(Calendar.MONTH, 3);
	    cal.set(Calendar.DAY_OF_MONTH, 30);
	    Date dateRep = cal.getTime();
		try {
			Occurrence occ = new Occurrence("1", "2012-03-27", "16:42:20", "3", "MovingImage", "3");
			Identification id = new Identification(1, 2, dateRep, 3);
			ImageSet is = new ImageSet(1, 1, "/home/encima/pictures/test.jpg");
			Vector<ImageSet> vis = new Vector<ImageSet>();
			vis.add(is);
			DarwinCore dwc = new DarwinCore(occ, id, vis);
			return dwc;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
