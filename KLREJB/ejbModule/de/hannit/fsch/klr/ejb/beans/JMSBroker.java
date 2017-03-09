package de.hannit.fsch.klr.ejb.beans;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.jms.Topic;

import de.hannit.fsch.klr.ejb.interfaces.Topics;
import de.hannit.fsch.klr.persistence.entities.Mitarbeiter;

/**
 * Singleton wird beim Start der Applikation erzeugt
 * 
 * @author hit
 * @since 08.03.2017
 *
 */
@Singleton
@Startup
@JMSDestinationDefinitions(
		{@JMSDestinationDefinition(name=Topics.TOPIC_MITARBEITER, interfaceName="javax.jms.Topic")})
public class JMSBroker 
{
@Inject
private JMSContext jmsContext;

@Resource(mappedName=Topics.TOPIC_MITARBEITER)
private Topic topic;

	public void send(Mitarbeiter m)
	{
	jmsContext.createProducer().send(topic, m);
	}

}
