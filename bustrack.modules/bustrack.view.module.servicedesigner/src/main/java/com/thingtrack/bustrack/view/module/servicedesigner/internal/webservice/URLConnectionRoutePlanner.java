package com.thingtrack.bustrack.view.module.servicedesigner.internal.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;

import com.thingtrack.bustrack.view.module.servicedesigner.internal.model.MapCoordinates;
import com.thingtrack.bustrack.view.module.servicedesigner.internal.model.Route;
import com.thingtrack.bustrack.view.module.servicedesigner.internal.model.RoutePlannerException;



/**
 * Implementation that fetches data from an URL and delegates to implementing
 * class to decode
 */
public abstract class URLConnectionRoutePlanner<T extends Route> {

	/**
	 * Retrieve the full URL to fetch
	 * 
	 * @param addresses
	 *            input address
	 * @return full URL
	 * @throws UnsupportedEncodingException
	 *             if subclass uses {@link java.net.URLEncoder} and it fails
	 */
	protected abstract String getURL(String... addresses)
			throws UnsupportedEncodingException, JSONException;

	/**
	 * Retrieve the full URL to fetch
	 * 
	 * @param coordinates
	 *            input
	 *            {@link com.thingtrack.route.planner.model.MapCoordinates }
	 * @return full URL
	 * @throws UnsupportedEncodingException
	 *             if subclass uses {@link java.net.URLEncoder} and it fails
	 * @throws JSONException 
	 */
	protected abstract String getURL(MapCoordinates... coordinates)
			throws UnsupportedEncodingException, JSONException;

	
	
	public T getRoute(MapCoordinates... coordinates)
			throws RoutePlannerException {

		T route;
		BufferedReader reader = null;

		try {
			String addr = getURL(coordinates);
			URLConnection con = new URL(addr).openConnection();
			con.setDoOutput(true);
			con.connect();
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream(), getEncoding()));

			final StringBuilder builder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null)
				builder.append(line);
			
			route = createRoute(builder.toString(), coordinates);

		} catch (Exception e) {
			throw new RoutePlannerException(e.getMessage(), e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// ignore
				}
			}

		}
		
		return route;
	}
	
	/**
     * Creates {@link Route} objects from response stream
     * @param coordinates input address
     * @param input response stream as string
     * @return {@link Route} object
     * @throws RoutePlannerException
     */
    protected abstract T createRoute(String input, MapCoordinates ... coordinates) throws RoutePlannerException;

	/**
	 * Encoding the response stream is to be expected. Default is UTF-8.
	 * Override in subclass as necessary.
	 */
	protected String getEncoding() {
		return "UTF-8";
	}
}
