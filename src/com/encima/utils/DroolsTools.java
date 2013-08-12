package com.encima.utils;

import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.cdi.KBase;
import org.drools.definition.KnowledgePackage;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class DroolsTools {

	public static Drools initDrools() {
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		Drools dr = new Drools(kbase);
        return dr;
	}
	
	public static void addRule(String rule) {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		Resource myResource = ResourceFactory.newReaderResource((Reader) new StringReader(rule));
        kbuilder.add(myResource, ResourceType.DRL);
		if ( kbuilder.hasErrors() ) {
		   System.err.println( kbuilder.getErrors().toString() );
		}
		Drools dr = FileTools.deserializeRuleBase();
		dr.getKbase().addKnowledgePackages(kbuilder.getKnowledgePackages());
//		dr.setKsession(dr.getKbase().newStatefulKnowledgeSession());
		FileTools.serializeRuleBase(dr);
	}
	
}
