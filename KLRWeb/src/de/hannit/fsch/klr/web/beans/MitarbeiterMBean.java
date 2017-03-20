/**
 * 
 */
package de.hannit.fsch.klr.web.beans;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.hannit.fsch.klr.model.Constants;
import de.hannit.fsch.klr.model.mitarbeiter.Person;
import de.hannit.fsch.klr.model.team.TeamMitgliedschaft;
import de.hannit.fsch.klr.persistence.entities.Arbeitszeitanteil;
import de.hannit.fsch.klr.persistence.entities.Mitarbeiter;
import de.hannit.fsch.util.DateUtility;

/**
 * @author fsch
 *
 */
public class MitarbeiterMBean extends Person 
{
public static final int STATUS_SONSTIGE = 0;
public static final int STATUS_ANGESTELLTER = 1;
public static final int STATUS_BEAMTER = 2;
public static final int STATUS_AUSZUBILDENDER = 6;
public static final int STATUS_ALTERSTEILZEIT_ANGESTELLTE = 7;
public static final int STATUS_ALTERSTEILZEIT_BEAMTE = 8;
public static final int STATUS_ELTERNZEIT = 5;

private int personalNR = 0;
private int teamNR = -1;
private int letzteTeamNR = -1;
private int status = -1;
private String benutzerName;
private LocalDate abrechnungsMonat = null;
private double brutto;
private double vollzeitAequivalent = 0;
private String tarifGruppe = null;
private String tarifStufe = null;
private double stellenAnteil = 0;

private ArrayList<TeamMitgliedschaft> teamMitgliedschaften = null;
private ArrayList<ArbeitszeitanteilMBean> azvMonat = new ArrayList<ArbeitszeitanteilMBean>();
private boolean azvAktuell = true;
private String rowStyle = null;
private Mitarbeiter entity;
private List<ArbeitszeitanteilMBean> arbeitszeitAnteile = new ArrayList<>();


/*
 * Wird benutzt, um das passende Icon im Navigationsbaum anzuzeigen
 */
private String typ = Constants.TREETYPES.TREETYPE_DEFAULT;

/**
 * Der Gesamtprozentanteil der gespeicherten Arbeitszeitanteile.
 * Dieser MUSS = 100 sein, sonst ist etwas schief gelaufen.
 * Wird vom NavPart und vom NavTreeContentProvider geprüft.
 */
private int azvProzentSumme = 0;

	/**
	 * 
	 */
	public MitarbeiterMBean() 
	{
	}
	
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
	
	
	
	public List<ArbeitszeitanteilMBean> getArbeitszeitAnteile(){return arbeitszeitAnteile;}
	public void setArbeitszeitAnteile(List<ArbeitszeitanteilMBean> arbeitszeitAnteile){this.arbeitszeitAnteile = arbeitszeitAnteile;}
	public void setTeamMitgliedschaften(ArrayList<TeamMitgliedschaft> teamMitgliedschaften)	
	{
	this.teamMitgliedschaften = teamMitgliedschaften;
		switch (this.teamMitgliedschaften.size())
		{
		case 0:	
		break;

		// Standard: Teammitgliedschaften enthält genau einen Eintrag
		case 1:
		this.teamNR = teamMitgliedschaften.get(teamMitgliedschaften.size()-1).getTeamNummer();	
		break;
		
		/*
		 * Sonderfall für z.b. Altersteilzeitler. 
		 * Hier enthält Teammitgliedschaften mindestens zwei Einträge.
		 * Für die Personaldurchschnittskosten wird dann zusätzlich das letzte Team gespeichert:
		 */
		default:
			for (TeamMitgliedschaft teamMitgliedschaft : teamMitgliedschaften)
			{
				if (teamMitgliedschaft.getSqlEnddatum() == null)
				{
				this.teamNR = teamMitgliedschaft.getTeamNummer();
				}
				else
				{
				this.letzteTeamNR = teamMitgliedschaft.getTeamNummer();	
				}
			}
		break;
		}
		
		switch (this.teamNR)
		{
		case 6:
		setStatus(MitarbeiterMBean.STATUS_AUSZUBILDENDER);	
		break;
		case 7:
		setStatus(MitarbeiterMBean.STATUS_ALTERSTEILZEIT_ANGESTELLTE);	
		break;
		case 8:
		setStatus(MitarbeiterMBean.STATUS_ALTERSTEILZEIT_BEAMTE);	
		break;

		default:
		break;
		}
	}
	public ArrayList<ArbeitszeitanteilMBean> getAzvMonat() {return azvMonat;}
	public void setAzvMonat(ArrayList<ArbeitszeitanteilMBean> azvMonat) {this.azvMonat = azvMonat;}

	public int getTeamNR() 
	{
		switch (teamMitgliedschaften.size())
		{
		case 0:
			if (azvMonat != null && ! azvMonat.isEmpty())
			{
			teamNR = azvMonat.get((azvMonat.size()-1)).getITeam();	
			}
		break;
		case 1:
		teamNR = teamMitgliedschaften.get(teamMitgliedschaften.size()-1).getTeamNummer();	
		break;

		default:
		break;
		}
	return teamNR;
	}
	public int getLetzteTeamNR() 
	{
	return letzteTeamNR;
	}
	
	public String getLabel() 
	{
	return getNachname() + ", " + getVorname();
	}
	
	public int getPersonalNR() {return personalNR;}
	public void setPersonalNR(int personalNR) {this.personalNR = personalNR;}
	public void setPersonalNRAsString(String personalNR){this.personalNR = Integer.parseInt(personalNR);}
	public String getPersonalNRAsString() {return String.valueOf(personalNR);}

	public int getStatus(){return status;}
	public void setStatus(int status)
	{
		switch (this.status)
		{
		case MitarbeiterMBean.STATUS_AUSZUBILDENDER:
		break;
		case MitarbeiterMBean.STATUS_ALTERSTEILZEIT_ANGESTELLTE:
		break;
		case MitarbeiterMBean.STATUS_ALTERSTEILZEIT_BEAMTE:
		break;
		default:
		this.status = status;
		break;
		}
	}

	public String getBenutzerName() {return benutzerName;}
	public void setBenutzerName(String benutzerName) {this.benutzerName = benutzerName;}

	public LocalDate getAbrechnungsMonat(){return abrechnungsMonat;}
	
	public void setAbrechnungsMonat(LocalDate abrechnungsMonat)	
	{
	this.abrechnungsMonat = abrechnungsMonat;
		if (arbeitszeitAnteile.size() > 0) 
		{
		ArrayList<ArbeitszeitanteilMBean> toSet = arbeitszeitAnteile.stream().filter(a -> a.getArbeitszeitAnteil().getBerichtsmonat().equals(abrechnungsMonat)).collect(Collectors.toCollection(ArrayList::new));
		setAzvMonat(toSet);
		}
	}

	public double getBrutto(){return brutto;}
	public void setBrutto(double brutto){this.brutto = brutto;}

	public double getVollzeitAequivalent(){return vollzeitAequivalent;}
	
	/**
	 * Speichert das Vollzeitäquivalent für den Mitarbeiter
	 * Das Vollzeitäquivalent wird aus den Tarifgruppen geliefert, wo der Mittelwert für die jeweilige Tarifgruppe bekannt ist.
	 * 
	 * Da das Vollzeitäquivalent für DIESEN Mitarbeiter gilt, wird mit dem Stellenanteil mutipliziert
	 * 
	 * Nachdem das Vollzeitäquivalent gespeichert wurde, wird in der Methode #updateBruttoanteil der Bruttoanteil
	 * für die gemeldeten Arbeitszeitanteile des Mitarbeiters gespeichert
	 * @param vollzeitAequivalent
	 */
	public double setVollzeitAequivalent(double vollzeitAequivalent)
	{
	this.vollzeitAequivalent = vollzeitAequivalent;
	updateBruttoanteil();
	return (this.vollzeitAequivalent * this.stellenAnteil);
	}
	
	/**
	 * 
	 * @return Die Summe aller AZV-Anteile. MUSS = 100% sein, sonst wird ein Fehler ausgegeben.
	 */
	public int getAzvProzentSumme()
	{
	azvProzentSumme = 0;
	
		for (ArbeitszeitanteilMBean azv : azvMonat)
		{
		azvProzentSumme += azv.getProzentanteil();
		}
	return azvProzentSumme;	
	}

	private void updateBruttoanteil()
	{
	double brutto = 0;	
		for (ArbeitszeitanteilMBean a : azvMonat)
		{
		brutto = ((this.brutto /100) * a.getProzentanteil());	
		// brutto = ((this.vollzeitAequivalent * this.stellenAnteil) / 100) * a.getProzentanteil();
		a.setBruttoAufwand(brutto);
		}
	}

	public String getTarifGruppe(){return tarifGruppe;}
	
	/**
	 * Legt die Tarifgruppe fest, in der der Mitarbeiter bezahlt wurde.
	 * Anhand der Tarifgruppe wird festgestellt, in welchem Beschaftigungsverhältnis der Mitarbeiter
	 * im aktuellen Monat stand.
	 */
	public void setTarifGruppe(String tarifGruppe)
	{
	this.tarifGruppe = tarifGruppe;

		switch (tarifGruppe)
		{
		case "5":
		setStatus(STATUS_ANGESTELLTER);	
		break;
		
		case "6":
		setStatus(STATUS_ANGESTELLTER);	
		break;
		
		case "7":
		setStatus(STATUS_ANGESTELLTER);	
		break;
		
		case "8":
		setStatus(STATUS_ANGESTELLTER);	
		break;
		
		case "9":
		setStatus(STATUS_ANGESTELLTER);	
		break;
		
		case "10":
		setStatus(STATUS_ANGESTELLTER);	
		break;
		
		case "11":
		setStatus(STATUS_ANGESTELLTER);	
		break;
		
		case "12":
		setStatus(STATUS_ANGESTELLTER);	
		break;
		
		case "13":
		setStatus(STATUS_ANGESTELLTER);	
		break;
		
		case "14":
		setStatus(STATUS_ANGESTELLTER);	
		break;
		
		case "15":
		setStatus(STATUS_ANGESTELLTER);	
		break;
		
		case "A8":
		setStatus(STATUS_BEAMTER);	
		break;
		
		case "A9":
		setStatus(STATUS_BEAMTER);	
		break;	
		
		case "A9mD":
		setStatus(STATUS_BEAMTER);	
		break;
		
		case "A10":
		setStatus(STATUS_BEAMTER);	
		break;	
		
		case "A11":
		setStatus(STATUS_BEAMTER);	
		break;
		
		case "A12":
		setStatus(STATUS_BEAMTER);	
		break;	
		
		case "A13":
		setStatus(STATUS_BEAMTER);	
		break;	
		
		case "A14":
		setStatus(STATUS_BEAMTER);	
		break;	
		
		case "A15":
		setStatus(STATUS_BEAMTER);	
		break;	
		
		case "A16":
		setStatus(STATUS_BEAMTER);	
		break;	
		
		case "AZU":
		setStatus(STATUS_AUSZUBILDENDER);	
		break;		

		default:
		setStatus(STATUS_SONSTIGE);	
		break;
		}
	}

	public String getTarifStufe(){return tarifStufe;}
	public void setTarifStufe(String tarifStufe){this.tarifStufe = tarifStufe;}

	public double getStellenAnteil(){return stellenAnteil;}
	public void setStellenAnteil(double stellenAnteil){this.stellenAnteil = stellenAnteil;}

	public boolean isAzvAktuell(){return azvAktuell;}
	public void setAzvAktuell(boolean azvAktuell){this.azvAktuell = azvAktuell;}

	public void setTyp(String typ) {this.typ = typ;}
	public String getTyp() 
	{
		switch (getStatus()) 
		{
		case STATUS_ALTERSTEILZEIT_ANGESTELLTE:
		setTyp(Constants.TREETYPES.TREETYPE_ALTERSTEILZEIT);	
		break;
		case STATUS_ALTERSTEILZEIT_BEAMTE:
		setTyp(Constants.TREETYPES.TREETYPE_ALTERSTEILZEIT);	
		break;
		case STATUS_AUSZUBILDENDER:
		setTyp(Constants.TREETYPES.TREETYPE_AUSZUBILDENDER);	
		break;

		default:
		typ = Constants.TREETYPES.TREETYPE_DEFAULT;
		break;
		}
		
		if (! isAzvAktuell()) 
		{
		typ = Constants.TREETYPES.TREETYPE_AZV_NICHT_AKTUELL;
		}
		
		if (getAzvProzentSumme() != 100) 
		{
		typ = Constants.TREETYPES.TREETYPE_ERROR;	
		}
		
		if (azvMonat == null) 
		{
		typ = Constants.TREETYPES.TREETYPE_DISABLED;	
		}
		 
	return typ;
	}

	public String getRowStyle() 
	{
		if (getPersonalNR() == 0) 
		{
		rowStyle = Constants.CSS.ROWSTYLE_RED;	
		} 

		if (getBenutzerName().equals(Constants.DUMMIES.DUMMY_USERNAME)) 
		{
		rowStyle = Constants.CSS.ROWSTYLE_ORANGE;	
		} 		
		
	return rowStyle;
	}	
	
	public Mitarbeiter getMitarbeiter() {return entity;}
}
