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
package se.oskardevelopment.reqa.simple.presenter;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import se.oskardevelopment.reqa.simple.listener.TestListener;
import se.oskardevelopment.reqa.simple.model.Session;

/**
 * Presenter is used for creating a result and presenting said
 * result when all tests have been completed.
 * @param <A> value that is created and used for the presentation.
 */
public abstract class Presenter<A> implements TestListener {
	
	// Session used by the Presenter for creating the result.
	protected Session session;

	/**
	 * setSession sets the session that should be used by the Presenter to create the result.
	 * @param session Session that will be used by the Presenter.
	 */
	public void setSession(Session session) {
		this.session = session;
	}
	
	/**
	 * getSession returns the session used by the Presenter.
	 * @return Session used by the Presenter.
	 */
	public Session getSession() {
		return session;
	}
	
	@Override
	public void runStarted(Session session) {
		this.session = session;
	}

	@Override
	public void beforeTest(Description testDescription) {}

	@Override
	public void failedTest(Description testDescription,
			Failure failureDescription) {}

	@Override
	public void afterTest(Description testDescription) {}

	@Override
	public void runFinished(Result result) {
		presentResult(createResult());
	}
	
	/**
	 * createResult uses the Presenter's session to create a result when all testing is finished.
	 * @return Result created by the Presenter from the Session.
	 */
	public A createResult() {
		return createResult(getSession());
	}
	
	/**
	 * createResult uses the Presenter's session to create a result when all testing is finished.
	 * @param session Session used by the Presenter to create the result.
	 * @return Result created by the Presenter from the Session.
	 */
	protected abstract A createResult(Session session);
	
	/**
	 * presentResult presents the created result after all testing is finished.
	 * @param result The created result by the Presenter.
	 */
	protected abstract void presentResult(A result);

}