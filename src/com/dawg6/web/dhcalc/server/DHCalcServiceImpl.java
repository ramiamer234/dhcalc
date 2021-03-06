/*******************************************************************************
 * Copyright (c) 2014, 2015 Scott Clarke (scott@dawg6.com).
 *
 * This file is part of Dawg6's Demon Hunter DPS Calculator.
 *
 * Dawg6's Demon Hunter DPS Calculator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dawg6's Demon Hunter DPS Calculator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.dawg6.web.dhcalc.server;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dawg6.d3api.shared.ApiData;
import com.dawg6.d3api.shared.CareerProfile;
import com.dawg6.d3api.shared.Follower;
import com.dawg6.d3api.shared.HeroProfile;
import com.dawg6.d3api.shared.ItemInformation;
import com.dawg6.d3api.shared.Leaderboard;
import com.dawg6.d3api.shared.Realm;
import com.dawg6.d3api.shared.SeasonIndex;
import com.dawg6.web.dhcalc.client.DHCalcService;
import com.dawg6.web.dhcalc.server.db.couchdb.CouchDBDHCalcDatabase;
import com.dawg6.web.dhcalc.server.db.couchdb.CouchDBDHCalcParameters;
import com.dawg6.web.dhcalc.server.db.couchdb.NewsDocument;
import com.dawg6.web.dhcalc.shared.calculator.ExportData;
import com.dawg6.web.dhcalc.shared.calculator.FormData;
import com.dawg6.web.dhcalc.shared.calculator.NewsItem;
import com.dawg6.web.dhcalc.shared.calculator.Util;
import com.dawg6.web.dhcalc.shared.calculator.Version;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DHCalcServiceImpl extends RemoteServiceServlet implements
		DHCalcService {

//	private final static Object lock = new Object();
//	private final static ThreadPool pool = new ThreadPool(20);
	
	private static final Logger log = Logger.getLogger(DHCalcServiceImpl.class
			.getName());

	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(
			"#,###.####");

	{
		Util.getInstance().setFormatter(new Util.Formatter() {

			@Override
			public String format(double d) {
				return DECIMAL_FORMAT.format(d);
			}
		});
	}

//	private final Gson gson = new Gson();
	
	@Override
	public CareerProfile getProfile(final Realm realm, String profile, final int tag) {

		try {
			profile = URLEncoder.encode(profile, "UTF-8");

			log.info("getProfile(" + realm.getDisplayName() + "," + profile
					+ "-" + tag + ")");
			CareerProfile career = IO.getInstance().readCareerProfile(realm,
					profile, tag);

//			log.info("Career: " + gson.toJson(career));
			
			if (career == null) {
				career = new CareerProfile();
				career.code = "Timeout";
				career.reason = "Timeout";
				
			}

			if (career.code != null)
				log.info(realm.getDisplayName() + "/" + profile + "-" + tag
					+ " Code: " + career.code + ", Reason: "
					+ career.reason);

//			else {
//				
//				final CareerProfile c2 = career;
//				final String p2 = profile;
//				
//				pool.add(new Runnable(){
//
//					@Override
//					public void run() {
//						scanHeroes(realm, p2, tag, c2);
//					}});
//				
//			}
			
			return career;

		} catch (Exception e) {
			log.log(Level.SEVERE, "Exception Getting Profile", e);
			return null;
		}
	}

//	private static Set<AccountDocument> accountLock = new HashSet<AccountDocument>();
	
//	private void scanHeroes(Realm realm, String profile, int tag, CareerProfile career) {
//		
//		AccountDocument a = new AccountDocument();
//		a.setRealm(realm);
//		a.setProfile(profile.toLowerCase());
//		a.setTag(tag);
//		long now = System.currentTimeMillis();
//		long old = now - (1000L * 60L * 60L * 4L);
//
//		try {
//			synchronized (accountLock) {
//				if (accountLock.contains(a)) {
//					return;
//				} else {
//					accountLock.add(a);
//				}
//			}
//			
//			List<AccountDocument> accounts = CouchDBDHCalcDatabase.getInstance().findAll(AccountDocument.class);
//			
//			boolean changed = false;
//			
//			for (AccountDocument ad : accounts) {
//				if (ad.equals(a)) {
//
//					if (ad.getLastChecked() >= old)
//						return;
//					
//					a = ad;
//					break;
//				}
//			}
//			
//			changed = true;
//			a.setLastChecked(now);
//
//			if (a.getHeroes() == null)
//				a.setHeroes(new TreeSet<Integer>());
//	
//			if ((career.heroes != null) && (career.heroes.length > 0)) {
//				Set<Integer> set = a.getHeroes();
//				
//				for (Hero h : career.heroes) {
//					
//					if ((h != null)  && (h.clazz != null) && (h.clazz.equals(Const.CLASS_DEMONHUNTER))) {
//						if (!set.contains(h.id)) {
//							set.add(h.id);
//							changed = true;
//						}
//						
//						HeroProfile hero = getHero(realm, profile, tag, h.id, false);
//						
//						if ((hero.code != null) && (hero.code.equals(Const.NOT_FOUND))) {
//							set.remove(h.id);
//							changed = true;
//						}
//					}
//				}
//			} else if (!a.getHeroes().isEmpty()) {
//				a.setHeroes(new TreeSet<Integer>());
//				changed = true;
//			}
//			
//			if (changed) {
//				if (a.getRevision() == null)
//					log.info("Creating account " + a);
//				else
//					log.info("Updating account " + a);
//				
//				CouchDBDHCalcDatabase.getInstance().persist(a);
//			}
//		}
//		finally {
//			synchronized (accountLock) {
//				accountLock.remove(a);
//			}
//		}
//	}

	@Override
	public ItemInformation getItem(Realm realm, String item) {

		try {
//			log.info("getItem(" + realm.getDisplayName() + "," + item + ")");
	
			ItemInformation result = IO.getInstance().readItemInformation(realm,
					item);

//			log.info("Item: " + gson.toJson(result));
			
			if (result == null) {
				result = new ItemInformation();
				result.code = "Timeout";
				result.reason = "Timeout";
				
			}

			if (result.code != null)
				log.info(realm.getDisplayName() + "/" + item + " Code: " + result.code + ", Reason: "
						+ result.reason);

			return result;

		} catch (Exception e) {
			log.log(Level.SEVERE, "Exception Getting Profile", e);
			return null;
		}
	}

	@Override
	public HeroProfile getHero(Realm realm, String profile, int tag, int id) {
		return getHero(realm, profile, tag, id, true);
	}
	
	public HeroProfile getHero(final Realm realm, String profile, final int tag, int id, boolean updateAccount) {
		
//		if (updateAccount) {
//			
//			final String p2 = profile;
//			
//			new Thread(new Runnable(){
//
//				@Override
//				public void run() {
//					getProfile(realm, p2, tag);
//				}}).start();
//		}
		
		try {
			profile = URLEncoder.encode(profile, "UTF-8");

			log.info("getHero(" + realm.getDisplayName() + "," + profile + "-"
					+ tag + "/" + id + ")");

			HeroProfile hero = IO.getInstance().readHeroProfile(realm,
					profile, tag, id);

			if (hero == null) {
				hero = new HeroProfile();
				hero.code = "Timeout";
				hero.reason = "Timeout";
				
			}

			if (hero.code != null)
				log.warning(realm.getDisplayName() + "/" + profile + "-" + tag
					+ " Code: " + hero.code + ", Reason: " + hero.reason);

			if (hero.items != null) {

				Map<String, ItemInformation> items = new TreeMap<String, ItemInformation>();

				for (Map.Entry<String, ItemInformation> e : hero.items
						.entrySet()) {
					ItemInformation item = getItemNoLog(realm,
							e.getValue().tooltipParams);
					
					if (item != null) {
						items.put(e.getKey(), item);
					}

				}

				hero.items = items;
			}
			
			if (hero.followers != null) {
				Follower[] flist = { hero.followers.templar, hero.followers.enchantress, hero.followers.scoundrel };
				
				for (Follower f : flist) {
					if ((f != null) && f.items != null) {

						ItemInformation[] ilist = { f.items.leftFinger, f.items.rightFinger, f.items.neck, f.items.mainHand,
								f.items.offHand, f.items.special };
						
						int n = 0;
						
						for (ItemInformation i : ilist) {
							
							if ((i != null) && (i.tooltipParams != null)) {
								ItemInformation item = getItemNoLog(realm,
										i.tooltipParams);
								
								if (item != null) {
									ilist[n] = item;
								}
							}
							
							n++;
						}
						
						n = 0;
						f.items.leftFinger = ilist[n++];
						f.items.rightFinger = ilist[n++];
						f.items.neck = ilist[n++];
						f.items.mainHand = ilist[n++];
						f.items.offHand = ilist[n++];
						f.items.special = ilist[n++];
					}
				}
				
			}

			return hero;

		} catch (Exception e) {
			log.log(Level.SEVERE, "Exception Getting Hero", e);
			return null;
		}
	}

	private ItemInformation getItemNoLog(Realm realm, String tooltipParams)
			throws JsonParseException, IOException {
		return IO.getInstance().readItemInformation(realm, tooltipParams);
	}

	public String serializeFormData(FormData data) {
		try {
			String key = String.valueOf(Math.random() + "." + Math.random()
					+ ".FormData");
			ObjectMapper mapper = new ObjectMapper();

			String result = mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(data);

			ClientBuffer.getInstance().put(key, result.getBytes());

			return key;

		} catch (Exception e) {
			log.log(Level.SEVERE, "Exception Serializing Form Data", e);
			return null;
		}
	}

	@Override
	public FormData getClientData(String client) {
		String key = client + ".FormData";

		Object value = ClientBuffer.getInstance().get(key);

		if (value == null) {

			try {
				Thread.sleep(1000);
			} catch (Exception ignore) {
			}

			return null;
		}

		try {
			return (FormData) value;
		} catch (Exception e) {
			log.log(Level.SEVERE, "Exception Deserializing Form Data", e);
			return null;
		}
	}

	@Override
	public String exportData(ExportData data) {
		ExportExcel excel = new ExportExcel(data);
		try {
			String key = String.valueOf(Math.random() + "." + Math.random()
					+ ".Excel");
			byte[] bytes = excel.export();
			ClientBuffer.getInstance().put(key, bytes);

			return key;
		} catch (Exception e) {
			log.log(Level.SEVERE, "Exception Exporting Excel data", e);
			return null;
		}
	}

	@Override
	public Version getVersion() {
		return Version.getVersion();
	}

	public String toJson(ApiData object) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
					false);
			String str = mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(object);

			return str;
		} catch (Exception e) {
			log.log(Level.SEVERE, "Exception Serializing Object", e);
			return null;
		}
	}

	public ApiData fromJson(String json, String type) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return (ApiData) mapper.readValue(json, Class.forName(type));
		} catch (Exception e) {
			log.log(Level.SEVERE, "Exception Deserializing JSON", e);
			return null;
		}
	}

//	@Override
//	public void logData(final CharacterData data) {
//
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				data.setDefaults();
//
//				log.info("Logging data for " + data.getProfile() + "-"
//						+ data.getTag() + "(" + data.getRealm().name() + ")/"
//						+ data.getHero());
//
//				DpsTableEntry entry = calculateDps(data);
//
//				CouchDBDHCalcDatabase.getInstance().logDps(entry);
//			}
//		}).start();
//
//	}

//	public DpsTableEntry calculateDps(CharacterData data) {
//
//		Build build = new Build();
//		build.setSkills(data.getSkills());
//
//		DpsTableEntry entry = new DpsTableEntry();
//
//		data.setDefaults();
//		ProfileHelper.updateWeaponDamage(data);
//		ProfileHelper.updateCdr(data);
//		data.setDefaults();
//
//		entry.setBattletag(data.getProfile() + "-" + data.getTag() + "/"
//				+ data.getHero());
//		entry.setRealm(data.getRealm());
//		entry.setBuild(build);
//		entry.setParagon_dex(data.getParagonDexterity());
//		entry.setParagon_cc(data.getParagonCC());
//		entry.setParagon_cdr(data.getParagonCDR());
//		entry.setParagon_chd(data.getParagonCHD());
//		entry.setParagon_ias(data.getParagonIAS());
//		entry.setParagon_hatred(data.getParagonHatred());
//		entry.setParagon_rcr(data.getParagonRCR());
//		entry.setParagon(data.getParagon());
//		entry.setProfile(data.getProfile());
//		entry.setTag(data.getTag());
//		entry.setHeroId(data.getHero());
//		entry.setHeroName(data.getHeroName());
//		entry.setPlayerAps(data.getAps());
//		entry.setSeasonal(data.isSeasonal());
//		entry.setHardcore(data.isHardcore());
//		entry.setDead(data.isDead());
//		entry.setSheetDps(data.getSheetDps());
//		entry.setLevel(data.getLevel());
//
//		data.setNumAdditional(0);
//		calculateDamage(data, entry);
//		data.setNumAdditional(10);
//		calculateDamage(data, entry);
//
//		entry.setWhen(System.currentTimeMillis());
//
//		return entry;
//	}
//
//	private void calculateDamage(CharacterData data, DpsTableEntry entry) {
//
//		DamageResult damage = FiringData.calculateDamages(data);
//
//		double total = 0.0;
//		double totalElite = 0.0;
//		double e = 1.0 + data.getTotalEliteDamage();
//
//		for (Damage d : damage.damages) {
//			total += d.actualDamage;
//			// System.out.println(d.log);
//		}
//
//		total = total / damage.duration;
//
//		totalElite = total * e;
//
//		if (data.getNumAdditional() == 0) {
//			entry.setSingle(total);
//			entry.setSingle_elite(totalElite);
//		} else {
//			entry.setMultiple(total);
//			entry.setMultiple_elite(totalElite);
//		}
//	}
//
//	@Override
//	public DBStatistics getStats(Rune sentryRune, ActiveSkill[] skills,
//			Rune[] runes) {
//		return CouchDBDHCalcDatabase.getInstance().getStatistics(sentryRune, skills, runes);
//	}

	@Override
	public SeasonIndex getSeasonEraIndex(Realm realm) {
		try {
			SeasonIndex seasons = IO.getInstance().readSeasonIndex(realm);
			SeasonIndex eras = IO.getInstance().readEraIndex(realm);
			
			seasons.eras = eras.eras;
			seasons.current_era = eras.current_era;

			return seasons;
			
		} catch (RuntimeException e) {
			log.log(Level.SEVERE, "Exception", e);
			throw e;
		} catch (Exception e2) {
			log.log(Level.SEVERE, "Exception", e2);
			throw new RuntimeException(e2);
		}
	}

	@Override
	public Leaderboard getLeaderboard(Realm realm, int seasonEra,
			boolean isEra, String which) {
		try {
			Leaderboard lb = null;
			
			if (isEra) {
				lb = IO.getInstance().readEraLeaderboard(realm, seasonEra, which);
			} else {
				lb =  IO.getInstance().readSeasonLeaderboard(realm, seasonEra, which);
			}
			
//			Gson gson = new GsonBuilder().setPrettyPrinting().create();
//			
//			FileOutputStream os = new FileOutputStream("C:\\code\\out.json");
//			PrintWriter writer = new PrintWriter(os);
//			writer.println(gson.toJson(lb));
//			writer.flush();
//			writer.close();
			
			return lb;
			
		} catch (RuntimeException e) {
			log.log(Level.SEVERE, "Exception", e);
			throw e;
		} catch (Exception e2) {
			log.log(Level.SEVERE, "Exception", e2);
			throw new RuntimeException(e2);
		}
	}

	@Override
	public NewsItem[] getNews() {
		try {
			List<NewsDocument> news = CouchDBDHCalcDatabase.getInstance().getNews();
			List<NewsItem> result = new Vector<NewsItem>(news.size());
			
			for (NewsDocument doc : news) {
				result.add(new NewsItem(doc.getText()));
			}
			
			return result.toArray(new NewsItem[0]);
			
		} catch (RuntimeException e) {
			log.log(Level.SEVERE, "Exception", e);
			throw e;
		} catch (Exception e2) {
			log.log(Level.SEVERE, "Exception", e2);
			throw new RuntimeException(e2);
		}
	}

	public static void terminate() {
		log.info("terminate");

	}

	public static void initialize() {
		log.info("initialize()");
		CouchDBDHCalcDatabase.getInstance();
		CouchDBDHCalcParameters.getInstance();
	}
	
	public static void main(String[] args) {
		DHCalcServiceImpl.initialize();
		
		DHCalcServiceImpl i = new DHCalcServiceImpl();
		HeroProfile p = i.getHero(Realm.US, "dawg6", 1416, 72386632);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		log.info(gson.toJson(p));
	}
}
