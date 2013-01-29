package com.thingtrack.bustrack.view.module.servicedesigner.internal.webservice;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.thingtrack.bustrack.view.module.servicedesigner.internal.model.Leg;
import com.thingtrack.bustrack.view.module.servicedesigner.internal.model.Location;
import com.thingtrack.bustrack.view.module.servicedesigner.internal.model.Maneuver;
import com.thingtrack.bustrack.view.module.servicedesigner.internal.model.MapCoordinates;
import com.thingtrack.bustrack.view.module.servicedesigner.internal.model.Route;
import com.thingtrack.bustrack.view.module.servicedesigner.internal.model.RoutePlannerException;
import com.thingtrack.bustrack.view.module.servicedesigner.internal.model.Shape;
import com.thingtrack.bustrack.view.module.servicedesigner.internal.model.Location.AdminAreaType;
import com.thingtrack.bustrack.view.module.servicedesigner.internal.model.Sign;

public final class MapQuestOpenDirectionsService extends
		URLConnectionRoutePlanner<Route> {

	public enum RouteType {

		SHORTEST("shortest"), FASTEST("fastest"), PEDESTRIAN("pedestrian"), MULTIMODAL(
				"multimodal"), BICYCLE("bicycle");

		private String value;

		private RouteType(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	public enum TimeType {

		NONE(0), CURRENT_TIME(1), CUSTOM_TIME(2);

		private int value;

		private TimeType(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

	public enum DateType {

		SPECIFIC_DATE_AND_TIME(0), SUNDAY(1), MONDAY(2), TUESDAT(3), WEDNESDAY(
				4), THURSDAY(5), FRIDAY(6), SATURDAY(7);

		private int value;

		private DateType(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

	public enum NarrativeType {

		TEXT("text"), HTML("html"), MICROFORMAT("microformat");

		private String value;

		private NarrativeType(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	public enum Unit {

		MILES("m"), KILOMETERS("k");

		private String value;

		private Unit(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	public enum ShapeFormat {

		NONE("none"), RAW("raw"), COMPRESSED("cmp"), COMPRESSED_6_DIGIT_PRECISION(
				"cmp6");

		private String value;

		private ShapeFormat(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

	}

	public enum InputFormat {

		KEY_VALUE_PAIRS("key/value pairs"), JSON("json"), XML("xml");

		private String value;

		private InputFormat(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	public enum OutputFormat {

		KEY_VALUE_PAIRS("key/value pairs"), JSON("json"), XML("xml");

		private String value;

		private OutputFormat(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	public enum DrivingStyle {

		CAUTIOUS(1), NORMAL(2), AGRESSIVE(3);

		private int value;

		private DrivingStyle(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

	public enum RoutingOption {

		// TODO: Faltan algunas opciones
		UNIT, ROUTE_TYPE, LOCALE, AVOIDS, DRIVING_STYLE, HIGHWAY_EFFICIENCY, TIME_TYPE, DATE_TYPE, LOCAL_TIME;
	}

	private static final String BASE_URL = "http://open.mapquestapi.com/directions/v1/route?locale=es&generalize=0&outFormat=json&unit=k";

	// URL COMMON PARAMETERS
	public static final String START_PARAM_KEY = "from=";
	public static final String END_PARAM_KEY = "to=";
	public static final String AMBIGUITIES_PARAM_KEY = "ambiguities=";
	public static final String AVOIDS_PARAM_KEY = "avoids=";
	public static final String ROUTE_TYPE_PARAM_KEY = "routeType=";
	public static final String TIME_TYPE_PARAM_KEY = "timeType=";
	public static final String DATE_TYPE_PARAM_KEY = "dateType=";
	public static final String DATE_PARAM_KEY = "localTime=";
	public static final String LOCAL_TIME_PARAM_KEY = "localTime=";
	public static final String NARRATIVE_TYPE_PARAM_KEY = "narrativeType=";
	public static final String ENHANCED_NARRATIVE_PARAM_KEY = "enhancedNarrative=";
	public static final String UNIT_PARAM_KEY = "unit=";
	public static final String SHAPE_FORMAT_PARAM_KEY = "shapeFormat=";
	public static final String GENERALIZE_PARAM_KEY = "generalize=";
	public static final String INPUT_FORMAT_PARAM_KEY = "inFormat=";
	public static final String OUTPUT_FORMAT_PARAM_KEY = "outFormat=";
	public static final String LOCALE_PARAM_KEY = "locale=";
	public static final String DRIVING_STYLE_PARAM_KEY = "drivingStyle=";
	public static final String HIGHWAY_EFFICIENCY_PARAM_KEY = "highwayEfficiency=";

	private static MapQuestOpenDirectionsService instance;

	private Map<RoutingOption, Object> routingOptions = new HashMap<RoutingOption, Object>();

	public static MapQuestOpenDirectionsService getInstance() {

		if (instance == null)
			instance = new MapQuestOpenDirectionsService();

		return instance;
	}

	private MapQuestOpenDirectionsService() {
	}

	public void addRoutingOption(RoutingOption routingOption, Object value) {
		routingOptions.put(routingOption, value);
	}

	public void removeRoutingOption(RoutingOption routingOption) {
		routingOptions.remove(routingOption);
	}
	
	public void removeAllOptions(){
		routingOptions.clear();
	}

	@Override
	protected String getURL(String... addresses)
			throws UnsupportedEncodingException, JSONException {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < addresses.length; i++) {
			if (i == 0) {
				sb.append("&" + START_PARAM_KEY
						+ URLEncoder.encode(addresses[i], "UTF-8"));
			} else if (i == addresses.length - 1) {

				sb.append("&" + END_PARAM_KEY
						+ URLEncoder.encode(addresses[i], "UTF-8"));
			}
		}

		//Routing options
		return BASE_URL + appendRoutingOptions(sb).toString();

	}

	@Override
	protected String getURL(MapCoordinates... coordinates)
			throws UnsupportedEncodingException, JSONException {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < coordinates.length; i++) {
			if (i == 0) {
				sb.append("&" + START_PARAM_KEY + coordinates[i].getLat() + ","
						+ coordinates[i].getLng());
			} else {

				sb.append("&" + END_PARAM_KEY + coordinates[i].getLat() + ","
						+ coordinates[i].getLng());
			}
		}

		//Routing options
		return BASE_URL + appendRoutingOptions(sb).toString();
	}

	private StringBuilder appendRoutingOptions(StringBuilder builder) {

		if (routingOptions.size() == 0)
			return builder;
		
		GregorianCalendar gregorianCalendar = (GregorianCalendar) GregorianCalendar
				.getInstance();

		for (RoutingOption routingOption : routingOptions.keySet()) {

			switch (routingOption) {

			case ROUTE_TYPE:
				String routeTypeValue = (String) routingOptions
						.get(routingOption);
				builder.append("&" + ROUTE_TYPE_PARAM_KEY + routeTypeValue);
				break;

			case AVOIDS:

				@SuppressWarnings("unchecked")
				List<String> avoids = ((List<String>) routingOptions
						.get(routingOption));

				for (String avoid : avoids)
					builder.append("&" + AVOIDS_PARAM_KEY + avoid);
				break;

			case DRIVING_STYLE:

				int drivingStyleValue = (Integer) routingOptions
						.get(routingOption);
				builder.append("&" + DRIVING_STYLE_PARAM_KEY
						+ drivingStyleValue);
				break;

			case HIGHWAY_EFFICIENCY:

				double highwayEffiencyValue = (Double) routingOptions
						.get(routingOption);
				builder.append("&" + HIGHWAY_EFFICIENCY_PARAM_KEY
						+ highwayEffiencyValue);
				break;

			case LOCALE:

				String languageCodeValue = (String) routingOptions
						.get(routingOption);
				builder.append("&" + LOCALE_PARAM_KEY + languageCodeValue);
				break;

			case UNIT:

				String unitValue = (String) routingOptions.get(routingOption);
				builder.append("&" + UNIT_PARAM_KEY + unitValue);
				break;

			case TIME_TYPE:

				int timeTypeValue = (Integer) routingOptions.get(routingOption);
				builder.append("&" + TIME_TYPE_PARAM_KEY + timeTypeValue);
				break;

			case DATE_TYPE:

				Date dateTypeValue = (Date) routingOptions.get(routingOption);
				gregorianCalendar.setTime(dateTypeValue);
				builder.append("&" + DATE_TYPE_PARAM_KEY
						+ gregorianCalendar.get(GregorianCalendar.DAY_OF_WEEK + 1));
				break;

			case LOCAL_TIME:

				Date localTimeValue = (Date) routingOptions.get(routingOption);
				gregorianCalendar.setTime(localTimeValue);
				builder.append("&" + LOCAL_TIME_PARAM_KEY
						+ gregorianCalendar.get(GregorianCalendar.HOUR_OF_DAY) + ":" + gregorianCalendar.get(GregorianCalendar.MINUTE));
				break;
			}
		}
		return builder;
	}

	@Override
	protected Route createRoute(String input, MapCoordinates... coordinates)
			throws RoutePlannerException {

		Route route = new Route();

		try {

			JSONObject obj = new JSONObject(input);

			JSONObject routeJsonObject = obj.getJSONObject("route");

			route.setHasTollRoad(routeJsonObject.optBoolean("hasTollRoad"));
			route.setHasFerry(routeJsonObject.optBoolean("hasFerry"));
			route.setHasHighway(routeJsonObject.optBoolean("hasHighway"));
			route.setHasSeasonalClosure(routeJsonObject
					.optBoolean("hasSeasonalClosure"));
			route.setHasUnpaved(routeJsonObject.optBoolean("hasUnpaved"));
			route.setHasCountryCross(routeJsonObject
					.optBoolean("hasCountryCross"));

			route.setDistance(routeJsonObject.getDouble("distance"));
			route.setTime(routeJsonObject.getLong("time"));
			route.setFormattedTime(routeJsonObject.getString("formattedTime"));
			route.setFuelUsed(routeJsonObject.getDouble("fuelUsed"));

			// Legs
			route.setLegs(createLegs(routeJsonObject.getJSONArray("legs")));

			// Locations
			route.setLocations(createLocations(routeJsonObject
					.getJSONArray("locations")));

			// Shape
			route.setShape(createShape(routeJsonObject.getJSONObject("shape")));

		} catch (JSONException e) {
			throw new RoutePlannerException(e.getMessage(), e);
		}

		return route;
	}

	private List<Leg> createLegs(JSONArray inputArray) throws JSONException {

		List<Leg> legs = new ArrayList<Leg>();
		for (int i = 0; i < inputArray.length(); i++) {

			Leg leg = new Leg();
			JSONObject legJsonObject = inputArray.getJSONObject(i);

			leg.setOrigIndex(legJsonObject.getInt("origIndex"));
			leg.setOrigNarrative(legJsonObject.getString("origNarrative"));
			leg.setDestIndex(legJsonObject.getInt("destIndex"));
			leg.setDestNarrative(legJsonObject.getString("destNarrative"));
			leg.setHasTollRoad(legJsonObject.optBoolean("hasTollRoad"));
			leg.setHasFerry(legJsonObject.optBoolean("hasFerry"));
			leg.setHasHighway(legJsonObject.optBoolean("hasHighway"));
			leg.setHasSeasonalClosure(legJsonObject
					.optBoolean("hasSeasonalClosure"));
			leg.setHasUnpaved(legJsonObject.optBoolean("hasUnpaved"));
			leg.setHasCountryCross(legJsonObject.optBoolean("hasCountryCross"));
			leg.setIndex(legJsonObject.getInt("index"));
			leg.setTime(legJsonObject.getInt("time"));
			leg.setFormattedTime(legJsonObject.getString("formattedTime"));
			leg.setDistance(legJsonObject.getDouble("distance"));

			// Maneuvers
			leg.setManeuvers(createManeuvers(legJsonObject
					.getJSONArray("maneuvers")));

			legs.add(leg);
		}

		return legs;
	}

	private List<Maneuver> createManeuvers(JSONArray inputArray)
			throws JSONException {

		List<Maneuver> maneuvers = new ArrayList<Maneuver>();

		for (int i = 0; i < inputArray.length(); i++) {

			Maneuver maneuver = new Maneuver();
			JSONObject maneuverJsonObject = inputArray.getJSONObject(i);

			maneuver.setIndex(maneuverJsonObject.getInt("index"));
			maneuver.setAttributes(maneuverJsonObject.getInt("attributes"));
			maneuver.setDirection(getDirection(maneuverJsonObject
					.getInt("direction")));
			maneuver.setDistance(maneuverJsonObject.getDouble("distance"));
			maneuver.setFormattedTime(maneuverJsonObject
					.getString("formattedTime"));
			maneuver.setIconUrl(maneuverJsonObject.getString("iconUrl"));
			maneuver.setTurnType(getTurnType(maneuverJsonObject
					.getInt("turnType")));

			JSONObject startpointJsonObject = maneuverJsonObject
					.getJSONObject("startPoint");

			maneuver.setStartPoint(new MapCoordinates(startpointJsonObject
					.getDouble("lat"), startpointJsonObject.getDouble("lng")));

			maneuver.setStreets(getStreets(maneuverJsonObject
					.getJSONArray("streets")));

			maneuver.setTime(maneuverJsonObject.getInt("time"));
			maneuver.setMapUrl(maneuverJsonObject.optString("mapUrl"));
			maneuver.setNarrative(maneuverJsonObject.getString("narrative"));

			// TODO: Marshaling the transport mode in the response's json

			maneuver.setSigns(getSigns(maneuverJsonObject.getJSONArray("signs")));

			maneuvers.add(maneuver);
		}

		return maneuvers;
	}

	private Maneuver.Direction getDirection(int input) {

		if (input == Maneuver.Direction.EAST.ordinal())
			return Maneuver.Direction.EAST;
		if (input == Maneuver.Direction.NORTH.ordinal())
			return Maneuver.Direction.NORTH;
		if (input == Maneuver.Direction.NORTHEAST.ordinal())
			return Maneuver.Direction.NORTHEAST;
		if (input == Maneuver.Direction.NORTHWEST.ordinal())
			return Maneuver.Direction.NORTHWEST;
		if (input == Maneuver.Direction.SOUTH.ordinal())
			return Maneuver.Direction.SOUTH;
		if (input == Maneuver.Direction.SOUTHEAST.ordinal())
			return Maneuver.Direction.SOUTHEAST;
		if (input == Maneuver.Direction.SOUTHWEST.ordinal())
			return Maneuver.Direction.SOUTHWEST;
		if (input == Maneuver.Direction.WEST.ordinal())
			return Maneuver.Direction.WEST;

		return Maneuver.Direction.NONE;

	}

	private Maneuver.TurnType getTurnType(int input) {

		if (input == Maneuver.TurnType.LEFT.ordinal())
			return Maneuver.TurnType.LEFT;
		if (input == Maneuver.TurnType.LEFT_FORM.ordinal())
			return Maneuver.TurnType.LEFT_FORM;
		if (input == Maneuver.TurnType.LEFT_MERGE.ordinal())
			return Maneuver.TurnType.LEFT_MERGE;
		if (input == Maneuver.TurnType.LEFT_OFF_RAMP.ordinal())
			return Maneuver.TurnType.LEFT_OFF_RAMP;
		if (input == Maneuver.TurnType.LEFT_ON_RAMP.ordinal())
			return Maneuver.TurnType.LEFT_ON_RAMP;
		if (input == Maneuver.TurnType.LEFT_U_TURN.ordinal())
			return Maneuver.TurnType.LEFT_U_TURN;
		if (input == Maneuver.TurnType.REVERSE.ordinal())
			return Maneuver.TurnType.REVERSE;
		if (input == Maneuver.TurnType.RIGHT.ordinal())
			return Maneuver.TurnType.RIGHT;
		if (input == Maneuver.TurnType.RIGHT_FORK.ordinal())
			return Maneuver.TurnType.RIGHT_FORK;
		if (input == Maneuver.TurnType.RIGHT_MERGE.ordinal())
			return Maneuver.TurnType.RIGHT_MERGE;
		if (input == Maneuver.TurnType.RIGHT_OFF_RAMP.ordinal())
			return Maneuver.TurnType.RIGHT_OFF_RAMP;
		if (input == Maneuver.TurnType.RIGHT_ON_RAMP.ordinal())
			return Maneuver.TurnType.RIGHT_ON_RAMP;
		if (input == Maneuver.TurnType.RIGHT_U_TURN.ordinal())
			return Maneuver.TurnType.RIGHT_U_TURN;
		if (input == Maneuver.TurnType.SHARP_LEFT.ordinal())
			return Maneuver.TurnType.SHARP_LEFT;
		if (input == Maneuver.TurnType.SHARP_RIGHT.ordinal())
			return Maneuver.TurnType.SHARP_RIGHT;
		if (input == Maneuver.TurnType.SLIGHT_LEFT.ordinal())
			return Maneuver.TurnType.SLIGHT_LEFT;
		if (input == Maneuver.TurnType.SLIGHT_RIGHT.ordinal())
			return Maneuver.TurnType.SLIGHT_RIGHT;
		if (input == Maneuver.TurnType.STRAIGHT.ordinal())
			return Maneuver.TurnType.STRAIGHT;
		if (input == Maneuver.TurnType.STRAIGHT_FORK.ordinal())
			return Maneuver.TurnType.STRAIGHT_FORK;

		return null;
	}

	private List<String> getStreets(JSONArray inputArray) throws JSONException {

		List<String> streets = new ArrayList<String>();

		for (int i = 0; i < inputArray.length(); i++) {

			String street = inputArray.getString(i);
			streets.add(street);
		}

		return streets;
	}

	private List<Sign> getSigns(JSONArray inputArray) throws JSONException {

		List<Sign> signs = new ArrayList<Sign>();

		for (int i = 0; i < inputArray.length(); i++) {

			Sign sign = new Sign();

			JSONObject signJsonObject = inputArray.getJSONObject(i);
			sign.setDirecction(getDirection(signJsonObject.getInt("direction")));
			sign.setExtraText(signJsonObject.getString("extraText"));
			sign.setUrl(signJsonObject.getString("url"));
			sign.setType(signJsonObject.getString("type"));

			signs.add(sign);
		}

		return signs;
	}

	private Shape createShape(JSONObject input) throws JSONException {

		Shape shape = new Shape();

		List<Integer> legIndexes = new ArrayList<Integer>();
		JSONArray legIndexesJsonArray = input.getJSONArray("legIndexes");

		for (int i = 0; i < legIndexesJsonArray.length(); i++)
			legIndexes.add(legIndexesJsonArray.getInt(i));

		shape.setLegIndex(legIndexes);

		List<Integer> maneuverIndexes = new ArrayList<Integer>();
		JSONArray maneuverIndexesJsonArray = input.getJSONArray("legIndexes");

		for (int i = 0; i < maneuverIndexesJsonArray.length(); i++)
			maneuverIndexes.add(maneuverIndexesJsonArray.getInt(i));

		shape.setLegIndex(maneuverIndexes);

		List<MapCoordinates> shapePoints = new ArrayList<MapCoordinates>();
		JSONArray shapePointsJsonArray = input.getJSONArray("shapePoints");

		for (int i = 0; i < shapePointsJsonArray.length() - 1;) {
			shapePoints.add(new MapCoordinates(shapePointsJsonArray
					.getDouble(i), shapePointsJsonArray.getDouble(i + 1)));
			i = i + 2;
		}

		shape.setShapePoints(shapePoints);

		return shape;

	}

	private List<Location> createLocations(JSONArray inputArray)
			throws JSONException {

		List<Location> locations = new ArrayList<Location>();

		for (int i = 0; i < inputArray.length(); i++) {

			Location location = new Location();
			JSONObject locationJsonObject = inputArray.getJSONObject(i);

			location.setAdminArea1(locationJsonObject.getString("adminArea1"));
			location.setAdminArea1Type(getAdminAreaType(locationJsonObject
					.getString("adminArea1Type")));

			location.setAdminArea3(locationJsonObject.getString("adminArea3"));
			location.setAdminArea3Type(getAdminAreaType(locationJsonObject
					.getString("adminArea3Type")));

			location.setAdminArea4(locationJsonObject.getString("adminArea4"));
			location.setAdminArea4Type(getAdminAreaType(locationJsonObject
					.getString("adminArea4Type")));

			location.setAdminArea5(locationJsonObject.getString("adminArea5"));
			location.setAdminArea5Type(getAdminAreaType(locationJsonObject
					.getString("adminArea5Type")));

			JSONObject displayLatLngJsonObject = locationJsonObject
					.getJSONObject("displayLatLng");

			location.setDisplayLatLng(new MapCoordinates(
					displayLatLngJsonObject.getDouble("lat"),
					displayLatLngJsonObject.getDouble("lng")));

			location.setDragPoint(locationJsonObject.getBoolean("dragPoint"));

			location.setGeocodeQuality(locationJsonObject
					.getString("geocodeQuality"));
			location.setGeocodeQualityCode(locationJsonObject
					.getString("geocodeQualityCode"));

			JSONObject latLngJsonObject = locationJsonObject
					.getJSONObject("latLng");

			location.setLatLng(new MapCoordinates(latLngJsonObject
					.getDouble("lat"), latLngJsonObject.getDouble("lng")));

			location.setLinkId(locationJsonObject.getInt("linkId"));

			location.setPostalCode(locationJsonObject.getString("postalCode"));

			location.setSideOFStreet(locationJsonObject
					.getString("sideOfStreet"));

			location.setStreet(locationJsonObject.getString("street"));

			location.setType(locationJsonObject.getString("type"));

			locations.add(location);
		}

		return locations;

	}

	private Location.AdminAreaType getAdminAreaType(String adminAreaType) {

		if (Location.AdminAreaType.COUNTRY.getValue().equals(adminAreaType)) {

			return AdminAreaType.COUNTRY;
		}
		if (Location.AdminAreaType.STATE.getValue().equals(adminAreaType)) {

			return AdminAreaType.STATE;
		}
		if (Location.AdminAreaType.COUNTY.getValue().equals(adminAreaType)) {

			return AdminAreaType.COUNTY;
		}
		if (Location.AdminAreaType.CITY.getValue().equals(adminAreaType)) {

			return AdminAreaType.CITY;
		}

		return AdminAreaType.UNKNOWN;
	}
}
