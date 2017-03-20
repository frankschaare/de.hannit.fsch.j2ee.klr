package de.hannit.fsch.klr.ejb.beans;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import de.hannit.fsch.klr.ejb.interfaces.KostenstelleDAO;
import de.hannit.fsch.klr.ejb.interfaces.KostentraegerDAO;
import de.hannit.fsch.klr.ejb.interfaces.LogaDatensatzDAO;
import de.hannit.fsch.klr.ejb.interfaces.MitarbeiterDAO;
import de.hannit.fsch.klr.persistence.entities.Kostenstelle;
import de.hannit.fsch.klr.persistence.entities.Kostentraeger;
import de.hannit.fsch.klr.persistence.entities.LogaDatensatz;
import de.hannit.fsch.klr.persistence.entities.Mitarbeiter;

/**
 * Singleton wird beim Start der Applikation erzeugt
 * 
 * @author hit
 * @since 08.03.2017
 *
 */
@Singleton
@Startup
public class ApplicationStart 
{
@EJB	
private MitarbeiterDAO mitarbeiterDAO;
private List<Mitarbeiter> mitarbeiter = new ArrayList<Mitarbeiter>();
@EJB	
private KostenstelleDAO kstDAO;
private List<Kostenstelle> kostenStellen;
@EJB	
private KostentraegerDAO ktrDAO;
private List<Kostentraeger> kostenTraeger;
@EJB
private LogaDatensatzDAO logaDAO;
private List<LogaDatensatz> logaDaten;
private LocalDate maxDate;
	
	/**
	 * Lädt alle Mitarbeiter für den Monat, für den maximale Loga Daten vorliegen
	 */
	@PostConstruct
	private void init()
	{
	setMaxDate(logaDAO.findMAXDate());	
	logaDaten = logaDAO.findByDate(getMaxDate());
		for (LogaDatensatz logaDatensatz : logaDaten) 
		{
		mitarbeiter.add(mitarbeiterDAO.getMitarbeiter(logaDatensatz.getMitarbeiterPNR()));	
		}
	Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "Es wurden " + mitarbeiter.size() + " Mitarbeiter vom Persistenzlayer vorgeladen.");
	
	kostenStellen = kstDAO.getAll();
	Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "Es wurden " + kostenStellen.size() + " Kostenstellen vom Persistenzlayer vorgeladen.");
	
	kostenTraeger = ktrDAO.getAll();
	Logger.getLogger(this.getClass().getSimpleName()).log(Level.INFO, "Es wurden " + kostenTraeger.size() + " Kostenträger vom Persistenzlayer vorgeladen.");
	}

	public LocalDate getMaxDate() {return maxDate;}
	public void setMaxDate(LocalDate maxDate) {this.maxDate = maxDate;}
	public List<Mitarbeiter> getMitarbeiter() {return mitarbeiter;}
	public List<LogaDatensatz> getLogaDaten() {return logaDaten;}
	public List<Kostenstelle> getKostenStellen() {return kostenStellen;}
	public List<Kostentraeger> getKostenTraeger() {return kostenTraeger;}
	
	
}
