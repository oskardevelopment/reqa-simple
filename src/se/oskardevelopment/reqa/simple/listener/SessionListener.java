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

import java.util.Date;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import se.oskardevelopment.reqa.simple.model.Session;

/**
 * SessionListener is the internal listener used
 * to track the test results in the session.
 */
public class SessionListener implements TestListener {

	// Session used to track testing.
	private Session session;
	
	/**
	 * getSession gets the SessionListener's session.
	 * @return Session that the SessionListener is managing.
	 */
	public Session getSession() {
		return session;
	}
	
	@Override
	public void runStarted(Session session) {
		this.session = session;
		session.setSessionStart(new Date());
	}

	@Override
	public void beforeTest(Description testDescription) {
		session.testStarted(testDescription);
	}

	@Override
	public void failedTest(Description testDescription,
			Failure failureDescription) {
		session.addFailure(testDescription, failureDescription);
	}

	@Override
	public void afterTest(Description testDescription) {
		session.testFinished(testDescription);
	}

	@Override
	public void runFinished(Result result) {
		session.setSessionEnd(new Date());
	}

}
