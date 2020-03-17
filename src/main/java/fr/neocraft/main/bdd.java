package fr.neocraft.main;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import fr.neocraft.main.util.BDDConnection;
import fr.neocraft.main.util.CRASH;
import static fr.neocraft.main.main.l;
import net.querz.nbt.CompoundTag;
import net.querz.nbt.NBTUtil;



public class bdd {
	
	public boolean IsDebug = true;
	public boolean IsOpen = false;
	public boolean IsClass = false;
	private BDDConnection DataConnexion;
	public Connection connexion;
	private  Map<Integer, Statement> s = new HashMap<Integer, Statement>();
	private int Id = 0;
	
	
	
	
	public bdd()
	{
		IsOpen = false;
		IsClass = false;
		try {
			Class.forName( "com.mysql.jdbc.Driver" );
			System.out.println("Openbdd CLASS LOAD");
			IsClass = true;
		}
		catch ( ClassNotFoundException e ) {
			this.IsClass = false;
			erreur("bdd", e);
	   }
	}
	
	public bdd(boolean Null)
	{ }

	public void Openbdd()
	{
		
		File f = new File("bdd.dat");
		if(f.exists())
		{
			try {
				CompoundTag tag = (CompoundTag) NBTUtil.readTag(f).clone();
				BDDConnection data = new BDDConnection(tag.getString("url"), tag.getString("user"), tag.getString("mdp"));
				if(DataConnexion == null || !data.equals(DataConnexion))
				{
					if(IsOpen)
					{
						this.Closebdd();
					}
					DataConnexion = data;
				
					System.out.println("TRY BDD:");
					

				    this.connexion = DriverManager.getConnection(DataConnexion.getURL(),DataConnexion.getUser(),DataConnexion.getMDP() );
				    this.IsOpen = true;
				    System.out.println("Openbdd BDD OPEN");
				}
			} catch(Exception e) {
				CRASH.Push(e);
			}
		} else {
			 this.erreur("Openbdd", "No bdd.dat found");
		}
		
		
	}
	
	public void Closebdd()
	{
		try {
			Iterator it = this.s.values().iterator();
			while(it.hasNext())
			{
				((Statement)it.next()).close();
			}
			this.s = new HashMap<Integer, Statement>();
			DataConnexion = null;
			this.connexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 this.erreur("Closebdd", "success");
		 this.IsOpen = false;
	}

	
	public long getId() {
		return this.Id;
	}
	
	public Map getStatement()
	{
		return this.s;
	}
	
	public int getNbStatementActive()
	{
		
		if(this.s.isEmpty()) {
			return 0;
		} else {
			return this.s.values().size();
		}
	}
	
	
	public int GetFreeId() {
		try {
			if(this.Id >= Integer.MAX_VALUE-10)
			{
				Iterator it = this.s.values().iterator();
				while(it.hasNext())
				{
					((Statement)it.next()).close();
				}
				this.Id = 0;
			}
			this.Id++;
			if(this.IsDebug)
			{
				System.out.println("[BDD DEBUG] Create id: "+this.Id);
			}
			if(this.connexion.isClosed())
			{
				Openbdd();
			}
			
			this.s.put(this.Id, this.connexion.createStatement());
			return this.Id;
		} catch (SQLException e) {
			this.erreur("GetFreeId("+this.Id+")", e);
			CloseFreeId(this.Id);
			return -1;
		}catch(NullPointerException e)
		{
			this.erreur("GetFreeId("+this.Id+")", e);
			return -1;
		}
	}
	
	public void CloseFreeId(int i) {
		Statement o = this.s.get(i);
		if(this.IsDebug)
		{
			System.out.println("[BDD DEBUG] delete id: "+i);
		}
		if(o != null)
		{
			try {
				o.close();
				if(this.s.remove(i, o)) {
				}
			} catch(SQLException e)
			{
				this.erreur("CloseFreeId("+i+")", e);
			}
			catch(NullPointerException e)
			{
				this.erreur("CloseFreeId("+i+")", e);
			}
		}
		else
		{
			this.erreur("CloseFreeId("+i+")", "NULL");
		}
	}
	
	public boolean Exist(String query)
	{
		int id = GetFreeId();
		if(this.s.containsKey(id))
		{
			ResultSet r = query(query, id);
			if(this.IsDebug)
			{
				System.out.println("[BDD DEBUG] EXIST: "+query);
			}
			if(r != null)
			{
				try {
					while(r.next())
					{
						CloseFreeId(id);
						return true;
					}
				} catch(Exception e)
				{
					this.erreur("Exist", e, query);
				}
			}else {
				this.erreur("Exist", "NULL", query);
			}
		}
		else
		{
			this.erreur("Exist", "key "+id+" null", query);
		}
		CloseFreeId(id);
		return false;
	}
	
	public ResultSet query(String query, int id)
	{
		ResultSet r = null;
		if(this.s.containsKey(id))
		{
			if(this.IsDebug)
			{
				System.out.println("[BDD DEBUG] QUERY: "+query);
			}
			try {
				r = this.s.get(id).executeQuery(query);
			} catch (SQLException e) {
				this.erreur("query", e, query);
			}catch(NullPointerException e)
			{
				this.erreur("query", e, query);
			}
		}
		else
		{
			this.erreur("query", "key "+id+" null", query);
		}
		return r;
	}

	
	public boolean update(String query)
	{
		int id = GetFreeId();
		if(this.s.containsKey(id))
		{
			if(this.IsDebug)
			{
				System.out.println("[BDD DEBUG] UPDATE: "+query);
			}
			try {
				this.s.get(id).executeUpdate(query);
				CloseFreeId(id);
				return true;
			} catch (SQLException e) {
				this.erreur("update", e, query);
			}
		}
		else
		{
			this.erreur("update", "key "+id+" null", query);
		}
		CloseFreeId(id);
		return false;
	}

	
	public boolean checkIfIsValidQuery(String query) {
		ResultSet r = null;
		int id=GetFreeId();
		if(this.s.containsKey(id))
		{
			if(this.IsDebug)
			{
				System.out.println("[BDD DEBUG] checkIfIsValidQuery: "+query);
			}
			try {
				r = this.s.get(id).executeQuery(query);
				while(r.next())
				{
					return true;
				}
				return true;
			} catch (SQLException e) {
				return false;
			}catch(NullPointerException e)
			{
				return false;
			}
		}
		else
		{
			this.erreur("query", "key "+id+" null", query);
		}
		return false;
	}
	
	public boolean execute(String query)
	{
		int id = GetFreeId();
		if(this.s.containsKey(id))
		{
			if(this.IsDebug)
			{
				System.out.println("[BDD DEBUG] EXECUTE: "+query);
			}
			try {
				this.s.get(id).execute(query);
				CloseFreeId(id);
				return true;
			} catch (SQLException e) {
				this.erreur("query", e, query);
		
			}
		}
		else
		{
			this.erreur("execute", "key "+id+" null", query);

		}
		CloseFreeId(id);
		return false;
	}

	public void erreur(String f, Exception e, String stat)
	{
		l.error("ERREUR LORS DE L'EXECUTION '"+f +"'");
		l.error(" INFO: ");
		e.printStackTrace();
		l.error(" DATA: " + stat);
		CRASH.Push(e);
	}

	public void erreur(String f, Exception e) {
		l.error("ERREUR LORS DE L'EXECUTION '"+f +"'");
		l.error(" INFO: ");
		e.printStackTrace();
		CRASH.Push(e);
	}
	public void erreur(String f, String e) {
		l.error("ERREUR LORS DE L'EXECUTION '"+f +"'");
		l.error(" INFO: "+e);
	}

	public void erreur(String f, String e, String stat)
	{
		l.error("ERREUR LORS DE L'EXECUTION '"+f +"'");
		l.error(" INFO: "+e);
		l.error(" DATA: " + stat);
	}

	public ArrayList<String> getAllTable() throws Exception {
		ArrayList<String> ar = new ArrayList<String>();
		DatabaseMetaData md = connexion.getMetaData();
		ResultSet rs = md.getTables(null, null, "%", null);
		while (rs.next()) {
			ar.add(rs.getString(3));
		}
		return ar;
	}
	
	public ArrayList<String> getAllColumn(String table) throws Exception {
		ArrayList<String> ar = new ArrayList<String>();
		String query = "SELECT * FROM `"+table+"`";
		int id = GetFreeId();
		ResultSet r = query(query, id);
		if(r != null)
		{
			ResultSetMetaData rsMetaData;
			rsMetaData = r.getMetaData();
			int numberOfColumns = rsMetaData.getColumnCount();
			for (int i = 1; i < numberOfColumns + 1; i++) {
				String columnName = rsMetaData.getColumnName(i);
				ar.add(columnName);
			}
		}
		CloseFreeId(id);
		return ar;
	}

	public int NumberOfLigne(String Table, String condition)
	{
		int id = GetFreeId();
		ResultSet r = query("SELECT count(*) AS nbLignes FROM `"+Table+"` WHERE "+condition, id);
		int id2 = 0;
		if(r != null)
		{
			try {
				while(r.next())
				{
					id2 =  r.getInt("nbLignes");
				}
			} catch(Exception e)
			{
				this.erreur("compterligne", e,"SELECT count(*) AS nbLignes FROM `"+Table+"` WHERE "+condition);
			}
		}
		else
		{
			this.erreur("compterligne", "NULL","SELECT count(*) AS nbLignes FROM `"+Table+"` WHERE "+condition);
		}
		CloseFreeId(id);
		return id2;
	}
	public int NumberOfLigne(String Table)
	{
		return NumberOfLigne(Table, "1");
	}
	
	
	/*
	CREATE TABLE IF NOT EXISTS `MMO-Player` (
	  `id` int(11) NOT NULL AUTO_INCREMENT,
	  `Life` int DEFAULT 100,
	  `Regen` int DEFAULT 5,
	  `Damage` double DEFAULT 5,
	  `Speed` double DEFAULT 1,
	  `Classe` text NULL,
	  `Race` text NULL,
	  `Money` double DEFAULT 100,
	  `Reputation1` double DEFAULT 50,
	  `Reputation2` double DEFAULT 50,
	  `MF` int DEFAULT 0,
	  `HouseIndex` int DEFAULT -1,
	  `Pseudo` text NOT NULL,
	  `HouseBy` int DEFAULT 0,
	  `posMap` text default null,
	  PRIMARY KEY (`id`)
	) ENGINE=MyISAM DEFAULT CHARSET=latin1;

	
	 */
	
	/**
	 * id
	 * Life
	 * Regen
	 * Damage
	 * Speed
	 * Classe
	 * Race
	 * Money
	 * Reputation1
	 * Reputation2
	 * MF
	 * HouseIndex
	 * Pseudo
	 * HouseBy
	 * posMap
	 */
	public String getStringPlayer() {
		return "`MMO-Player`";
	}
	/*
		CREATE TABLE IF NOT EXISTS `MMO-Zone` (
	  `id` int(11) NOT NULL AUTO_INCREMENT,
	  `XPos` int NOT NULL,
	  `ZPos` int NOT NULL,
	  `Name` text NOT NULL,
	  `SecName` text NOT NULL,
	  `lvl` int NOT NULL DEFAULT 0,
	  `pvp` int NOT NULL DEFAULT 0,
	  PRIMARY KEY (`id`)
	) ENGINE=MyISAM DEFAULT CHARSET=latin1;
	 */
	
	/**
	 * id
	 * XPos
	 * ZPos
	 * Name
	 * SecName
	 * lvl
	 * pvp
	 */
	public String getStringZone() {
		return "`MMO-Zone`";
	}
	
	
	public boolean updateProtocole(String table, Object[] obj, String cond)
	{
		return UpdateProtocole.update(this, table, obj, cond);
	}
	
	private UpdateProtocoleExtended UpdateProtocole = new UpdateProtocoleExtended();
	//extended
	public class UpdateProtocoleExtended {
		public boolean update(bdd b, String table, Object[] obj, String cond)
		{
			if(obj.length % 2 == 0)
			{
			
				String fu = "";
				for(int i = 0; i < obj.length; i++)
				{
					fu+="`"+String.valueOf(obj[i])+"`=";
					i++;
					fu+= (isString(obj[i]) ? String.valueOf("'"+obj[i]+"'") :obj[i]) + ((i < obj.length-1) ? ",": "");
				}
				return b.update("UPDATE "+table+" SET "+fu+" "+cond);
			} else {
				String fu = "";
				for(int i = 0; i < obj.length; i++)
				{
					fu += obj[i]+", ";
				}
				b.erreur("UpdateProtocoleExtended.update", "Object are not properly config: "+table+", "+fu);
				return false;
			}
		}
		
		private boolean isString(Object o)
		{
			return o instanceof String;
		}
	}
}
