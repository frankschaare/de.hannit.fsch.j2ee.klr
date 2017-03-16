package de.hannit.fsch.klr.persistence.queries;

public class SQL 
{
public static final String NAMEDQUERY_MITARBEITER_GETALL = "Mitarbeiter.getAll";
public static final String NAMEDQUERY_MITARBEITER_GETALL_SQL = "SELECT m FROM Mitarbeiter m";

public static final String NAMEDQUERY_LOGA_GETALL = "LogaDatensatz.getAll";
public static final String NAMEDQUERY_LOGA_GETALL_SQL = "SELECT l FROM LogaDatensatz l";
public static final String NAMEDQUERY_LOGA_FINDBYDATE = "LogaDatensatz.findByDate";
public static final String NAMEDQUERY_LOGA_FINDBYDATE_SQL = "SELECT l FROM LogaDatensatz l WHERE l.berichtsmonat = :berichtsmonat";

public static final String NAMEDQUERY_KOSTENSTELLE_GETALL = "Kostenstelle.getAll";
public static final String NAMEDQUERY_KOSTENSTELLE_GETALL_SQL = "SELECT k FROM Kostenstelle k";
public static final String NAMEDQUERY_KOSTENTRAEGER_GETALL = "Kostentraeger.getAll";
public static final String NAMEDQUERY_KOSTENTRAEGER_GETALL_SQL = "SELECT k FROM Kostentraeger k";
}
