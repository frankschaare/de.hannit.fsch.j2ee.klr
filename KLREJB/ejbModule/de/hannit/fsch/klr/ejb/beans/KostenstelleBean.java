package de.hannit.fsch.klr.ejb.beans;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.hannit.fsch.klr.ejb.interfaces.KostenstelleDAO;
import de.hannit.fsch.klr.persistence.entities.Kostenstelle;
import de.hannit.fsch.klr.persistence.queries.SQL;

@Stateless
@Remote(KostenstelleDAO.class)
public class KostenstelleBean implements KostenstelleDAO 
{
/*
 * Persistence Manager injezieren	
 */
@PersistenceContext	
private EntityManager em;

	public KostenstelleBean() 
	{
	
	}

	@Override
	public Kostenstelle create(Kostenstelle toCreate) 
	{
	em.persist(toCreate);	
	return toCreate;
	}

	@Override
	public Kostenstelle update(Kostenstelle toUpdate) 
	{
	return em.merge(toUpdate);
	}

	@Override
	public Kostenstelle getKostenstelle(String kostenstelle) 
	{
	return em.find(Kostenstelle.class, kostenstelle);
	}

	@Override
	public void delete(String kostenstelle) 
	{
	em.remove(getKostenstelle(kostenstelle));	
	}

	@Override
	public List<Kostenstelle> getAll() 
	{
	return em.createNamedQuery(SQL.NAMEDQUERY_KOSTENSTELLE_GETALL, Kostenstelle.class).getResultList();
	}



}
