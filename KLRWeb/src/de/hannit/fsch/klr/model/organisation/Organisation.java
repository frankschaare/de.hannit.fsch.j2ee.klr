/**
 * 
 */
package de.hannit.fsch.klr.model.organisation;

import java.time.LocalDate;
import java.util.Date;
import java.util.TreeMap;

import javax.ejb.EJB;

import de.hannit.fsch.klr.ejb.beans.ApplicationStart;
import de.hannit.fsch.klr.model.kostenrechnung.KostenStelle;
import de.hannit.fsch.klr.model.kostenrechnung.KostenTraeger;
import de.hannit.fsch.klr.model.mitarbeiter.Tarifgruppe;
import de.hannit.fsch.klr.model.mitarbeiter.Tarifgruppen;
import de.hannit.fsch.klr.model.mitarbeiter.Vorstand;
import de.hannit.fsch.klr.model.team.Team;
import de.hannit.fsch.klr.persistence.entities.LogaDatensatz;
import de.hannit.fsch.klr.persistence.entities.Mitarbeiter;
import de.hannit.fsch.klr.web.beans.MitarbeiterMBean;


/**
 * @author fsch
 *
 */
public class Organisation implements IOrganisation
{
@EJB
private ApplicationStart appStart;

private final String name = "Hannoversche Informationstechnologien";
private TreeMap<Date, Monatsbericht> monatsBerichte = new TreeMap<Date, Monatsbericht>(); 
private LocalDate abrechnungsMonat = null;
private TreeMap<Integer, MitarbeiterMBean> mitarbeiterPNR = null;
private TreeMap<String, MitarbeiterMBean> mitarbeiterNachname = null;
private TreeMap<Integer, Team> teams = null;
private TreeMap<Integer, KostenStelle> kostenstellen = null;
private TreeMap<Integer, KostenTraeger> kostentraeger = null;
private Tarifgruppen tarifgruppen = new Tarifgruppen();

private Vorstand vorstand = null;

	/**
	 * 
	 */
	public Organisation()
	{
	vorstand = new Vorstand();
	mitarbeiterPNR = new TreeMap<>();
	MitarbeiterMBean neu = null;
	setAbrechnungsMonat(appStart.getMaxDate());
	
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


	public Tarifgruppen getTarifgruppen() {return tarifgruppen;}
	public LocalDate getAbrechnungsMonat() {return abrechnungsMonat;}
	public void setAbrechnungsMonat(LocalDate abrechnungsMonat) {this.abrechnungsMonat = abrechnungsMonat;}
	
	

}
