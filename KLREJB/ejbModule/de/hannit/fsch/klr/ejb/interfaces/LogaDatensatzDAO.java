package de.hannit.fsch.klr.ejb.interfaces;

import java.time.LocalDate;
import java.util.List;

import de.hannit.fsch.klr.persistence.entities.LogaDatensatz;

public interface LogaDatensatzDAO 
{
public LogaDatensatz create(LogaDatensatz toCreate);
public LogaDatensatz update(LogaDatensatz toUpdate);
public LogaDatensatz getLogaDatensatz(int id);
public void delete(int id);
public List<LogaDatensatz> getAll();
public List<LogaDatensatz> findByDate(LocalDate berichtsMonat);
}
