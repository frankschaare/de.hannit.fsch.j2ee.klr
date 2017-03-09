package de.hannit.fsch.klr.ejb.beans;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

import de.hannit.fsch.klr.ejb.interfaces.LogaDatensatzDAO;
import de.hannit.fsch.klr.persistence.entities.LogaDatensatz;
import de.hannit.fsch.klr.persistence.queries.SQL;
import de.hannit.fsch.util.DateUtility;

@Stateless
@Remote(LogaDatensatzDAO.class)
public class LogaDatensatzBean implements LogaDatensatzDAO 
{
@PersistenceContext	
private EntityManager em;

	public LogaDatensatzBean() 
	{
	}

	@Override
	public LogaDatensatz create(LogaDatensatz toCreate) 
	{
	em.persist(toCreate);	
	return toCreate;
	}

	@Override
	public LogaDatensatz update(LogaDatensatz toUpdate) 
	{
	return em.merge(toUpdate);
	}

	@Override
	public LogaDatensatz getLogaDatensatz(int id) 
	{
	return em.find(LogaDatensatz.class, id);
	}

	@Override
	public void delete(int id) 
	{
	em.remove(getLogaDatensatz(id));
	}

	@Override
	public List<LogaDatensatz> getAll() 
	{
	return em.createNamedQuery(SQL.NAMEDQUERY_LOGA_GETALL, LogaDatensatz.class).getResultList();
	}

	@Override
	public List<LogaDatensatz> findByDate(LocalDate berichtsMonat) 
	{
	return em.createNamedQuery(SQL.NAMEDQUERY_LOGA_FINDBYDATE, LogaDatensatz.class).setParameter("berichtsMonat", DateUtility.asDate(berichtsMonat), TemporalType.DATE).getResultList();
	}



}
