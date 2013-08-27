package com.encima.utils;

import java.io.Serializable;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.runtime.StatefulKnowledgeSession;

public class Drools implements Serializable {
	private StatefulKnowledgeSession ksession =  null;
	private KnowledgeBase kbase;
	private String STATUS;
	
	public Drools(KnowledgeBase kbase, String STATUS) {
		this.kbase = kbase;
		this.STATUS = STATUS;
//		this.ksession = kbase.newStatefulKnowledgeSession();
		FileTools.serializeRuleBase(this);
	}
	
	public String getStatus() {
		return STATUS;
	}

	public void setStatus(String sTATUS) {
		STATUS = sTATUS;
	}

	public StatefulKnowledgeSession getKsession() {
		return ksession;
	}
	void setKsession(StatefulKnowledgeSession ksession) {
		this.ksession = ksession;
	}

	public KnowledgeBase getKbase() {
		return kbase;
	}

	void setKbase(KnowledgeBase kbase) {
		this.kbase = kbase;
	}
}
