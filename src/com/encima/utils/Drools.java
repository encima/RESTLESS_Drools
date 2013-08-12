package com.encima.utils;

import java.io.Serializable;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.runtime.StatefulKnowledgeSession;

public class Drools implements Serializable {
	private StatefulKnowledgeSession ksession =  null;
	private KnowledgeBase kbase;
	
	public Drools(KnowledgeBase kbase) {
		this.kbase = kbase;
//		this.ksession = kbase.newStatefulKnowledgeSession();
		FileTools.serializeRuleBase(this);
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
