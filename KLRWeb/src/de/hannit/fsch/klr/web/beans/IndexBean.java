package de.hannit.fsch.klr.web.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.hannit.fsch.klr.model.organisation.Organisation;

@ManagedBean
@SessionScoped
public class IndexBean implements Serializable 
{
private static final long serialVersionUID = 1L;
private Organisation hannit = null;


	/**
	 * Die Organisation wird hier initiert. Dadurch werden:
	 * - Alle Mitarbeiter-Entitäten gewrappt
	 * - Alle Wrapper mit dem ermittelten MaxDate initiert, d.h.
	 * - sie haben dann die AZS aus MaxDate in azvMonat
	 */
	public IndexBean() 
	{
	hannit = new Organisation();

	}

}
