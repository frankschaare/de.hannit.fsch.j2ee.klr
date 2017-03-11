package de.hannit.fsch.klr.web.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;

import org.primefaces.event.SelectEvent;

import de.hannit.fsch.klr.dataservice.mssql.MSSQLDataService;
import de.hannit.fsch.klr.ejb.beans.SelectionService;
import de.hannit.fsch.klr.ejb.interfaces.MitarbeiterDAO;
import de.hannit.fsch.klr.model.mitarbeiter.Mitarbeiter;

@ManagedBean
@ViewScoped
public class MitarbeiterListe implements Serializable
{
private static final long serialVersionUID = 1L;
private static final String ERFASSEN = "Mitarbeiter erfassen:";
private static final String BEARBEITEN = "Mitarbeiter bearbeiten:";
@ManagedProperty (value = "#{dataService}")
private MSSQLDataService dataService;
private FacesContext fc = null;
private ArrayList<MitarbeiterMBean> mitarbeiterListe = new ArrayList<>();
private MitarbeiterMBean selected = new MitarbeiterMBean(new de.hannit.fsch.klr.persistence.entities.Mitarbeiter());
private boolean btnBenutzerAktualisierenDisabled = true;
private boolean btnBenutzerResetDisabled = true;
private boolean inputPersonalnummerDisabled = false;
private String gridHeaderText = MitarbeiterListe.ERFASSEN;

@EJB
private SelectionService selectionService;
@EJB
private MitarbeiterDAO mitarbeiterDAO;

	public MitarbeiterListe() 
	{
	init();	
	}

	private void init()
	{
	fc = FacesContext.getCurrentInstance();
	dataService = dataService != null ? dataService : fc.getApplication().evaluateExpressionGet(fc, "#{dataService}", MSSQLDataService.class);
	ArrayList<Mitarbeiter> alleMitarbeiter = dataService.getMitarbeiter();
		
		for (Mitarbeiter mitarbeiter : alleMitarbeiter) 
		{
		mitarbeiterListe.add(new MitarbeiterMBean(mitarbeiter));	
		}
	}

	public void delete()
	{
		
	}
	
	public void reset()
	{
		
	}
	
	public void save()
	{
		
	}
	
	public void update()
	{

	}

	public void migrate()
	{
		for (MitarbeiterMBean mitarbeiterMBean : mitarbeiterListe) 
		{
		mitarbeiterDAO.create(mitarbeiterMBean.getMitarbeiter());	
		}
	}
	
	public void onRowSelect(SelectEvent event) 
	{
	MitarbeiterMBean mBean = (MitarbeiterMBean) event.getObject();
	selectionService.setSelection(mBean);	
	setSelected(mBean);
	setGridHeaderText(MitarbeiterListe.BEARBEITEN);
	btnBenutzerAktualisierenDisabled = false;
	btnBenutzerResetDisabled = false;
	inputPersonalnummerDisabled = true;
	
	FacesMessage msg = new FacesMessage("Mitarbeiter ", mBean.getMitarbeiter().getPersonalNR() + " ausgewählt.");
	FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public ListDataModel<MitarbeiterMBean> getMitarbeiterModel() 
	{
	return new ListDataModel<MitarbeiterMBean>(mitarbeiterListe);
	}


	public MitarbeiterMBean getSelected() {return selected;}
	public void setSelected(MitarbeiterMBean selected) {this.selected = selected;}
	public ArrayList<MitarbeiterMBean> getMitarbeiterListe() {return mitarbeiterListe;}
	public String getGridHeaderText() {return gridHeaderText;}
	public void setGridHeaderText(String gridHeaderText) {this.gridHeaderText = gridHeaderText;}
	public boolean isBtnBenutzerAktualisierenDisabled() {return btnBenutzerAktualisierenDisabled;}
	public boolean isBtnBenutzerSpeichernDisabled() {return (mitarbeiterListe != null && mitarbeiterListe.size() > 0) ? false:true;}
	public boolean isBtnBenutzerResetDisabled() {return btnBenutzerResetDisabled;}
	public boolean isInputPersonalnummerDisabled() {return inputPersonalnummerDisabled;}
	public MSSQLDataService getDataService() {return dataService;}
	public void setDataService(MSSQLDataService dataService) {this.dataService = dataService;}

	
    
}
