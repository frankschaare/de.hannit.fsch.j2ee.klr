package de.hannit.fsch.klr.persistence.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import de.hannit.fsch.klr.persistence.queries.SQL;

@Entity
@NamedQueries
({
@NamedQuery(name=SQL.NAMEDQUERY_LOGA_GETALL, query=SQL.NAMEDQUERY_LOGA_GETALL_SQL),
@NamedQuery(name=SQL.NAMEDQUERY_LOGA_FINDBYDATE, query=SQL.NAMEDQUERY_LOGA_FINDBYDATE_SQL)
})
public class LogaDatensatz implements Serializable
{
private static final long serialVersionUID = 1L;
@Id
@GeneratedValue (strategy=GenerationType.IDENTITY)
private long id;
@Version
private Timestamp lastChanged;
@NotNull
private int mitarbeiterPNR;
@NotNull
private LocalDate berichtsmonat;
@NotNull
private BigDecimal brutto;
@NotNull
@Size(min=1, max=50)
private String tarifgruppe;
private int tarifstufe;
@NotNull
private float stellenanteil;

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
	public LocalDate getBerichtsmonat() {
		return berichtsmonat;
	}
	public void setBerichtsmonat(LocalDate berichtsmonat) {
		this.berichtsmonat = berichtsmonat;
	}
	public BigDecimal getBrutto() {
		return brutto;
	}
	public void setBrutto(BigDecimal brutto) {
		this.brutto = brutto;
	}
	public String getTarifgruppe() {
		return tarifgruppe;
	}
	public void setTarifgruppe(String tarifgruppe) {
		this.tarifgruppe = tarifgruppe;
	}
	public int getTarifstufe() {
		return tarifstufe;
	}
	public void setTarifstufe(int tarifstufe) {
		this.tarifstufe = tarifstufe;
	}
	public float getStellenanteil() {
		return stellenanteil;
	}
	public void setStellenanteil(float stellenanteil) {
		this.stellenanteil = stellenanteil;
	}



}
