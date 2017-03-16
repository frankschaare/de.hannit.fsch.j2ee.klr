package de.hannit.fsch.klr.persistence.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import de.hannit.fsch.klr.persistence.queries.SQL;

@Entity
@NamedQuery(name=SQL.NAMEDQUERY_KOSTENSTELLE_GETALL, query=SQL.NAMEDQUERY_KOSTENSTELLE_GETALL_SQL)
public class Kostenstelle implements Serializable 
{
private static final long serialVersionUID = 1L;

@Id
@GeneratedValue (strategy=GenerationType.IDENTITY)
private long id;
@Version
private Timestamp lastChanged;
@NotNull
private String kostenstelle;
private String bezeichnung;
private LocalDate von;
private LocalDate bis;


	public Kostenstelle() 
	{

	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public Timestamp getLastChanged() {
		return lastChanged;
	}


	public void setLastChanged(Timestamp lastChanged) {
		this.lastChanged = lastChanged;
	}


	public String getKostenstelle() {
		return kostenstelle;
	}


	public void setKostenstelle(String kostenstelle) {
		this.kostenstelle = kostenstelle;
	}


	public String getBezeichnung() {
		return bezeichnung;
	}


	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}


	public LocalDate getVon() {
		return von;
	}


	public void setVon(LocalDate von) {
		this.von = von;
	}


	public LocalDate getBis() {
		return bis;
	}


	public void setBis(LocalDate bis) {
		this.bis = bis;
	}


}
