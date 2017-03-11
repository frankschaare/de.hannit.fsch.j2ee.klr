package de.hannit.fsch.klr.web.beans;

import java.io.Serializable;

import de.hannit.fsch.klr.persistence.entities.LogaDatensatz;

public class LogaDatensatzMBean implements Serializable 
{
private static final long serialVersionUID = 1L;
private LogaDatensatz logaDatensatz;

	public LogaDatensatzMBean() 
	{
	}
	public LogaDatensatzMBean(LogaDatensatz toSet) 
	{
	this.logaDatensatz = toSet;	
	}
	
	public LogaDatensatz getLogaDatensatz() {
		return logaDatensatz;
	}
	public void setLogaDatensatz(LogaDatensatz logaDatensatz) {
		this.logaDatensatz = logaDatensatz;
	}
	
	

}
