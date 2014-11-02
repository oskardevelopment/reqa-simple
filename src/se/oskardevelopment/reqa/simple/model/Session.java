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
import java.util.Date;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;

import se.oskardevelopment.reqa.simple.annotations.Verifiable;
import se.oskardevelopment.reqa.simple.utility.PrettyPrinter;

import com.google.gson.annotations.Expose;

/**
 * Session represents a testing session.
 * A model keeping track of the tests, what's verified and whatever
 * tracked during the testing.
 */
public class Session {

	// Date when the test session started.
	@Expose
	public Date sessionStart = null;
	// Date when the test session ended.
	@Expose
	public Date sessionEnd = null;
	// What is attempted to be verified during test session.
	@Expose
	public List<Verified> verifies = new ArrayList<Verified>();
	@Expose
	// The tests ran during the test session.
	public List<Tested> tests = new ArrayList<Tested>();
	
	/**
	 * setSessionStart sets the session's start.
	 * @param sessionStart Date when session started.
	 */
	public void setSessionStart(Date sessionStart) {
		this.sessionStart = sessionStart;
	}
	
	/**
	 * setSessionEnd sets the session's finished.
	 * @param sessionEnd Date when session finished.
	 */
	public void setSessionEnd(Date sessionEnd) {
		this.sessionEnd = sessionEnd;
	}
	
	/**
	 * addDescription adds a description of a test to the test session.
	 * Creates a test if no earlier description of the test exists.
	 * @param description Description of a test.
	 */
	public void addDescription(Description description) {
		updateTested(new Tested(description));
	}
	
	/**
	 * addFailure adds a failure description of a test to the test session.
	 * Updates the test with failure information.
	 * @param description Description of the test.
	 * @param failure Failure describing why the test failed.
	 */
	public void addFailure(Description description, Failure failure) {
		updateTested(new Tested(description, failure));
	}
	
	/**
	 * getVerifies gets all the verification that are attempted
	 * during the test session.
	 * @return List<Verified> of all the Verified used for verification.
	 */
	public List<Verified> getVerifies() {
		return verifies;
	}
	
	/**
	 * getTests gets all the tests that ran during the test session.
	 * @return List<Tested> of all tests that ran during the test session.
	 */
	public List<Tested> getTests() {
		return tests;
	}
	
	/**
	 * testStarted starts a test. If the test is the first tested,
	 * the time is given as session end time.
	 * @param description Description of the test that is started.
	 */
	public void testStarted(Description description) {
		if(sessionStart == null) {
			this.sessionStart = new Date();
		}
		Tested test = updateTested(new Tested(description));
		test.startedAt(System.currentTimeMillis());
	}
	
	/**
	 * testFinished finishes a test. If the test is the first tested
	 * the time is given as session end time.
	 * @param description Description describing the test.
	 */
	public void testFinished(Description description) {
		Tested test = updateTested(new Tested(description));
		test.finishedAt(System.currentTimeMillis());
		this.sessionEnd = new Date();
	}
	
	/**
	 * updateTested updates a test's success and failure status.
	 * @param update Tested with the update information.
	 * @return Tested that is stored in the Session.
	 */
	public Tested updateTested(Tested update) {
		if(!tests.contains(update)) {
			tests.add(update);
			return update;
		} else {
			return tests.get(tests.indexOf(update)).update(update);
		}
	}
	
	/**
	 * verify the test session and generate all the Verified
	 * that are used in the verification.
	 */
	public void verify() {
		List<Verified> verifies = new ArrayList<Verified>();
		for(Tested test : getTests()) {
			List<Verifiable> testedVerifiable = test.verifies();
			for(Verifiable verifiable : testedVerifiable) {
				for(String verify : verifiable.verifies()) {
					Verified verified = new Verified(verify);
					if(!verifies.contains(verified)) {
						verifies.add(verified);
					}
					test.setGist(verifiable.gist());
					verifies.get(verifies.indexOf(verified)).addTest(test);
				}
			}
		}
		this.verifies = verifies;
	}
	
	@Override
	public String toString() {
		return PrettyPrinter.prettify(this);
	}
	
}
