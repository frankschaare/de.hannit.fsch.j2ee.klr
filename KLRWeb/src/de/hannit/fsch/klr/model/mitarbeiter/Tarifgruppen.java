package de.hannit.fsch.klr.model.mitarbeiter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.TreeMap;

import de.hannit.fsch.common.AppConstants;
import de.hannit.fsch.klr.model.Datumsformate;

public class Tarifgruppen 
{
private TreeMap<String, Tarifgruppe> tarifGruppen = new TreeMap<String, Tarifgruppe>();
private BigDecimal summeTarifgruppen = null;
private double summeStellen = 0;
private double summeVollzeitAequivalent = 0;
private int anzahlMitarbeiter = 0;
private LocalDate berichtsMonat = null;

	public Tarifgruppen()
	{
	}
	public TreeMap<String, Tarifgruppe> getTarifGruppen(){return tarifGruppen;}
	public void setTarifGruppen(TreeMap<String, Tarifgruppe> tarifGruppen){this.tarifGruppen = tarifGruppen;}
	
	
	public BigDecimal getSummeTarifgruppen()
	{
	summeTarifgruppen = new BigDecimal(0);
	
		for (Tarifgruppe t : getTarifGruppen().values())
		{
		summeTarifgruppen = summeTarifgruppen.add(t.getSummeTarifgruppe());	
		}
	return summeTarifgruppen;
	}
	
	public String getFormattedSummeTarifgruppen()
	{
	return NumberFormat.getCurrencyInstance().format(getSummeTarifgruppen());
	}	
	
	public double getSummeStellen()
	{
	summeStellen = 0;	
		for (Tarifgruppe t : getTarifGruppen().values())
		{
		summeStellen = summeStellen + t.getSummeStellen();	
		}
	return summeStellen;
	}
	
	public String getFormattedSummeStellen()
	{
	return "Summe Stellen: " + AppConstants.KOMMAZAHL.format((getSummeStellen()));
	}	
	
	public double getSummeVollzeitAequivalent()
	{
		for (Tarifgruppe t : getTarifGruppen().values())
		{
		summeVollzeitAequivalent = summeVollzeitAequivalent + t.getVollzeitAequivalent();	
		}
	return (summeVollzeitAequivalent * summeStellen);
	}
	
	public int getAnzahlMitarbeiter(){return anzahlMitarbeiter;}
	public String getFormattedAnzahlMitarbeiter(){return String.valueOf(getAnzahlMitarbeiter()) + " Mitarbeiter";}
	
	public void setAnzahlMitarbeiter(int anzahlMitarbeiter)	{this.anzahlMitarbeiter = anzahlMitarbeiter;}
	public LocalDate getBerichtsMonat(){	return berichtsMonat;}
	public String getFormattedBerichtsMonat(){return Datumsformate.MONATLANG_JAHR.format(getBerichtsMonat());}
	public void setBerichtsMonat(LocalDate berichtsMonat){this.berichtsMonat = berichtsMonat;}
	
}
