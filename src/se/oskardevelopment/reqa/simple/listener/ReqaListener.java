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
package se.oskardevelopment.reqa.simple.listener;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import se.oskardevelopment.reqa.simple.model.Session;

/**
 * ReqaListener used by ReqaTester to keep
 * track of all tests, the session and the listeners
 * tracking the testing.
 * Create a new ReqaListener for each new test session.
 */
public class ReqaListener extends RunListener {

	// All listeners listening on the testing.
	public List<TestListener> listeners = new ArrayList<TestListener>();
	// Session that contains the test information.
	protected Session session;
	
	/**
	 * ReqaListener constructor with custom selected session and listeners.
	 * @param session Session containing the test and verification information.
	 * @param listeners List<TestListener> of all listening on the testing.
	 */
	public ReqaListener(Session session, List<TestListener> listeners) {
		this.session = session;
		this.listeners.add(new SessionListener());
		this.listeners.addAll(listeners);
	}
	
	/**
	 * ReqaListener constructor that uses a default empty Session and no added listeners.
	 */
	public ReqaListener() {
		this(new Session(), new ArrayList<TestListener>());
	}
	
	/**
	 * getSession gets the current test session.
	 * @return Session tracking the testing and verification.
	 */
	public Session getSession() {
		return session;
	}
	
	@Override
	public void testRunStarted(Description description) {
		// Description is always null from JUnit.
		for(TestListener listener : listeners) {
			listener.runStarted(session);
		}
	}
	
	@Override
	public void testStarted(Description description) throws Exception {
		for(TestListener listener : listeners) {
			listener.beforeTest(description);
		}
		super.testStarted(description);
	}
	
	@Override
	public void testFinished(Description description) throws Exception {
		for(TestListener listener : listeners) {
			listener.afterTest(description);
		}
		super.testFinished(description);
	}
	
	@Override
	public void testFailure(Failure failure) throws Exception {
		for(TestListener listener : listeners) {
			listener.failedTest(failure.getDescription(), failure);
		}
		super.testFailure(failure);
	}
	
	@Override
	public void testRunFinished(Result result) throws Exception {
		session.verify();
		for(TestListener listener : listeners) {
			listener.runFinished(result);
		}
		super.testRunFinished(result);
	}
	
}
