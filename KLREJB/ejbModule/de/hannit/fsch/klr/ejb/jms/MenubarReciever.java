package de.hannit.fsch.klr.ejb.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import de.hannit.fsch.klr.ejb.beans.JMSBroker;
import de.hannit.fsch.klr.persistence.entities.Mitarbeiter;

@MessageDriven(activationConfig={@ActivationConfigProperty(propertyName="destination", propertyValue=JMSBroker.TOPIC_MITARBEITER)})
public class MenubarReciever implements MessageListener 
{

	@Override
	public void onMessage(Message message) 
	{
		if (message instanceof ObjectMessage) 
		{
			try 
			{
			Mitarbeiter m = message.getBody(Mitarbeiter.class);
			System.err.println("Mitarbeiter " + m.getNachname() + " wurde versendet !");	
			} 
			catch (JMSException e) 
			{
			e.printStackTrace();
			}
				
		}
	}

}
