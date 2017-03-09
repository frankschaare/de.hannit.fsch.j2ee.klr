package de.hannit.fsch.klr.persistence.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
public class Arbeitszeitanteil implements Serializable 
{
private static final long serialVersionUID = 1L;

@Id
@GeneratedValue (strategy=GenerationType.IDENTITY)
private long id;
@Version
private Timestamp lastChanged;
@NotNull
private int mitarbeiterPNR;
private String team;
@NotNull
private LocalDate berichtsmonat;
private String kostenstelle;
private String kostentraeger;
@NotNull
private int Prozentanteil;

	public Arbeitszeitanteil() 
	{

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getMitarbeiterPNR() {
		return mitarbeiterPNR;
	}

	public void setMitarbeiterPNR(int mitarbeiterPNR) {
		this.mitarbeiterPNR = mitarbeiterPNR;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public LocalDate getBerichtsmonat() {
		return berichtsmonat;
	}

	public void setBerichtsmonat(LocalDate berichtsmonat) {
		this.berichtsmonat = berichtsmonat;
	}

	public String getKostenstelle() {
		return kostenstelle;
	}

	public void setKostenstelle(String kostenstelle) {
		this.kostenstelle = kostenstelle;
	}

	public String getKostentraeger() {
		return kostentraeger;
	}

	public void setKostentraeger(String kostentraeger) {
		this.kostentraeger = kostentraeger;
	}

	public int getProzentanteil() {
		return Prozentanteil;
	}

	public void setProzentanteil(int prozentanteil) {
		Prozentanteil = prozentanteil;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
