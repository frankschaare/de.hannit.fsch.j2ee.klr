package de.hannit.fsch.klr.web.commands;

import java.io.Serializable;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import de.hannit.fsch.klr.ejb.interfaces.Topics;
import de.hannit.fsch.klr.web.beans.MenuBar;
import de.hannit.fsch.klr.web.beans.MitarbeiterMBean;

@MessageDriven
(
activationConfig=
{
@ActivationConfigProperty(propertyName="destination", propertyValue=Topics.TOPIC_SELECTION),
@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
@ActivationConfigProperty(propertyName = "clientId", propertyValue = "CreateMitarbeiterMDCID"),
@ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "CreateMitarbeiterMDCSub")
})
public class CreateMitarbeiterMDC implements MessageListener 
{
private final String TTT_ENABLED = "Erstellt oder ändert ";
private String toolTipText = TTT_ENABLED;
private boolean disabled = true;
private MitarbeiterMBean selected = null;

	public CreateMitarbeiterMDC() 
	{
	}

	@Inject
	MenuBar menubar;
	@Override
	public void onMessage(Message message) 
	{
	Serializable activeSelection = null;

		try 
		{
		activeSelection = message.getBody(Serializable.class);
		
			if (activeSelection instanceof MitarbeiterMBean) 
			{
			setSelected((MitarbeiterMBean) activeSelection);
			System.out.println("Mitabeiter " + getSelected().getMitarbeiter().getNachname() + " wurde empfangen.");
			}
		} 
		catch (JMSException e) 
		{
		e.printStackTrace();
		}	
	}
	
	public MitarbeiterMBean getSelected() {
		return selected;
	}

	public void setSelected(MitarbeiterMBean toSet) 
	{
	this.selected = toSet;
	setDisabled(false);
	}

	public String getToolTipText() 
	{
	String suffix = selected != null ? "Mitarbeiterdaten für Personalnummer " + selected.getMitarbeiter().getPersonalNR() : "Mitarbeiterdaten";	
	return toolTipText + suffix;
	}

	public void setToolTipText(String toolTipText) {
		this.toolTipText = toolTipText;
	}

	public boolean isDisabled() 
	{
	return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	
}
