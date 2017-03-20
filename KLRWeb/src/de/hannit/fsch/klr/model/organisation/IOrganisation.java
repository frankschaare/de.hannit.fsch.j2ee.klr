/**
 * 
 */
package de.hannit.fsch.klr.model.organisation;

import java.util.TreeMap;

import de.hannit.fsch.klr.model.kostenrechnung.KostenStelle;
import de.hannit.fsch.klr.model.kostenrechnung.KostenTraeger;
import de.hannit.fsch.klr.model.mitarbeiter.Vorstand;
import de.hannit.fsch.klr.model.team.Team;
import de.hannit.fsch.klr.web.beans.MitarbeiterMBean;


/**
 * @author fsch
 *
 */
public interface IOrganisation
{
public String getName();
public void setMitarbeiter(TreeMap<Integer, MitarbeiterMBean> mitarbeiter);
public TreeMap<String, MitarbeiterMBean> getMitarbeiterNachName();
public TreeMap<Integer, MitarbeiterMBean> getMitarbeiterNachPNR();
public TreeMap<Integer, Team> getTeams();
public void setKostenstellen(TreeMap<Integer, KostenStelle> kostenstellen);
public void setKostentraeger(TreeMap<Integer, KostenTraeger> kostentraeger);
public TreeMap<Integer, KostenTraeger> getKostentraeger();
public TreeMap<Integer, KostenStelle> getKostenStellen();
public Vorstand getVorstand();
}
