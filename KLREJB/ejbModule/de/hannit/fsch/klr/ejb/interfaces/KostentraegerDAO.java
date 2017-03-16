package de.hannit.fsch.klr.ejb.interfaces;

import java.util.List;

import de.hannit.fsch.klr.persistence.entities.Kostentraeger;

public interface KostentraegerDAO 
{
public Kostentraeger create(Kostentraeger toCreate);
public Kostentraeger update(Kostentraeger toUpdate);
public Kostentraeger getKostentraeger(String kostentraeger);
public void delete(String kostentraeger);
public List<Kostentraeger> getAll();
}
