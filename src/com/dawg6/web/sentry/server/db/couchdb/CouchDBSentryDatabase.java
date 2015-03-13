package com.dawg6.web.sentry.server.db.couchdb;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;
import org.lightcouch.DesignDocument;
import org.lightcouch.NoDocumentException;
import org.lightcouch.Response;
import org.lightcouch.View;

import com.dawg6.web.sentry.server.SentryServiceImpl;
import com.dawg6.web.sentry.server.util.SentryProperties;
import com.dawg6.web.sentry.shared.calculator.ActiveSkill;
import com.dawg6.web.sentry.shared.calculator.Build;
import com.dawg6.web.sentry.shared.calculator.CharacterData;
import com.dawg6.web.sentry.shared.calculator.ProfileHelper;
import com.dawg6.web.sentry.shared.calculator.Rune;
import com.dawg6.web.sentry.shared.calculator.SkillAndRune;
import com.dawg6.web.sentry.shared.calculator.d3api.HeroProfile;
import com.dawg6.web.sentry.shared.calculator.d3api.Realm;
import com.dawg6.web.sentry.shared.calculator.stats.DBStatistics;
import com.dawg6.web.sentry.shared.calculator.stats.DocumentBase;
import com.dawg6.web.sentry.shared.calculator.stats.DpsTableEntry;
import com.dawg6.web.sentry.shared.calculator.stats.StatCategory;
import com.dawg6.web.sentry.shared.calculator.stats.Statistics;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class CouchDBSentryDatabase {

	protected boolean LOGGING = false;

	private CouchDbClient dbClient;

	public static final String DB_NAME = "sentry";

	private static final String PARAM_PREFIX = "config.";
	
	private static final Logger log = Logger
			.getLogger(CouchDBSentryDatabase.class.getName());

	private static CouchDBSentryDatabase instance = null;

	public static synchronized CouchDBSentryDatabase getInstance() {
		if (instance == null)
			instance = new CouchDBSentryDatabase();

		return instance;
	}
	
	private final Object dpsLock = new Object();

	private CouchDBSentryDatabase() {
		try {
			CouchDbProperties props = new CouchDbProperties()
				.setDbName(DB_NAME)
				.setCreateDbIfNotExist(true)
				.setProtocol("http")
				.setHost(SentryProperties.getInstance().getDb())
				.setPort(5984)
				.setMaxConnections(100)
				.setConnectionTimeout(0);
			
			dbClient = new CouchDbClient(props);
			
			DesignDocument designDoc = dbClient.design().getFromDesk(DB_NAME);
			
			dbClient.design().synchronizeWithDb(designDoc); 
		} 
		
		catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public void setLogging(boolean logging) {
		this.LOGGING = logging;
	}
	
	public <T extends DocumentBase> void persist(List<T> objects) {
		List<Response> resp = dbClient.bulk(objects, true);

		for (int i = 0; i < objects.size(); i++) {
			Response r = resp.get(i);
			T obj = objects.get(i);
			obj.setId(r.getId());
			obj.setRevision(r.getRev());
		}
	}
	
	public <T extends DocumentBase> T persist(T object) {
		
		Response resp = null;
		
		if (object.getRevision() == null)
			resp = dbClient.save(object);
		else
			resp = dbClient.update(object);
		
		object.setId(resp.getId());
		object.setRevision(resp.getRev());

		return object;
	}
	
	public <T extends DocumentBase> T viewOne(Class<T> clazz, String viewName, Object... key) {
		List<T> list = view(clazz, viewName, key);
		
		return list.isEmpty() ? null : list.get(0);
	}

	public <T> T reduce(Class<T> clazz, String viewName, Object... key) {
		View view = dbClient.view(DB_NAME + "/" + viewName);
		
		if (key.length > 0) {
			view.key(key);
		}

		List<T> list = view.query(clazz);
		
		return list.isEmpty() ? null : list.get(0);
	}

	public <T extends DocumentBase> List<T> view(Class<T> clazz, String viewName, List<String> keys) {
		View view = dbClient.view(DB_NAME + "/" + viewName)
				.includeDocs(true);
		
		if (keys.size() > 0)
			view.keys(keys);

		return view.query(clazz);
	}
	
	public <T extends DocumentBase> List<T> view(Class<T> clazz, String viewName, Object... key) {
		
		View view = dbClient.view(DB_NAME + "/" + viewName)
				.includeDocs(true);
		
		if (key.length > 0) {
			view.key(key);
		}
		
		return view.query(clazz);
	}
	
	public <T extends DocumentBase> List<T> viewRange(Class<T> clazz, String viewName, Object start, Object end) {
		
		View view = dbClient.view(DB_NAME + "/" + viewName)
				.includeDocs(true);
		
		view.startKey(start);
		view.endKey(end);
		
		return view.query(clazz);
	}

	public <T extends DocumentBase> void truncate(Class<T> clazz) {
		
		try {
			List<T> list = findAll(clazz);

			for (T doc : list) {
				dbClient.remove(doc);
			}
		} catch (Exception e) { 
			log.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	public <T extends DocumentBase> List<T> findAll(Class<T> clazz) {
		
		try {
			String type = clazz.newInstance().getDocumentType();
		
			return view(clazz, "allByType", type);
		} catch (Exception e) { 
			log.log(Level.SEVERE, e.getMessage(), e);
			
			return new Vector<T>();
		}
	}
	
	public <T extends DocumentBase> void delete(List<T> list) {
		for (T obj : list)
			dbClient.remove(obj);
	}

	public <T extends DocumentBase> void delete(T obj) {
		dbClient.remove(obj);
	}

	public <T extends DocumentBase> void delete(Class<T> clazz, Collection<String> idList) {
		
		for (String id : idList)
			delete(clazz, id);
	}

	public <T extends DocumentBase> void delete(Class<T> clazz, String id) {
		T obj = get(clazz, id);
		
		if (obj != null)
			dbClient.remove(obj.getId(), obj.getRevision());
		else
			log.warning("Unable to find doucment with id " + id);
	}
	
	public <T extends DocumentBase> T get(Class<T> clazz, String id) {
		
		try {
			return dbClient.find(clazz, id);
		} catch (NoDocumentException e) {
			return null;
		}
	}

	public String getParameter(String parameter) {

		String name = PARAM_PREFIX + parameter;
		ParameterDocument doc = this.get(ParameterDocument.class, name);
		
		return (doc != null) ? doc.getValue() : null;
	}

	public void putParameter(String parameter, String value) {

		String name = PARAM_PREFIX + parameter;
		ParameterDocument doc = this.get(ParameterDocument.class, name);

		if (doc == null) {
			doc = new ParameterDocument();
			doc.setId(name);
		}
		
		doc.setValue(value);

		this.persist(doc);
	}

	public <T extends DocumentBase> List<String> createIdListFromObjects(List<T> objects) {
		Set<String> list = new HashSet<String>();
		
		for (T t : objects)
			list.add(t.getId());
		
		return new Vector<String>(list);
	}


	protected void updateDpsData(Long since) {

		SentryServiceImpl service = new SentryServiceImpl();
		Long start = (long)0;
		List<DpsTableEntry> list = this.viewRange(DpsTableEntry.class, DpsTableEntry.BY_TIME, start, since);
		int count = 1;
		int num = list.size();
		
		for (DpsTableEntry source : list) {

			System.out.println(count + "/" + num + ": Updating DPS for "
					+ source.getRealm().name() + "/" + source.getBattletag());
			Realm realm = source.getRealm();
			String battletag = source.getBattletag();
			String[] split1 = battletag.split("/");
			String[] split2 = split1[0].split("-");
			String profile = split2[0];
			Integer tag = Integer.parseInt(split2[1]);
			Integer heroId = Integer.parseInt(split1[1]);
			HeroProfile hero = service.getHero(realm, profile, tag, heroId);

			if (hero.code == null) {
				CharacterData data = ProfileHelper.importHero(hero, null);
				data.setRealm(realm);
				data.setProfile(profile);
				data.setTag(tag);
				data.setHero(heroId);
				data.setParagonCC(source.getParagon_cc());
				data.setParagonCHD(source.getParagon_chd());
				data.setParagonCDR(source.getParagon_cdr());
				data.setParagonIAS(source.getParagon_ias());
				data.setParagonHatred(getValue(source.getParagon_hatred(), 0));
				data.setParagonRCR(getValue(source.getParagon_rcr(), 0));
				data.setBp(source.getBp());
				
				DpsTableEntry entry = service.calculateDps(data);
				entry.setId(source.getId());
				entry.setRevision(source.getRevision());
				
				persist(entry);
			} else {
				log.warning("Unable to find hero: " + battletag);
			}

			count++;
		}
	}


	public int getValue(Integer value, int defaultValue) {
		return (value == null) ? defaultValue : value;
	}

	protected void importData(String from, String to) {
//		AmazonDynamoDBClient client = this.getClient();
//
//		DynamoDBMapperConfig.Builder builder = new DynamoDBMapperConfig.Builder();
//		builder.setConsistentReads(ConsistentReads.EVENTUAL);
//		builder.setPaginationLoadingStrategy(PaginationLoadingStrategy.ITERATION_ONLY);
//		builder.setTableNameOverride(TableNameOverride
//				.withTableNameReplacement(from));
//
//		DynamoDBMapperConfig fromMapperConfig = builder.build();
//		DynamoDBMapper fromMapper = new DynamoDBMapper(client, fromMapperConfig);
//
//		builder.setTableNameOverride(TableNameOverride
//				.withTableNameReplacement(to));
//		DynamoDBMapperConfig toMapperConfig = builder.build();
//		DynamoDBMapper toMapper = new DynamoDBMapper(client, toMapperConfig);
//
//		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
//
//		List<DpsTableEntry> fromList = fromMapper.scan(DpsTableEntry.class,
//				scanExpression);
//
//		for (Iterator<DpsTableEntry> iter = fromList.iterator(); iter.hasNext();) {
//			DpsTableEntry source = iter.next();
//
//			System.out.println("Copying " + source.getRealm().name() + "/"
//					+ source.getBattletag());
//
//			toMapper.save(source);
//		}
	}

	public DpsTableEntry getDps(String battletag, Realm realm) {
		try {

			synchronized (dpsLock) {
				JsonObject q = new JsonObject();
				q.addProperty(DpsTableEntry.REALM, realm.name());
				q.addProperty(DpsTableEntry.BATTLETAG, battletag);
				
				return this.viewOne(DpsTableEntry.class, DpsTableEntry.PROFILES, q);
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, "Exception", e);

			return null;
		}

	}

	public void logDps(DpsTableEntry entry) {
		try {
			synchronized (dpsLock) {
				DpsTableEntry existing = this.getDps(entry.getBattletag(), entry.getRealm());
				
				if (existing != null) {
					entry.setId(existing.getId());
					entry.setRevision(existing.getRevision());
				}

				this.persist(entry);
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, "Exception", e);
		}

	}

	public static class DBStat {
		public double single_elite;
		public double single;
		public double multiple_elite;
		public double multiple;
	}

	public static class DBStatHolder {
		public int count;
		public DBStat total;
		public DBStat min;
		public DBStat max;
		public DBStat average;
	}
	
	public static class DBStats {
		public String key;
		public DBStatHolder value;
	}
	
	public DBStats getStatistics(Build build) {
		
		if (build != null) {
			JsonObject key = new JsonObject();
			key.addProperty("Sentry", build.getSentryRune().name());
			
			for (SkillAndRune skr : build.getSkills()) {
				key.addProperty(skr.getSkill().name(), "true");
				key.addProperty(skr.getRune().name(), "true");
			}
			
			log.info("Build = " + key.toString());
			
			return this.reduce(DBStats.class, DpsTableEntry.DPS_SUMMARY, key);
		} else {
			return this.reduce(DBStats.class, DpsTableEntry.DPS_SUMMARY);
		}
	}
	
	public DBStatistics getStatistics(Rune sentryRune, ActiveSkill[] skills,
			Rune[] runes) {
		DBStatistics stats = new DBStatistics();

		Map<ActiveSkill, Rune> skillMap = new HashMap<ActiveSkill, Rune>();

		int skillCount = 0;

		for (int i = 0; i < skills.length; i++) {
			if (skills[i] != null)
				skillCount++;
		}

		synchronized (dpsLock) {

			for (DpsTableEntry e : this.findAll(DpsTableEntry.class)) {
				addStatistics(stats.stats, e);

				Build build = e.getBuild();

				if ((sentryRune == Rune.All_Runes)
						|| (sentryRune == build.getSentryRune())) {

					int match = 0;

					for (int i = 0; i < skills.length; i++) {
						ActiveSkill s1 = skills[i];
						Rune r2 = build.getRune(s1);

						if ((s1 == ActiveSkill.Any) || (r2 != null)) {
							Rune r1 = runes[i];

							if ((r1 == Rune.All_Runes) || (r1 == r2)) {
								match++;
							}
						}
					}

					if ((match == skillCount)
							&& (build.getSkills().size() <= skillCount)) {

						Statistics s = stats.builds.get(build);

						if (s == null) {
							s = new Statistics();
							stats.builds.put(build, s);
						}

						addStatistics(s, e);
					}
				}
			}
		}

		return stats;
	}

	private void addStatistics(Statistics stats, DpsTableEntry e) {

		for (StatCategory c : StatCategory.values()) {
			DpsTableEntry old = stats.max.get(c);
			double value = c.getValue(e);

			if ((old == null) || (value > c.getValue(old))) {
				stats.max.put(c, e);
			}

			Double avg = stats.average.get(c);

			if (avg == null) {
				stats.average.put(c, value);
			} else {
				Double newAverage = ((avg * stats.total) + value)
						/ (stats.total + 1);
				stats.average.put(c, newAverage);
			}
		}

		stats.total++;
	}
	
	public static void main(String[] args) {
		try {
			CouchDBSentryDatabase db = CouchDBSentryDatabase.getInstance();
			long since = System.currentTimeMillis();
			System.out.println("Start Time = " + since);
			
			Build build = new Build();
			build.setSentryRune(Rune.Polar_Station);
			Set<SkillAndRune> skills = new TreeSet<SkillAndRune>();
			skills.add(new SkillAndRune(ActiveSkill.CA, Rune.Maelstrom));
			skills.add(new SkillAndRune(ActiveSkill.EF, Rune.Focus));
			build.setSkills(skills);
			
			DBStats stats = db.getStatistics(build);

			Gson gson = new Gson();
			System.out.println("Stats = " + gson.toJson(stats));
	//		Long start = (long)0;
	//		List<DpsTableEntry> list = db.viewRange(DpsTableEntry.class, DpsTableEntry.BY_TIME, start, since);
	//		
	//		System.out.println("Count = " + list.size());
//			db.updateDpsData(since);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			System.out.println("End Time = " + System.currentTimeMillis());
		}
	}
}
