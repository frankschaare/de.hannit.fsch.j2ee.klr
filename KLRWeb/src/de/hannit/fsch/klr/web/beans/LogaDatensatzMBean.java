package de.hannit.fsch.klr.web.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import de.hannit.fsch.klr.persistence.entities.LogaDatensatz;
import de.hannit.fsch.util.DateUtility;

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
	
	public LogaDatensatzMBean(de.hannit.fsch.klr.model.loga.LoGaDatensatz toSet) 
	{
	this.logaDatensatz = new LogaDatensatz();
	logaDatensatz.setMitarbeiterPNR(toSet.getPersonalNummer());
	logaDatensatz.setBerichtsmonat(DateUtility.asLocalDate(toSet.getAbrechnungsMonat()));
	logaDatensatz.setBrutto(BigDecimal.valueOf(toSet.getBrutto()));
	logaDatensatz.setTarifgruppe(toSet.getTarifGruppe());
	logaDatensatz.setTarifstufe(toSet.getTarifstufe());
	logaDatensatz.setStellenanteil((float) toSet.getStellenAnteil());
	}	
	public LogaDatensatz getLogaDatensatz() {
		return logaDatensatz;
	}
	public void setLogaDatensatz(LogaDatensatz logaDatensatz) {
		this.logaDatensatz = logaDatensatz;
	}
	
	

}
