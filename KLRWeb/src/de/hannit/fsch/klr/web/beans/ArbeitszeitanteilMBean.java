package de.hannit.fsch.klr.web.beans;

import java.io.Serializable;

import de.hannit.fsch.klr.persistence.entities.Arbeitszeitanteil;

public class ArbeitszeitanteilMBean implements Serializable 
{
private static final long serialVersionUID = 1L;
private Arbeitszeitanteil arbeitszeitAnteil;
	
	public ArbeitszeitanteilMBean() {}
	public ArbeitszeitanteilMBean(Arbeitszeitanteil toSet) 
	{
	this.arbeitszeitAnteil = toSet;	
	}
	
	public Arbeitszeitanteil getArbeitszeitAnteil() {
		return arbeitszeitAnteil;
	}
	public void setArbeitszeitAnteil(Arbeitszeitanteil arbeitszeitAnteil) {
		this.arbeitszeitAnteil = arbeitszeitAnteil;
	}
}
