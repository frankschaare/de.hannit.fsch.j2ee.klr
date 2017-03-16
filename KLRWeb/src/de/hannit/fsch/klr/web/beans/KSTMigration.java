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
import de.hannit.fsch.klr.ejb.interfaces.KostenstelleDAO;
import de.hannit.fsch.klr.model.kostenrechnung.KostenStelle;
import de.hannit.fsch.klr.persistence.entities.Kostenstelle;

@ManagedBean
@ViewScoped
public class KSTMigration implements Serializable 
{
private static final long serialVersionUID = 1L;
@ManagedProperty (value = "#{dataService}")
private MSSQLDataService dataService;
private FacesContext fc = null;
private ArrayList<KostenStelle> daten = new ArrayList<>();
private KostenStelle selected = new KostenStelle();
@EJB
private KostenstelleDAO kstDAO;


	public KSTMigration() 
	{
	fc = FacesContext.getCurrentInstance();
	dataService = dataService != null ? dataService : fc.getApplication().evaluateExpressionGet(fc, "#{dataService}", MSSQLDataService.class);
	
	daten = dataService.getKostenstellen();
	}
	
	public void migrate()
	{
		for (KostenStelle kst : daten) 
		{
		Kostenstelle toInsert = new Kostenstelle();	
		toInsert.setKostenstelle(kst.getBezeichnung());
		toInsert.setBezeichnung(kst.getBeschreibung());
		toInsert.setVon(LocalDate.of(2012, 1, 1));
		kstDAO.create(toInsert);	
		}
	}	
	
	public ListDataModel<KostenStelle> getModel() 
	{
	return new ListDataModel<KostenStelle>(daten);
	}	

	public KostenStelle getSelected() {return selected;}
	public void setSelected(KostenStelle selected) {this.selected = selected;}

	public MSSQLDataService getDataService() {return dataService;}
	public void setDataService(MSSQLDataService dataService) {this.dataService = dataService;}

	
}
