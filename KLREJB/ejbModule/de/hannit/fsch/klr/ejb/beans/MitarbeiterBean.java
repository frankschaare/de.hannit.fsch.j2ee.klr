package de.hannit.fsch.klr.ejb.beans;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.hannit.fsch.klr.ejb.interfaces.MitarbeiterDAO;
import de.hannit.fsch.klr.persistence.entities.Mitarbeiter;
import de.hannit.fsch.klr.persistence.queries.SQL;

@Stateless
@Remote(MitarbeiterDAO.class)
public class MitarbeiterBean implements MitarbeiterDAO 
{
/*
 * Persistence Manager injezieren	
 */
@PersistenceContext	
private EntityManager em;

	public MitarbeiterBean() 
	{
	
	}

	@Override
	public Mitarbeiter create(Mitarbeiter toCreate) 
	{
	em.persist(toCreate);	
	return toCreate;
	}

	@Override
	public Mitarbeiter update(Mitarbeiter toUpdate) 
	{
	return em.merge(toUpdate);
	}

	@Override
	public Mitarbeiter getMitarbeiter(int personalNR) 
	{
	return em.find(Mitarbeiter.class, personalNR);
	}

	@Override
	public void delete(int personalNR) 
	{
	em.remove(getMitarbeiter(personalNR));
	}

	@Override
	public List<Mitarbeiter> getAllMitarbeiter() 
	{
	return em.createNamedQuery(SQL.NAMEDQUERY_MITARBEITER_GETALL, Mitarbeiter.class).getResultList();
	}

}
