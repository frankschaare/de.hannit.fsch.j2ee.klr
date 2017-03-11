package de.hannit.fsch.klr.ejb.beans;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.jms.Topic;

import de.hannit.fsch.klr.ejb.interfaces.Topics;

@Singleton
@Startup
@DependsOn("ApplicationStart")
@JMSDestinationDefinitions(
		{@JMSDestinationDefinition(name=Topics.TOPIC_SELECTION, interfaceName="javax.jms.Topic")})
public class SelectionService 
{
@Inject
private JMSContext jmsContext;

@Resource(mappedName=Topics.TOPIC_SELECTION)
private Topic topic;

private Serializable activeSelection;

	public SelectionService() 
	{
	
	}

	public void setSelection(Serializable selected)
	{
	System.out.println("Selection erhalten");	
	this.activeSelection = selected;
	jmsContext.createProducer().send(topic, activeSelection);
	}
	
}
