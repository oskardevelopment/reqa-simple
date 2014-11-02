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

import java.util.EventListener;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import se.oskardevelopment.reqa.simple.model.Session;

/**
 * TestListener is the listener interface that 
 * is used to listen on all testing by the testers.
 * Use when want to get events during testing.
 */
public interface TestListener extends EventListener {
	
	/**
	 * runStarted fires when a session started.
	 * @param session Session that tracks the test runs and verifications.
	 */
	public void runStarted(Session session);
	
	/**
	 * beforeTest fires before a test is started.
	 * @param testDescription Description of the test that is about to start.
	 */
	public void beforeTest(Description testDescription);

	/**
	 * failedTest fires when a test fails.
	 * @param testDescription Description of the test that is about to start.
	 * @param failureDescription Failure describing why the test failed.
	 */
	public void failedTest(Description testDescription, Failure failureDescription);

	/**
	 * afterTest fires after a test is finished.
	 * @param testDescription Description of the test that finished.
	 */
	public void afterTest(Description testDescription);
	
	/**
	 * runFinished fires when a session finished.
	 * @param result Result describing the result of the tests run in the session.
	 */
	public void runFinished(Result result);
	
}