package de.hannit.fsch.klr.web.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;

import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class MitarbeiterDetails implements Serializable 
{
private static final long serialVersionUID = 1L;
@ManagedProperty (value = "#{menuBar}")
private MenuBar menuBar;
private FacesContext fc = null;
private MitarbeiterMBean selected;
private boolean btnBenutzerAktualisierenDisabled = true;
private boolean btnBenutzerResetDisabled = true;
private boolean btnBenutzerSpeichernDisabled = true;

private boolean inputPersonalnummerDisabled = false;
private String gridHeaderText = MitarbeiterListe.ERFASSEN;


	public MitarbeiterDetails() 
	{
	fc = FacesContext.getCurrentInstance();
	menuBar = menuBar != null ? menuBar : fc.getApplication().evaluateExpressionGet(fc, "#{menuBar}", MenuBar.class);	
	setSelected(menuBar.getCreateMitarbeiterCommand().getMitarbeiter());
	}
	
	public ListDataModel<ArbeitszeitanteilMBean> getArbeitszeitanteile() 
	{
	return new ListDataModel<ArbeitszeitanteilMBean>(getSelected().getArbeitszeitAnteile());
	}
	
	public void onRowSelect(SelectEvent event) 
	{

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

	public MenuBar getMenuBar() {
		return menuBar;
	}

	public void setMenuBar(MenuBar menuBar) {
		this.menuBar = menuBar;
	}

	public MitarbeiterMBean getSelected() {
		return selected;
	}

	public void setSelected(MitarbeiterMBean selected) 
	{
	this.selected = selected;
	int rowCount = 1;
		for (ArbeitszeitanteilMBean mBean : getSelected().getArbeitszeitAnteile()) 
		{
		mBean.getArbeitszeitAnteil().setId(rowCount);
		rowCount++;
		}
	}

	public String getGridHeaderText() {
		return gridHeaderText;
	}

	public boolean isBtnBenutzerAktualisierenDisabled() {
		return btnBenutzerAktualisierenDisabled;
	}

	public void setBtnBenutzerAktualisierenDisabled(boolean btnBenutzerAktualisierenDisabled) {
		this.btnBenutzerAktualisierenDisabled = btnBenutzerAktualisierenDisabled;
	}

	public boolean isBtnBenutzerSpeichernDisabled() {
		return btnBenutzerSpeichernDisabled;
	}

	public void setBtnBenutzerSpeichernDisabled(boolean btnBenutzerSpeichernDisabled) {
		this.btnBenutzerSpeichernDisabled = btnBenutzerSpeichernDisabled;
	}

	public boolean isBtnBenutzerResetDisabled() {
		return btnBenutzerResetDisabled;
	}

	public void setBtnBenutzerResetDisabled(boolean btnBenutzerResetDisabled) {
		this.btnBenutzerResetDisabled = btnBenutzerResetDisabled;
	}

	public boolean isInputPersonalnummerDisabled() {
		return inputPersonalnummerDisabled;
	}

	public void setInputPersonalnummerDisabled(boolean inputPersonalnummerDisabled) {
		this.inputPersonalnummerDisabled = inputPersonalnummerDisabled;
	}

	
}
