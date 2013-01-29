package com.thingtrack.bustrack.view.module.servicetemplate.addon.parser.geolocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

public class NominatinGeocoder {

	// URLs
	private static final String URL = "http://open.mapquestapi.com/nominatim/v1/reverse?";

	// URL COMMON PARAMETERS
	private static final String OUTPUT_FORMAT_PARAM_KEY = "format=";
	private static final String LATITUDE_PARAM_KEY = "lat=";
	private static final String LONGITUDE_PARAM_KEY = "lon=";

	private static NominatinGeocoder instance;

	private NominatinGeocoder() {
	}

	public static NominatinGeocoder getIsntance() {

		if (instance == null)
			instance = new NominatinGeocoder();

		return instance;
	}

	public Collection<GeocodedLocation> geocode(double latitude,
			double longitude) throws GeocodingException {

		final Set<GeocodedLocation> locations = new LinkedHashSet<GeocodedLocation>();
		BufferedReader reader = null;
		try {
			String addr = getURL(latitude, longitude);
			URLConnection con = new URL(addr).openConnection();
			con.setDoOutput(true);
			con.connect();
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			final StringBuilder builder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null)
				builder.append(line);

			// Add all geocoded locations
			locations.addAll(createLocations(null, builder.toString()));

		} catch (Exception e) {
			throw new GeocodingException(e.getMessage(), e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
		return locations;
	}

	protected String getURL(double latitude, double longitude)
			throws UnsupportedEncodingException {

		StringBuilder sb = new StringBuilder();

		// Output format
		sb.append(OUTPUT_FORMAT_PARAM_KEY + "json");

		// Latitude and longitude
		sb.append("&" + LATITUDE_PARAM_KEY + latitude);
		sb.append("&" + LONGITUDE_PARAM_KEY + longitude);

		// Compound the url
		return URL + sb.toString();

	}

	protected Collection<GeocodedLocation> createLocations(String address,
			String input) throws GeocodingException {

		final Set<GeocodedLocation> locations = new LinkedHashSet<GeocodedLocation>();

		try {
			JSONObject result = new JSONObject(input);
			boolean ambiguous = false;

			GeocodedLocation loc = new GeocodedLocation();
			loc.setAmbiguous(ambiguous);
			loc.setOriginalAddress(address);
			loc.setGeocodedAddress(result.getString("display_name"));
			loc.setLat(Double.parseDouble(result.getString("lat")));
			loc.setLon(Double.parseDouble(result.getString("lon")));
			loc.setType(getLocationType(result));
			if (result.has("address")) {
				JSONObject obj = result.getJSONObject("address");
				if (obj.has("house_number"))
					loc.setStreetNumber(obj.getString("house_number"));
				if (obj.has("road"))
					loc.setRoute(obj.getString("road"));
				if (obj.has("city"))
					loc.setLocality(obj.getString("city"));
				if (obj.has("county"))
					loc.setAdministrativeAreaLevel2(obj.getString("county"));
				if (obj.has("state"))
					loc.setAdministrativeAreaLevel1(obj.getString("state"));
				if (obj.has("postcode"))
					loc.setPostalCode(obj.getString("postcode"));
				if (obj.has("country_code"))
					loc.setCountry(obj.getString("country_code").toUpperCase());
			}
			locations.add(loc);

		} catch (JSONException e) {
			throw new GeocodingException(e.getMessage(), e);
		}

		return locations;
	}

	private LocationType getLocationType(JSONObject result)
			throws JSONException {

		if (!(result.has("class") && result.has("type")))
			return null;

		final String classValue = result.getString("class");
		final String type = result.getString("type");
		if ("highway".equals(classValue) || "railway".equals(classValue))
			return LocationType.ROUTE;
		else if ("amenity".equals(classValue) || "liesure".equals(classValue)
				|| "natural".equals(type) || "shop".equals(classValue)
				|| "tourism".equals(classValue) || "waterway".equals(type)) {
			return LocationType.POI;
		} else if ("building".equals(classValue))
			return LocationType.STREET_ADDRESS;
		else if ("place".equals(classValue)) {
			if ("house".equals(type) || "houses".equals(type)
					|| "airport".equals(type) || "farm".equals(type))
				return LocationType.STREET_ADDRESS;
			else if ("city".equals(type) || "hamlet".equals(type)
					|| "town".equals(type)
					|| "unincorporated_area".equals(type)
					|| "locality".equals(type) || "village".equals(type)
					|| "municipality".equals(type)) {
				return LocationType.LOCALITY;
			} else if ("state".equals(type) || "region".equals(type))
				return LocationType.ADMIN_LEVEL_1;
			else if ("postcode".equals(type))
				return LocationType.POSTAL_CODE;
			else if ("country".equals(type))
				return LocationType.COUNTRY;
			else if ("county".equals(type))
				return LocationType.ADMIN_LEVEL_2;
			else if ("subdivision".equals(type) || "suburb".equals(type))
				return LocationType.NEIGHBORHOOD;
			else if ("moor".equals(type) || "island".equals(type)
					|| "islet".equals(type) || "sea".equals(type))
				return LocationType.POI;
		} else if ("boundary".equals(classValue)
				&& "administrative".equals(type))
			return LocationType.ADMIN_LEVEL_1;
		return LocationType.UNKNOWN;

	}

}
