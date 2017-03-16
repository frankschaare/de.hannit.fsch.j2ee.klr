package de.hannit.fsch.klr.web.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;

import de.hannit.fsch.klr.dataservice.mssql.MSSQLDataService;
import de.hannit.fsch.klr.ejb.interfaces.LogaDatensatzDAO;
import de.hannit.fsch.klr.model.loga.LoGaDatensatz;

@ManagedBean
@ViewScoped
public class LoGaMigration implements Serializable 
{
private static final long serialVersionUID = 1L;
@ManagedProperty (value = "#{dataService}")
private MSSQLDataService dataService;
private FacesContext fc = null;
private ArrayList<LogaDatensatzMBean> logaDaten = new ArrayList<>();
private LogaDatensatzMBean selected = new LogaDatensatzMBean();
@EJB
private LogaDatensatzDAO logaDAO;


	public LoGaMigration() 
	{
	fc = FacesContext.getCurrentInstance();
	dataService = dataService != null ? dataService : fc.getApplication().evaluateExpressionGet(fc, "#{dataService}", MSSQLDataService.class);
	
	ArrayList<LoGaDatensatz> altDaten = dataService.getLoGaDaten();
		for (LoGaDatensatz loGaDatensatz : altDaten) 
		{
		logaDaten.add(new LogaDatensatzMBean(loGaDatensatz));	
		}
	}
	
	public void migrate()
	{
		for (LogaDatensatzMBean logaMBean : logaDaten) 
		{
		logaDAO.create(logaMBean.getLogaDatensatz());	
		}
	}	
	
	public ListDataModel<LogaDatensatzMBean> getModel() 
	{
	return new ListDataModel<LogaDatensatzMBean>(logaDaten);
	}	

	public LogaDatensatzMBean getSelected() {return selected;}
	public void setSelected(LogaDatensatzMBean selected) {this.selected = selected;}

	public MSSQLDataService getDataService() {return dataService;}
	public void setDataService(MSSQLDataService dataService) {this.dataService = dataService;}

	
}
