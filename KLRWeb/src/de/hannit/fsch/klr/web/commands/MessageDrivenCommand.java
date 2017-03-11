package de.hannit.fsch.klr.web.commands;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

import de.hannit.fsch.klr.ejb.interfaces.Topics;

@MessageDriven(activationConfig={@ActivationConfigProperty(propertyName="destination", propertyValue=Topics.TOPIC_SELECTION)})
public class MessageDrivenCommand implements MessageListener 
{

	public MessageDrivenCommand() 
	{
	}

	@Override
	public void onMessage(Message message) 
	{
	System.out.println("MessageDrivenCommand hat Nachricht erhalten");
	}

}
