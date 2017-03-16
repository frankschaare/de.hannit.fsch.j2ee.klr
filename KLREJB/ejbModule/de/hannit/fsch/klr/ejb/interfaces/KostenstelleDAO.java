package de.hannit.fsch.klr.ejb.interfaces;

import java.util.List;

import de.hannit.fsch.klr.persistence.entities.Kostenstelle;

public interface KostenstelleDAO 
{
public Kostenstelle create(Kostenstelle toCreate);
public Kostenstelle update(Kostenstelle toUpdate);
public Kostenstelle getKostenstelle(String bezeichnung);
public void delete(String kostenstelle);
public List<Kostenstelle> getAll();
}
