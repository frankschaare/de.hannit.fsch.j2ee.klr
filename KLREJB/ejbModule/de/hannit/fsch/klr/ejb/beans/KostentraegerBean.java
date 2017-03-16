package de.hannit.fsch.klr.ejb.beans;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.hannit.fsch.klr.ejb.interfaces.KostentraegerDAO;
import de.hannit.fsch.klr.persistence.entities.Kostentraeger;
import de.hannit.fsch.klr.persistence.queries.SQL;

@Stateless
@Remote(KostentraegerDAO.class)
public class KostentraegerBean implements KostentraegerDAO 
{
/*
 * Persistence Manager injezieren	
 */
@PersistenceContext	
private EntityManager em;

	public KostentraegerBean() 
	{
	
	}

	@Override
	public Kostentraeger create(Kostentraeger toCreate) 
	{
	em.persist(toCreate);	
	return toCreate;
	}

	@Override
	public Kostentraeger update(Kostentraeger toUpdate) 
	{
	return em.merge(toUpdate);
	}

	@Override
	public Kostentraeger getKostentraeger(String kostentraeger) 
	{
	return em.find(Kostentraeger.class, kostentraeger);
	}

	@Override
	public void delete(String kostentraeger) 
	{
	em.remove(getKostentraeger(kostentraeger));	
	}

	@Override
	public List<Kostentraeger> getAll() 
	{
	return em.createNamedQuery(SQL.NAMEDQUERY_KOSTENTRAEGER_GETALL, Kostentraeger.class).getResultList();
	}


}
