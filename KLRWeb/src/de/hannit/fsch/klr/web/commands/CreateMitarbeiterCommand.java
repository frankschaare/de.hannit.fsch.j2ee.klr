package de.hannit.fsch.klr.web.commands;

import de.hannit.fsch.klr.web.beans.MitarbeiterMBean;

public class CreateMitarbeiterCommand
{
private final String TTT_ENABLED = "Erstellt oder ändert ";
private String toolTipText = TTT_ENABLED;	
private MitarbeiterMBean mitarbeiter = new MitarbeiterMBean();	
private boolean disabled = true;

	public CreateMitarbeiterCommand() 
	{
	toolTipText = TTT_ENABLED;	
	}

	public String getToolTipText() 
	{
	return mitarbeiter.getMitarbeiter() != null ? toolTipText + " Mitarbeiterdaten für Mitarbeiter " + mitarbeiter.getMitarbeiter().getNachname() : toolTipText + " Mitarbeiterdaten" ;
	}

	public void setToolTipText(String toolTipText) {
		this.toolTipText = toolTipText;
	}

	public MitarbeiterMBean getMitarbeiter() {
		return mitarbeiter;
	}

	public void setMitarbeiter(MitarbeiterMBean mitarbeiter) 
	{
	this.mitarbeiter = mitarbeiter;
	this.disabled = false;
	}

	public boolean isDisabled() 
	{
	return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	


}
