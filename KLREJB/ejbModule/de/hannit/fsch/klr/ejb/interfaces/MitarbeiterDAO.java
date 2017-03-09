package de.hannit.fsch.klr.ejb.interfaces;

import java.util.List;

import de.hannit.fsch.klr.persistence.entities.Mitarbeiter;

public interface MitarbeiterDAO 
{
public Mitarbeiter create(Mitarbeiter toCreate);
public Mitarbeiter update(Mitarbeiter toUpdate);
public Mitarbeiter getMitarbeiter(int personalNR);
public void delete(int personalNR);
public List<Mitarbeiter> getAllMitarbeiter();
}
