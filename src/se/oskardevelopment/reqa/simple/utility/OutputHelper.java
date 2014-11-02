/**
 * Copyright (c) 2014 Oskar Präntare
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * The name of the copyright holders and oskardevelopment may not be used to endorse or promote products derived
 * from this software without specific prior written permission.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 **/
package se.oskardevelopment.reqa.simple.utility;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.oskardevelopment.reqa.simple.model.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

/**
 * OutputHelper is an utility class used for formatting
 * and preparing information for external use.
 * Ex. saving or loading saved sessions.
 */
public class OutputHelper {
	// OutputHelper's logger.
	private static final Logger LOGGER = LogManager.getLogger();
	// Name of the file where text (ex. ReQA sessions) are saved.
	public static String FILE_NAME = "history.reqa";
	// Encoding used when saving or loading files.
	public static String ENCODING = "UTF-8";
	// Gson date format. 
	protected static String FORMAT = "yyyy-MM-dd'T'HH:mm:ss:SSSX";
	
	// Gson used to transform Json to objects or objects to Json.
	protected Gson gson = createGson();
	
	/**
	 * createGson creates and configures a new Gson.
	 * @return Gson that is used for Json conversions.
	 */
	public static Gson createGson() {
		return new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().setDateFormat(FORMAT).create();
	}
	
	/**
	 * save text to the OutputHelper's file.
	 * @param text String to be appended to the file.
	 * @throws IOException is thrown if failure to save to file.
	 */
	public void save(String text) throws IOException {
		LOGGER.debug("Starting to save to file {}: {}", FILE_NAME, text);
		PrintWriter writer = new PrintWriter(getWriter(), false);
		writer.println(text);
		writer.close();
		LOGGER.debug("Saved to file {}: {}", FILE_NAME, text);
	}
	
	/**
	 * getWriter returns a writer specified by the OutputHelper.
	 * @return Writer using the filename and encoding specified by the OutputHelper.
	 * @throws IOException if the creation of the writer failed.
	 */
	public Writer getWriter() throws IOException {
		return new BufferedWriter(new FileWriter(FILE_NAME));
	}
	
	/**
	 * getReader returns a reader specified by the OutputHelper.
	 * @return Reader using the filename and encoding specified by the OutputHelper.
	 * @throws IOException if the creation of the writer failed.
	 */
	public Reader getReader() throws IOException {
		return new StringReader(new String(readAllBytes(get(FILE_NAME)), Charset.forName(ENCODING)).trim());
	}
	
	/**
	 * toJson uses the OutputHelper's Gson to convert an object to Json String.
	 * @param gsonObject Object to be converted to Json String.
	 * @return String Json representation of the supplied object.
	 */
	public String toJson(Object gsonObject) {
		return gson.toJson(gsonObject);
	}
	
	/**
	 * appendSession adds a session to the file.
	 * @param session Session to be appended.
	 * @return List<Session> of all sessions in the file.
	 * @throws IOException if read or save to file failed.
	 */
	public List<Session> appendSession(Session session) throws IOException {
		LOGGER.debug("Appending to file session: {}", session);
		List<Session> result = new ArrayList<Session>(
				Arrays.asList(session));
		List<Session> currentResults = getSavedSessions();
		if (currentResults != null) {
			result.addAll(currentResults);
		}
		this.save(gson.toJson(result));
		return result;
	}
	
	/**
	 * getSavedSessions gets all saved sessions.
	 * @return List<Session> of all Sessions that are saved to file.
	 * @throws IOException when reading file fails.
	 */
	public List<Session> getSavedSessions() throws IOException {
		return getSavedList(Session.class);
	}
	
	/**
	 * getSavedList gets a list of objects located in the file.
	 * @param klazz Class of the objects contained in the list read from the file.
	 * @return List of all objects read from file.
	 * @throws IOException thrown if reading the file failed.
	 */
	protected <T> List<T> getSavedList(Class<T> klazz) throws IOException {
		ArrayList<T> outcome = null;
		JsonReader reader = null;
		try {
			reader = new JsonReader(getReader());
			reader.setLenient(true);
			Type type = new TypeToken<ArrayList<T>>(){}.getType();
			outcome = (ArrayList<T>)gson.fromJson(reader, type);
		} catch(IOException exception){
			LOGGER.warn("Exception {} when reading file: {}", exception, FILE_NAME);
		} finally {
			if(reader != null) {
				reader.close();
			}
		}
		return outcome;
	}

}