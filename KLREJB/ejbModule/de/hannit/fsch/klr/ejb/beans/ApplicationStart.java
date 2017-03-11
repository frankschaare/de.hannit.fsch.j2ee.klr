package de.hannit.fsch.klr.ejb.beans;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import de.hannit.fsch.klr.ejb.interfaces.MitarbeiterDAO;
import de.hannit.fsch.klr.persistence.entities.Arbeitszeitanteil;
import de.hannit.fsch.klr.persistence.entities.Mitarbeiter;
import de.hannit.fsch.klr.persistence.enums.Teams;

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
@Inject
private JMSBroker broker;

	@PostConstruct
	private void init()
	{
		if (mitarbeiterDAO.getAllMitarbeiter().size() == 0) 
		{
/*		Mitarbeiter m = new Mitarbeiter();
		m.setVorname("Horst");
		m.setNachname("Brack");
		m.setPersonalNR(123456);
		m.setTeam(Teams.Team1);
		m.setUsername("HBrack");
		
		ArrayList<Arbeitszeitanteil> azvListe = new ArrayList<>();
		
		Arbeitszeitanteil azv = new Arbeitszeitanteil();
		azv.setBerichtsmonat(LocalDate.now());
		azv.setMitarbeiterPNR(m.getPersonalNR());
		azv.setKostenstelle("1010");
		azv.setProzentanteil(50);
		azvListe.add(azv);
		
		azv = new Arbeitszeitanteil();
		azv.setBerichtsmonat(LocalDate.now());
		azv.setMitarbeiterPNR(m.getPersonalNR());
		azv.setKostenstelle("1030");
		azv.setProzentanteil(50);
		azvListe.add(azv);
		
		m.setArbeitszeitAnteile(azvListe);
		
		mitarbeiterDAO.create(m);

		broker.send(m);*/
		
		}
	}
}
