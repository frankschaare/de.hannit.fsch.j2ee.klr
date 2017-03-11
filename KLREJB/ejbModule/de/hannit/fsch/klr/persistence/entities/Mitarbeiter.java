package de.hannit.fsch.klr.persistence.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import de.hannit.fsch.klr.persistence.enums.Teams;
import de.hannit.fsch.klr.persistence.queries.SQL;

@Entity
@NamedQuery(name=SQL.NAMEDQUERY_MITARBEITER_GETALL, query=SQL.NAMEDQUERY_MITARBEITER_GETALL_SQL)
public class Mitarbeiter implements Serializable
{
private static final long serialVersionUID = 1L;

@Id
@NotNull
private int personalNR;
/**
 * Definiert optimistische Sperrung des Datensatzes
 */
@Version
private Timestamp lastChanged;
private String vorname;
@NotNull
@Size(min=1)
private String nachname;
private String username;

@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
@JoinColumn(name="mitarbeiterPNR")
private List<Arbeitszeitanteil> arbeitszeitAnteile = new ArrayList<Arbeitszeitanteil>();

	public Mitarbeiter() 
	{
	}

	public int getPersonalNR() {
		return personalNR;
	}

	public void setPersonalNR(int personalNR) {
		this.personalNR = personalNR;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorName) {
		this.vorname = vorName;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachName) {
		this.nachname = nachName;
	}

	
	public List<Arbeitszeitanteil> getArbeitszeitAnteile() {
		return arbeitszeitAnteile;
	}

	public void setArbeitszeitAnteile(List<Arbeitszeitanteil> arbeitszeitAnteile) {
		this.arbeitszeitAnteile = arbeitszeitAnteile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
