/**
 * 
 */
package de.hannit.fsch.klr.model.organisation;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;

import de.hannit.fsch.common.MonatsSummen;
import de.hannit.fsch.klr.ejb.beans.ApplicationStart;
import de.hannit.fsch.klr.model.Datumsformate;
import de.hannit.fsch.klr.model.kostenrechnung.KostenStelle;
import de.hannit.fsch.klr.model.kostenrechnung.KostenTraeger;
import de.hannit.fsch.klr.model.mitarbeiter.GemeinKosten;
import de.hannit.fsch.klr.model.mitarbeiter.PersonalDurchschnittsKosten;
import de.hannit.fsch.klr.model.mitarbeiter.Tarifgruppe;
import de.hannit.fsch.klr.model.mitarbeiter.Tarifgruppen;
import de.hannit.fsch.klr.model.mitarbeiter.Vorstand;
import de.hannit.fsch.klr.model.team.Team;
import de.hannit.fsch.klr.persistence.entities.LogaDatensatz;
import de.hannit.fsch.klr.persistence.entities.Mitarbeiter;
import de.hannit.fsch.klr.web.beans.ArbeitszeitanteilMBean;
import de.hannit.fsch.klr.web.beans.MitarbeiterMBean;
import de.hannit.fsch.util.DateUtility;


/**
 * @author fsch
 *
 */
public class Organisation implements IOrganisation
{
@EJB
private ApplicationStart appStart;

private final String name = "Hannoversche Informationstechnologien";
private final static Logger log = Logger.getLogger(Organisation.class.getSimpleName());	
private String logPrefix = null;	
private FacesContext fc = null;
private FacesMessage msg = null;
private String detail = null;
private TreeMap<Date, Monatsbericht> monatsBerichte = new TreeMap<Date, Monatsbericht>(); 
private LocalDate abrechnungsMonat = null;
private TreeMap<Integer, MitarbeiterMBean> mitarbeiterPNR = null;
private TreeMap<String, MitarbeiterMBean> mitarbeiterNachname = null;
private TreeMap<Integer, Team> teams = null;
private TreeMap<Integer, KostenStelle> kostenstellen = null;
private TreeMap<Integer, KostenTraeger> kostentraeger = null;
private Tarifgruppen tarifgruppen = new Tarifgruppen();
private MonatsSummen mSumme = null;

private Vorstand vorstand = null;
/**
 * Wieviel Vollzeitanteile wurden aus den Tarifgruppen verteilt ?
 */
private double vzaeVerteilt = 0;
/**
 * Wie hoch ist die Summe der in den Mitarbeiterdaten gespeicherten Bruttoaufwendungen ?
 */
private double vzaeTotal = 0;	

	/**
	 * 
	 */
	public Organisation()
	{
	vorstand = new Vorstand();
	mitarbeiterPNR = new TreeMap<>();
	setAbrechnungsMonat(appStart.getMaxDate());
	loadData();
	}
	
	public void loadData()
	{
	fc = FacesContext.getCurrentInstance();	
	logPrefix = this.getClass().getName() + ".loadData(): ";		
	MitarbeiterMBean neu = null;
		
		for (Mitarbeiter entity : appStart.getMitarbeiter()) 
		{
		neu = new MitarbeiterMBean(entity);	
		neu.setAbrechnungsMonat(getAbrechnungsMonat());
		mitarbeiterPNR.put(neu.getPersonalNR(), neu);	
		}	
	setMitarbeiter(mitarbeiterPNR);
	setTarifgruppen(new Tarifgruppen());
	getTarifgruppen().setBerichtsMonat(appStart.getMaxDate());
	getTarifgruppen().setAnzahlMitarbeiter(getMitarbeiterNachPNR().size());
	
	/*
	 * Nachdem die Tarifgruppen erstellt wurden, wird für jeden Mitarbeiter
	 * das passende Vollzeitäquivalent gespeichert:
	 */
	vzaeVerteilt = 0;
		Tarifgruppe t = null;
		for (MitarbeiterMBean m : getMitarbeiterNachPNR().values())
		{
		t = tarifgruppen.getTarifGruppen().get(m.getTarifGruppe());	
		vzaeVerteilt += m.setVollzeitAequivalent(t.getVollzeitAequivalent());
		}
	if (fc.isProjectStage(ProjectStage.Development)) {log.log(Level.INFO, logPrefix + "Für den Monat " + Datumsformate.MONATLANG_JAHR.format(getAbrechnungsMonat()) + " wurden insgesamt " + NumberFormat.getCurrencyInstance().format(vzaeVerteilt) + " Vollzeitäquivalente auf " + getMitarbeiterNachPNR().size() + " Mitarbeiter verteilt.");}	
		
	/*
	 * Im Log wird nun zu Prüfzwecken ausgegeben, wie hoch das Vollzeitäquivalent Insgesamt beträgt:	
	 */
	vzaeTotal = 0;	
		for (MitarbeiterMBean m : getMitarbeiterNachPNR().values())
		{
			for (ArbeitszeitanteilMBean azv : m.getAzvMonat())
			{
			vzaeTotal += azv.getBruttoAufwand();	
			}
		}
		if (NumberFormat.getCurrencyInstance().format(vzaeTotal).equals(NumberFormat.getCurrencyInstance().format(vzaeVerteilt)))
		{
		log.log(Level.INFO, logPrefix + "Für den Monat " + Datumsformate.MONATLANG_JAHR.format(getAbrechnungsMonat()) + " wurden insgesamt " + NumberFormat.getCurrencyInstance().format(vzaeTotal) + " Vollzeitäquivalente entsprechend der Arbeitszeitanteile verteilt.");	
		}
		else
		{
		log.log(Level.SEVERE, logPrefix + "Für den Monat " + Datumsformate.MONATLANG_JAHR.format(getAbrechnungsMonat()) + " wurden insgesamt " + NumberFormat.getCurrencyInstance().format(vzaeTotal) + " Vollzeitäquivalente entsprechend der Arbeitszeitanteile verteilt.");
		}	
		
	/*
	 * Nun steht das Vollzeitäquivalent für jeden Mitarbeiter fest.
	 * Die Mitarbeiter werden erneut durchlaufen und es werden die Monatssummen für alle
	 * gemeldeten Kostenstellen / Kostenträger gebildet	
	 */
	mSumme = new MonatsSummen();
	mSumme.setGesamtSummen(getMitarbeiterNachPNR());
	mSumme.setBerichtsMonat(DateUtility.asDate(getAbrechnungsMonat()));
	
	PersonalDurchschnittsKosten pdk = new PersonalDurchschnittsKosten(DateUtility.asDate(getAbrechnungsMonat()));
	pdk.setMitarbeiter(getMitarbeiterNachPNR());
	
	GemeinKosten gk = new GemeinKosten(DateUtility.asDate(getAbrechnungsMonat()));
	gk.setMitarbeiter(getMitarbeiterNachPNR());		
	
	/*
	 * Nachdem alle Kostenstellen / Kostenträger verteilt sind, wird die Gesamtsumme gebildet und im Log ausgegeben.
	 * Diese MUSS gleich dem Gesamtbruttoaufwand sein !
	 */
	double monatssummenTotal = mSumme.getKstktrMonatssumme();
	
	if (NumberFormat.getCurrencyInstance().format(monatssummenTotal).equals(NumberFormat.getCurrencyInstance().format(vzaeVerteilt)))
	{
	mSumme.setChecked(true);
	mSumme.setSummeOK(true);

	pdk.setChecked(true);
	pdk.setDatenOK(true);
	gk.setChecked(true);
	gk.setDatenOK(true);
	if (fc.isProjectStage(ProjectStage.Development)) {log.log(Level.INFO, logPrefix + "Für den Monat " + Datumsformate.MONATLANG_JAHR.format(getAbrechnungsMonat()) + " wurden insgesamt " + NumberFormat.getCurrencyInstance().format(monatssummenTotal) + " auf " + mSumme.getGesamtKosten().size() + " Kostenstellen / Kostenträger verteilt.");}
	detail = "Für den Monat " + Datumsformate.MONATLANG_JAHR.format(getAbrechnungsMonat()) + " wurden insgesamt " + NumberFormat.getCurrencyInstance().format(vzaeVerteilt) + " auf " + mSumme.getGesamtKosten().size() + " Kostenstellen / Kostenträger verteilt. Das entspricht der Summe der Personalaufwendungen i.H.v. " + NumberFormat.getCurrencyInstance().format(monatssummenTotal); 
	msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Monatssummen erfolgreich geprüft.", detail);
	fc.addMessage(null, msg);
	}
	else
	{
	mSumme.setChecked(true);
	mSumme.setSummeOK(false);
	
	pdk.setChecked(true);
	pdk.setDatenOK(false);
	gk.setChecked(true);
	gk.setDatenOK(false);
	detail = "Für den Monat " + Datumsformate.MONATLANG_JAHR.format(getAbrechnungsMonat()) + " wurden insgesamt " + NumberFormat.getCurrencyInstance().format(vzaeVerteilt) + " auf " + mSumme.getGesamtKosten().size() + " Kostenstellen / Kostenträger verteilt. Die Summe der Personalaufwendungen beträgt aber " + NumberFormat.getCurrencyInstance().format(monatssummenTotal); 
	log.log(Level.SEVERE, logPrefix + detail);		
	msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Monatssummen sind fehlerhaft !", detail);
	fc.addMessage(null, msg);
	}		
	} 
	
	public void setTarifgruppen(Tarifgruppen tarifgruppen) 
	{
	this.tarifgruppen = tarifgruppen;
	Tarifgruppe tg = null;
	
		for (LogaDatensatz ds : appStart.getLogaDaten()) 
		{
			if (tarifgruppen.getTarifGruppen().containsKey(ds.getTarifgruppe())) 
			{
			tarifgruppen.getTarifGruppen().get(ds.getTarifgruppe()).addSummeTarifgruppe(ds.getBrutto());	
			tarifgruppen.getTarifGruppen().get(ds.getTarifgruppe()).addSummeStellen(ds.getStellenanteil());
			} 
			else 
			{
			tg = new Tarifgruppe();
			tg.setTarifGruppe(ds.getTarifgruppe());
			tg.setBerichtsMonat(ds.getBerichtsmonat());
			tg.setSummeTarifgruppe(ds.getBrutto());
			tg.setSummeStellen(ds.getStellenanteil());
			
			tarifgruppen.getTarifGruppen().put(tg.getTarifGruppe(), tg);
			}
		}
	}
	public TreeMap<Date, Monatsbericht> getMonatsBerichte()	{return monatsBerichte;}
	public void setMonatsBerichte(TreeMap<Date, Monatsbericht> monatsBerichte) {this.monatsBerichte = monatsBerichte;}

	/* (non-Javadoc)
	 * @see de.hannit.fsch.common.organisation.IOrganisation#getName()
	 */
	@Override
	public String getName() {return this.name;}

	@Override
	public void setMitarbeiter(TreeMap<Integer, MitarbeiterMBean> incoming)
	{
	mitarbeiterNachname = new TreeMap<String, MitarbeiterMBean>();	
	teams = new TreeMap<Integer, Team>();
	int teamNR = -1;
	
		for (MitarbeiterMBean mPNR : mitarbeiterPNR.values())
		{
		mitarbeiterNachname.put(mPNR.getNachname() + ", " + mPNR.getVorname() , mPNR);
		
			if (mPNR.getStatus() == MitarbeiterMBean.STATUS_ALTERSTEILZEIT_ANGESTELLTE || mPNR.getStatus() == MitarbeiterMBean.STATUS_ALTERSTEILZEIT_BEAMTE)
			{
			teamNR = 7;	
			}
			else
			{
			teamNR = mPNR.getTeamNR();
			}
			if (! teams.containsKey(teamNR))
			{
			Team team = new Team(teamNR);
			teams.put(teamNR, team);
			}
		teams.get(teamNR).addMitarbeiter(mPNR);
		}
	}

	@Override
	public TreeMap<String, MitarbeiterMBean> getMitarbeiterNachName(){return this.mitarbeiterNachname;}
	@Override
	public TreeMap<Integer, MitarbeiterMBean> getMitarbeiterNachPNR() {return this.mitarbeiterPNR;}

	@Override
	public TreeMap<Integer, Team> getTeams()
	{
	return teams;
	}

	@Override
	public void setKostenstellen(TreeMap<Integer, KostenStelle> kostenstellen)
	{
	this.kostenstellen = kostenstellen;	
	}

	@Override
	public void setKostentraeger(TreeMap<Integer, KostenTraeger> kostentraeger)
	{
	this.kostentraeger = kostentraeger;	
	}

	@Override
	public TreeMap<Integer, KostenTraeger> getKostentraeger()
	{
	return this.kostentraeger;
	}

	@Override
	public TreeMap<Integer, KostenStelle> getKostenStellen()
	{
	return this.kostenstellen;
	}

	@Override
	public Vorstand getVorstand()
	{
	return vorstand;
	}

	public MonatsSummen getmSumme() {return mSumme;}
	public void setmSumme(MonatsSummen mSumme) {this.mSumme = mSumme;}
	public Tarifgruppen getTarifgruppen() {return tarifgruppen;}
	public LocalDate getAbrechnungsMonat() {return abrechnungsMonat;}
	public void setAbrechnungsMonat(LocalDate abrechnungsMonat) {this.abrechnungsMonat = abrechnungsMonat;}
	
	

}
