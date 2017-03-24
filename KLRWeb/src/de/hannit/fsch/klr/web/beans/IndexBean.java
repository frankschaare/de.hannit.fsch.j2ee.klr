package de.hannit.fsch.klr.web.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.ListDataModel;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import de.hannit.fsch.klr.model.kostenrechnung.Kostenrechnungsobjekt;
import de.hannit.fsch.klr.model.mitarbeiter.Tarifgruppe;
import de.hannit.fsch.klr.model.organisation.Organisation;
import de.hannit.fsch.klr.model.team.Team;

@ManagedBean
@SessionScoped
public class IndexBean implements Serializable 
{
private static final long serialVersionUID = 1L;
private Organisation hannit = null;
private ListDataModel<Kostenrechnungsobjekt> gesamt = null;
private ListDataModel<Kostenrechnungsobjekt> gesamtKTR = null;
private ListDataModel<Kostenrechnungsobjekt> gesamtKST = null;
private ListDataModel<Tarifgruppe> tarifgruppenListe = null;
private TreeNode root;
private TreeNode treeName;
private TreeNode treeTeams;

	/**
	 * Die Organisation wird hier initiert. Dadurch werden:
	 * - Alle Mitarbeiter-Entitäten gewrappt
	 * - Alle Wrapper mit dem ermittelten MaxDate initiert, d.h.
	 * - sie haben dann die AZS aus MaxDate in azvMonat
	 */
	public IndexBean() 
	{
	hannit = new Organisation();
	
	gesamt = new ListDataModel<Kostenrechnungsobjekt>(new ArrayList<Kostenrechnungsobjekt>(hannit.getmSumme().getGesamtKosten().values()));
	gesamtKTR = new ListDataModel<Kostenrechnungsobjekt>(new ArrayList<Kostenrechnungsobjekt>(hannit.getmSumme().getGesamtKostentraeger().values()));
	gesamtKST = new ListDataModel<Kostenrechnungsobjekt>(new ArrayList<Kostenrechnungsobjekt>(hannit.getmSumme().getGesamtKostenstellen().values()));
	tarifgruppenListe = new ListDataModel<Tarifgruppe>(new ArrayList<Tarifgruppe>(hannit.getTarifgruppen().getTarifGruppen().values()));

	setTree();
	}
	
	@SuppressWarnings("unused")
	private void setTree() 
	{
    root = new DefaultTreeNode("Root", null);
    treeName = new DefaultTreeNode("Root", null);
    treeTeams = new DefaultTreeNode("Root", null);

		TreeNode tNode = null;
       	TreeNode mNode = null;
    	TreeNode azvNode = null;
		for (MitarbeiterMBean m : hannit.getMitarbeiterNachPNR().values())
		{
		mNode  = new DefaultTreeNode(m.getTyp(), m, root);
			for (ArbeitszeitanteilMBean	azv : m.getAzvMonat()) 
			{
			azvNode = new DefaultTreeNode("AZV", azv, mNode);	
			}
			
		}
		
    	mNode = null;
    	azvNode = null;
		for (MitarbeiterMBean m : hannit.getMitarbeiterNachName().values())
		{
		mNode  = new DefaultTreeNode(m.getTyp(), m, treeName);
			for (ArbeitszeitanteilMBean	azv : m.getAzvMonat()) 
			{
			azvNode = new DefaultTreeNode("AZV", azv, mNode);	
			}
			
		}
		
    	mNode = null;
    	azvNode = null;
		for (Team t : hannit.getTeams().values())
		{
		tNode  = new DefaultTreeNode("Team", t, treeTeams);
			for (MitarbeiterMBean m : t.getTeamMitglieder().values())
			{
			mNode  = new DefaultTreeNode(m.getTyp(), m, tNode);
				for (ArbeitszeitanteilMBean	azv : m.getAzvMonat()) 
				{
				azvNode = new DefaultTreeNode("AZV", azv, mNode);	
				}
			}
		}		
	}
	
	public ListDataModel<Tarifgruppe> getTarifgruppenRows() 
	{
	return tarifgruppenListe;	
	}
	
	public ListDataModel<Kostenrechnungsobjekt> getMonatsGesamtSummen() 
	{
	return gesamt;		
	}
	
	public ListDataModel<Kostenrechnungsobjekt> getGesamtSummenRowsKST() 
	{
	return gesamtKST;	
	}
	
	public ListDataModel<Kostenrechnungsobjekt> getGesamtSummenRowsKTR() 
	{
	return gesamtKTR;	
	}		
    public TreeNode getRoot() {return root;}	
    public TreeNode getTreeName() {return treeName;}	
    public TreeNode getTreeTeams() {return treeTeams;}
}
