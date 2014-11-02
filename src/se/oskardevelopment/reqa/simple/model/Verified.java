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

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.oskardevelopment.reqa.simple.utility.PrettyPrinter;

import com.google.gson.annotations.Expose;

/**
 * Verified contains information about tests
 * that have been annotated with @Verifies, for instance
 * if the verifies is true or not, and what tests are used
 * for the verification.
 */
public class Verified {

	// Verified's logger.
	private static final Logger LOGGER = LogManager.getLogger();
	
	// ID representing what is being verified.
	// Ex: Feature ID or story name. 
	@Expose
	public String id;
	// isVerified is if its verified.
	// True if all tests are successful, else false.
	@Expose
	public boolean isVerified = true;
	// List of all tests used for verification of the ID.
	@Expose
	public List<Tested> tests = new ArrayList<Tested>();

	/**
	 * Verified constructor used to create a Verified with supplied tests.
	 * @param id String identifier of the Verified.
	 * @param tests List<Tested> tests that is used to verify.
	 */
	public Verified(String id, List<Tested> tests) {
		this(id);
		for(Tested test : tests) {
			addTest(test);
		}
	}
	
	/**
	 * Verified constructor used to create a Verified with
	 * a specific identifier.
	 * @param id String ID representing what is being verified.
	 */
	public Verified(String id) {
		this.id = id;
	}
	
	/**
	 * getDurationInMillis returns the amount of milliseconds
	 * required to test all tests in the Verified.
	 * @return long of duration in milliseconds.
	 */
	public long getDurationInMillis() {
		long duration = 0;
		for(Tested test : tests) {
			duration += test.durationInMillis;
		}
		return duration;
	}

	/**
	 * getTested returns all tests used by the verification.
	 * @return List<Tested> tests used by the verification.
	 */
	public List<Tested> getTested() {
		return tests;
	}
	
	/**
	 * addTest adds a test to be used for the verification.
	 * @param test Tested used for the verification.
	 */
	public void addTest(Tested test) {
		tests.add(test);
		isVerified = isVerified && test.isSuccessful();
	}
	
	/**
	 *  Verified's hashCode identifier is based on the id.
	 */
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	/**
	 *  A Verified is always equal if the identifier is equal.
	 */
	@Override
	public boolean equals(Object o) {
		if(o != null && o.getClass().equals(this.getClass())) {
			return ((Verified)o).id.equals(this.id);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return PrettyPrinter.prettify(this);
	}
	
}
