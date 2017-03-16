package de.hannit.fsch.klr.web.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;

import de.hannit.fsch.klr.dataservice.mssql.MSSQLDataService;
import de.hannit.fsch.klr.ejb.interfaces.KostentraegerDAO;
import de.hannit.fsch.klr.model.kostenrechnung.KostenTraeger;
import de.hannit.fsch.klr.persistence.entities.Kostentraeger;

@ManagedBean
@ViewScoped
public class KTRMigration implements Serializable 
{
private static final long serialVersionUID = 1L;
@ManagedProperty (value = "#{dataService}")
private MSSQLDataService dataService;
private FacesContext fc = null;
private ArrayList<KostenTraeger> daten = new ArrayList<>();
private KostenTraeger selected = new KostenTraeger();
@EJB
private KostentraegerDAO ktrDAO;


	public KTRMigration() 
	{
	fc = FacesContext.getCurrentInstance();
	dataService = dataService != null ? dataService : fc.getApplication().evaluateExpressionGet(fc, "#{dataService}", MSSQLDataService.class);
	
	daten = dataService.getKostenTraeger();
	}
	
	public void migrate()
	{
		for (KostenTraeger ktr : daten)
		{
		Kostentraeger toInsert = new Kostentraeger();	
		toInsert.setKostentraeger(ktr.getBezeichnung());
		toInsert.setBezeichnung(ktr.getBeschreibung());
		toInsert.setVon(LocalDate.of(2012, 1, 1));
		ktrDAO.create(toInsert);			
		}
	}	
	
	public ListDataModel<KostenTraeger> getModel() 
	{
	return new ListDataModel<KostenTraeger>(daten);
	}	

	public KostenTraeger getSelected() {return selected;}
	public void setSelected(KostenTraeger selected) {this.selected = selected;}

	public MSSQLDataService getDataService() {return dataService;}
	public void setDataService(MSSQLDataService dataService) {this.dataService = dataService;}

	
}
