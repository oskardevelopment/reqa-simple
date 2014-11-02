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
package se.oskardevelopment.reqa.simple.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;

import se.oskardevelopment.reqa.simple.annotations.Verifiable;
import se.oskardevelopment.reqa.simple.utility.PrettyPrinter;

import com.google.gson.annotations.Expose;

/**
 * Test contains the data of a test.
 * A test is here defined as a method annotated with @Test
 */
public class Tested {
	
	// Tested's logger.
	private static final Logger LOGGER = LogManager.getLogger();
	
	// Name of the test.
	@Expose
	public String test;
	// Gist of what the test verifies.
	@Expose
	public String gist;
	// Date the test started at.
	@Expose
	public Date startedAt = null;
	// Date the test finished at.
	@Expose
	public Date finishedAt = null;
	// Duration the test took from start to finish in milliseconds.
	@Expose
	public Long durationInMillis = null;
	// If the test was successful, true if successful, else false.
	@Expose
	public boolean isSuccessful = true;
	// If the test was skipped. Ignore until further notice.
	@Expose
	public Boolean isSkipped = null;
	
	// The test method.
	protected Method method;
	// Test description.
	protected Description description;
	// Failure description.
	protected Failure failure;

	/**
	 * Tested constructor that's used for Gson compatibility.
	 * Used for internal workings, do not use!
	 */
	public Tested() {}
	
	/**
	 * Tested constructor that is used by a ReqaListener
	 * when the test finished.
	 * @param description Description describing the test.
	 */
	public Tested(Description description) {
		this.description = description;
		try {
			this.method = description.getTestClass().getMethod(description.getMethodName());
			this.test = getPath();
		} catch(Exception exception) {
			LOGGER.error("Could not create method and test of the Tested!", exception);
		}
	}
	
	/**
	 * Tested constructor that is used by a ReqaListener
	 * when the test failed.
	 * @param description Description of the test.
	 * @param failure Failure that describes the failure.
	 */
	public Tested(Description description, Failure failure) {
		this(description);
		this.failure = failure;
		isSuccessful = false;
	}
	
	/**
	 * update the Tested's failure and if successful.
	 * @param tested that is used to update the Tested.
	 * @return Tested that the update is called upon.
	 */
	public Tested update(Tested tested) {
		if(!tested.isSuccessful()) {
			this.failure = tested.failure;
			isSuccessful = tested.isSuccessful();
		}
		return this;
	}
	
	/**
	 * isSuccessful is if the test was successful or not.
	 * @return true if successful, else false.
	 */
	public boolean isSuccessful() {
		return isSuccessful;
	}
	
	/**
	 * getPath creates a readable path to the test. Requires the test to have a description.
	 * @return String as a readable path to the test.
	 */
	public String getPath() {
		try {
			return getDescription().getTestClass().getSimpleName() + "." + method.getName();
		} catch(Exception exception) {
			LOGGER.error("Could not get path Tested!", exception);
			return test;
		}
	}
	
	/**
	 * startedAt sets the started at time.
	 * @param startedAt long milliseconds of the date when the test started.
	 */
	public void startedAt(long startedAt) {
		this.startedAt = new Date(startedAt);
	}
	
	/**
	 * finishedAt sets the finished at time.
	 * @param finishedAt long milliseconds of the date when the test finished.
	 */
	public void finishedAt(long finishedAt) {
		this.finishedAt = new Date(finishedAt);
		durationInMillis = finishedAt - startedAt.getTime();
	}
	
	/**
	 * Sets the gist of what the test verifies.
	 * @param gist String description of what's verified.
	 */
	public void setGist(String gist) {
		if(gist == null || gist.trim().isEmpty()) {
			this.gist = null;
		} else {
			this.gist = gist;
		}
	}
	
	/**
	 * Gets the gist of what the test verifies.
	 * @param gist String description of what's verified.
	 */
	public String getGist() {
		return gist;
	}
	
	/**
	 * getDescription describes the test.
	 * @return Description describing the test.
	 */
	public Description getDescription() {
		return description;
	}

	/**
	 * setDescription sets the Description describing the test.
	 * @param description Description describing the test.
	 */
	public void setDescription(Description description) {
		this.description = description;
	}

	/**
	 * verifies gets all the Verifiables that the test helped verify.
	 * @return List<Verifiable> of all the Verifiable that the test helped verify.
	 */
	public List<Verifiable> verifies() {
		ArrayList<Verifiable> verifiables = new ArrayList<Verifiable>();
		Collection<Annotation> annotations = getDescription().getAnnotations();
		for(Annotation annotation : annotations) {
			if(annotation instanceof Verifiable) {
				verifiables.addAll(Arrays.asList(((Verifiable)annotation)));
			}
		}
		return verifiables;
	}
	
	@Override
	public boolean equals(Object o){
		if(o != null && o.getClass().equals(getClass())) {
			Tested tested = (Tested) o;
			try {
				return tested.getDescription().getTestClass().equals(tested.getDescription().getTestClass()) 
						&& tested.method.equals(method);
			} catch (Exception exception) {
				LOGGER.error("Exception thrown when comparing Tested!", exception);
				return tested.test == test;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		try {
			return getDescription().getTestClass().hashCode() + method.hashCode();
		} catch (Exception exception) {
			LOGGER.error("Exception thrown when generating hashCode of Tested!", exception);
			return test == null ? 1337 : test.hashCode();
		}
	}
	
	@Override
	public String toString() {
		return PrettyPrinter.prettify(this);
	}
    
}
