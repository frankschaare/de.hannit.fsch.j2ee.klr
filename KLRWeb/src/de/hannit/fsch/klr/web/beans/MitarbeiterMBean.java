package de.hannit.fsch.klr.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import de.hannit.fsch.klr.ejb.interfaces.MitarbeiterDAO;
import de.hannit.fsch.klr.persistence.entities.Arbeitszeitanteil;
import de.hannit.fsch.klr.persistence.entities.Mitarbeiter;
import de.hannit.fsch.util.DateUtility;

public class MitarbeiterMBean implements Serializable 
{
private static final long serialVersionUID = 1L;
private Mitarbeiter entity;
private List<ArbeitszeitanteilMBean> arbeitszeitAnteile = new ArrayList<>();
@EJB
private MitarbeiterDAO mitarbeiterDAO;
	
	public MitarbeiterMBean() {}
	public MitarbeiterMBean(Mitarbeiter toSet) 
	{
	this.entity = toSet;
		for (Arbeitszeitanteil	azv : entity.getArbeitszeitAnteile()) 
		{
		arbeitszeitAnteile.add(new ArbeitszeitanteilMBean(azv));	
		}
	}
	
	/**
	 * Konvertiert einen vorhandenen Mitarbeiter in das JEE Model
	 * 
	 * @param toConvert: das 'alte' Mitarbeitermodel
	 */
	public MitarbeiterMBean(de.hannit.fsch.klr.model.mitarbeiter.Mitarbeiter toConvert)
	{
	entity = new Mitarbeiter();
	entity.setPersonalNR(toConvert.getPersonalNR());
	entity.setUsername(toConvert.getBenutzerName());
	entity.setNachname(toConvert.getNachname());
	entity.setVorname(toConvert.getVorname());
	
		for (de.hannit.fsch.klr.model.azv.Arbeitszeitanteil azv : toConvert.getArbeitszeitAnteile()) 
		{
		Arbeitszeitanteil neu = new Arbeitszeitanteil();
		neu.setMitarbeiterPNR(azv.getPersonalNummer());
		neu.setTeam(String.valueOf(azv.getITeam()));
		neu.setBerichtsmonat(DateUtility.asLocalDate(azv.getBerichtsMonat()));
			
			if (azv.getKostenstelle() == null) 
			{
			neu.setKostentraeger(azv.getKostentraeger());	
			} 
			else 
			{
			neu.setKostenstelle(azv.getKostenstelle());	
			}
		neu.setProzentanteil(azv.getProzentanteil());
		
		entity.getArbeitszeitAnteile().add(neu);
		}
	
		for (Arbeitszeitanteil neu : entity.getArbeitszeitAnteile()) 
		{
		getArbeitszeitAnteile().add(new ArbeitszeitanteilMBean(neu));	
		}
	}
	
	/**
	 * Bereitet den Datensatz für den Persistenzlayer vor
	 */
	@SuppressWarnings("unused")
	private void prepareMitarbeiter()
	{
	entity.getArbeitszeitAnteile().clear();
	
		for (ArbeitszeitanteilMBean azvMBean : arbeitszeitAnteile) 
		{
		entity.getArbeitszeitAnteile().add(azvMBean.getArbeitszeitAnteil());	
		}
	}
	public Mitarbeiter getMitarbeiter() {return entity;}
	public void setMitarbeiter(Mitarbeiter mitarbeiter) {this.entity = mitarbeiter;}
	public List<ArbeitszeitanteilMBean> getArbeitszeitAnteile() {return arbeitszeitAnteile;}
	public void setArbeitszeitAnteile(List<ArbeitszeitanteilMBean> arbeitszeitAnteile) {this.arbeitszeitAnteile = arbeitszeitAnteile;}

}
