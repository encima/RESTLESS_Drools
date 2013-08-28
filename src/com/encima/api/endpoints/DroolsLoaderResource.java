package com.encima.api.endpoints;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.encima.dwc.DarwinCore;
import com.encima.dwc.Identification;
import com.encima.dwc.ImageSet;
import com.encima.dwc.Occurrence;
import com.encima.utils.APITools;
import com.encima.utils.DBTools;
import com.encima.utils.Drools;
import com.encima.utils.FileTools;

public class DroolsLoaderResource extends ServerResource{
	
	
	@Post
	public JacksonRepresentation<String> loadDWC(Representation entity) {
		APITools.addAccessToHeader(getResponse());
		Form form = new Form(entity);
		String eventID = form.getFirstValue("id");
		System.out.println(eventID);
		if(eventID != null) {
			loadObservations(Integer.parseInt(eventID));
		}else{
			loadObservations(-1);
		}
		return new JacksonRepresentation<String>(MediaType.APPLICATION_JSON, "({\"responseData\":\"Observations loaded\"})");
	}
	
	public void loadObservations(int id) {
		Drools dr = FileTools.deserializeRuleBase();
		KnowledgeBase kb = dr.getKbase();
		StatefulKnowledgeSession ksession = kb.newStatefulKnowledgeSession();
		Connection conn = DBTools.dbConnect("root", "root", "gsn");
		String query = "SELECT * FROM occurrence";
		if(id > 0) query = "SELECT * FROM occurrence WHERE EVENTID=" + id + ";";
		ResultSet dwcs = DBTools.execute(conn, "SELECT * FROM occurrence");
		try {
			while(dwcs.next()) {
				int eventID = dwcs.getInt("EVENTID");
				System.out.println(eventID);
				Date eventDate = DBTools.sqlDateToJavaDate(dwcs.getString("EVENTDATE"));
				Date eventTime = DBTools.sqlDateToJavaDate(dwcs.getString("EVENTTIME"));
				int locationID = dwcs.getInt("LOCATIONID");
				String basis = dwcs.getString("BASISOFRECORD");
				int recBy = dwcs.getInt("RECORDEDBY");
				Occurrence occ = new Occurrence(eventID, eventDate, eventTime, locationID, basis, recBy);
				Identification ident = getIdentification(eventID);
				Vector<ImageSet> is = getImageSets(eventID);
				DarwinCore dwc = new DarwinCore(occ, ident, is);
				System.out.println(dwc);
				System.out.println("Inserting darwin core");
				ksession.insert(dwc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		ksession.fireAllRules();
	}
	
	public Identification getIdentification(int eventID){
		Connection conn = DBTools.dbConnect("root", "root", "gsn");
		ResultSet ids = DBTools.execute(conn, "SELECT * FROM identification WHERE id=" + eventID + ";");
		Identification identification = null;
		try{
			while(ids.next()) {
				int id = ids.getInt("id");
				Date dateID = ids.getDate("dateIdentified");
				int specID = ids.getInt("speciesID");
				int idBy = ids.getInt("identifiedBy");
				System.out.println("Loading Identification");
				identification = new Identification(id, idBy, dateID, specID);
			}
		}catch(SQLException e) {
			return null;
		}
		return identification;
	}
	
	public Vector<ImageSet> getImageSets(int eventID) {
		System.out.println("Loading Image Sets");
		Connection conn = DBTools.dbConnect("root", "root", "gsn");
		ResultSet sets = DBTools.execute(conn, "SELECT * FROM imageset WHERE eventId=" + eventID + ";");
		Vector<ImageSet> is = new Vector<ImageSet>();
		try{
			while(sets.next()) {
				int id = sets.getInt("eventId");
				String identifier = sets.getString("identifier");
				is.add(new ImageSet(id, identifier));
				System.out.println("Adding image set");
			}
		}catch(SQLException e) {
			return null;
		}
		return is;
		
	}
}
