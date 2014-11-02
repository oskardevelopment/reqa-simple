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
package se.oskardevelopment.reqa.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import se.oskardevelopment.reqa.simple.listener.ReqaListener;
import se.oskardevelopment.reqa.simple.listener.TestListener;
import se.oskardevelopment.reqa.simple.model.Session;
import se.oskardevelopment.reqa.simple.presenter.Json;
import se.oskardevelopment.reqa.simple.scanner.AllTestScanner;
import se.oskardevelopment.reqa.simple.scanner.Scanner;

/**
 * ReqaTester is ReQA's version of JUnitCore, used to
 * test and verify given packages. Adds functionality
 * to add listeners, for instance to trigger events during
 * the testing.
 */
public class ReqaTester {
	
	// ReqaTester's logger.
	private static final Logger LOGGER = LogManager.getLogger();
	// Default scanner used by a instantiated ReqaTester.
	protected static Scanner DEFAULT_SCANNER =  new AllTestScanner();
	
	// Scanner used for finding tests.
	protected Scanner scanner;
	// TestListeners that listens on events triggered during testing.
	protected List<TestListener> listeners;
	// Last triggered session.
	protected Session lastSession = null;
	
	/**
	 * ReqaTester constructor used when for default functionality.
	 * No appending TestListeners and using the default scanner.
	 */
	public ReqaTester() {
		this(DEFAULT_SCANNER, new ArrayList<TestListener>());
	}
	
	/**
	 * ReqaTester constructor used when no extra
	 * TestListeners are required though want to
	 * use a custom selected Scanner.
	 * @param scanner Scanner used for finding tests
	 * to verify.
	 */
	public ReqaTester(Scanner scanner) {
		this(scanner, new ArrayList<TestListener>());
	}
	
	/**
	 * ReqaTester constructor used when the default
	 * scanning is sufficient though want to set
	 * what TestListeners are to be used.
	 * @param listeners TestListeners
	 * to be used when triggering during testing.
	 */
	public ReqaTester(TestListener... listeners) {
		this(DEFAULT_SCANNER, Arrays.asList(listeners));
	}
	
	/**
	 * ReqaTester constructor used when the default
	 * scanning is sufficient though want to set
	 * what TestListeners are to be used.
	 * @param listeners List<TestListener> of TestListeners
	 * to be used when triggering during testing.
	 */
	public ReqaTester(List<TestListener> listeners) {
		this(DEFAULT_SCANNER, listeners);
	}
	
	/**
	 * ReqaTester constructor when user wants custom selected
	 * scanner and listeners.
	 * @param scanner Scanner used for finding tests
	 * to verify.
	 * @param listeners TestListeners
	 * to be used when triggering during testing.
	 */
	public ReqaTester(Scanner scanner, TestListener... listeners) {
		this(scanner, Arrays.asList(listeners));
	}
	
	/**
	 * ReqaTester constructor when user wants custom selected
	 * scanner and listeners.
	 * @param scanner Scanner used for finding tests
	 * to verify.
	 * @param listeners List<TestListener> of TestListeners
	 * to be used when triggering during testing.
	 */
	public ReqaTester(Scanner scanner, List<TestListener> listeners) {
		this.scanner = scanner;
		this.listeners = listeners;
	}
	
	/**
	 * run uses the scanner to find classes to test and verify.
	 * @return Result of the run.
	 */
	public Result run() {
		return run(scanner.findClasses().toArray(new Class[0]));
	}
	
	/**
	 * run uses the scanner to find classes to test and verify,
	 * using a custom ReqaListener for triggering events.
	 * @param listener ReqaListener that manages the ReqaTester's
	 * listeners and state.
	 * @return Result describing the outcome of the run.
	 */
	public Result run(ReqaListener listener) {
		return run(listener, scanner.findClasses().toArray(new Class[0]));
	}
	
	/**
	 * run uses the supplied classes to test and verify,
	 * using the ReqaTester's ReqaListener and listeners.
	 * @param klazzez Class<?>[] to test and verify.
	 * @return Result describing the outcome of the run.
	 */
	public Result run(Class<?>... klazzez) {
		Session session = new Session();
		List<TestListener> testListeners = new ArrayList<TestListener>(listeners);
		if(testListeners.isEmpty()) {
			LOGGER.warn("No listener was added to the ReqaTester.");
		}
		ReqaListener listener = new ReqaListener(session, testListeners);
		return run(listener, klazzez);
	}
	
	/**
	 * sessionRun uses the scanner to find classes to test and verify.
	 * @return Session describing the run.
	 */
	public Session sessionRun() {
		run();
		return getLastSession();
	}
	
	/**
	 * sessionRun uses the supplied classes to test and verify,
	 * using the ReqaTester's ReqaListener and listeners..
	 * @return Session describing the run.
	 */
	public Session sessionRun(Class<?>... klazzez) {
		run(klazzez);
		return getLastSession();
	}
	
	/**
	 * sessionRun uses the supplied classes to test and verify,
	 * using the ReqaTester's ReqaListener and listeners.
	 * @param klazzez Class<?>[] to test and verify.
	 * @return Session describing the run.
	 */
	public Session sessionRun(ReqaListener listener, Class<?>... klazzez) {
		run(listener, klazzez);
		return getLastSession();
	}
	
	/**
	 * run with custom ReqaListener and using
	 * the supplied classes to test and verify.
	 * @param listener ReqaListener that manages the ReqaTester's
	 * listeners and state.
	 * @param klazzez Class<?>[] to test and verify.
	 * @return Result describing the outcome of the run.
	 */
	public Result run(ReqaListener listener, Class<?>... klazzez) {
		JUnitCore core = new JUnitCore();
		core.addListener(listener);
		lastSession = listener.getSession();
		LOGGER.debug("Starting a run with #{} classes: {}", klazzez.length, klazzez);
		return core.run(klazzez);
	}
	
	/**
	 * removeListener removes a TestListener from listening.
	 * @param listener TestListener to be removed from listening.
	 * @return boolean true if successfully removed, else false.
	 */
	public boolean removeListener(TestListener listener) {
		return listeners.remove(listener);
	}
	
	/**
	 * addListener adds a new TestListener for listening.
	 * @param listener TestListener to add for listening.
	 * @return boolean true if successfully added, else false.
	 */
	public boolean addListener(TestListener listener) {
		return listeners.add(listener);
	}
	
	/**
	 * getListeners returns all TestListeners listening
	 * on the ReqaTester's run.
	 * @return TestListener[] of all TestListeners that
	 * are listening on the ReqaTester's run.
	 */
	public TestListener[] getListeners() {
		return listeners.toArray(new TestListener[0]);
	}
	
	/**
	 * getLastSession gets the last session triggered by a 
	 * run in ReqaTester.
	 * @return Session describing the ReqaTester's session
	 * of testing and verification.
	 */
	public Session getLastSession() {
		return lastSession;
	}
	
	/**
	 * getScanner gets the Scanner the ReqaTester is configured
	 * to use during its runs.
	 * @return Scanner used by ReqaTester during runs to find
	 * classes to test and verify.
	 */
	public Scanner getScanner() {
		return scanner;
	}
	
	/**
	 * setScanner sets the Scanner that the ReqaTester is to
	 * use when finding classes to test and verify.
	 * @param scanner Scanner that is to be used by ReqaTester.
	 * Should not be null!
	 * @throws NullPointerException if scanner is null.
	 */
	public void setScanner(Scanner scanner) {
		if(scanner == null) {
			throw new NullPointerException("Scanner can't be null!");
		}
		this.scanner = scanner;
	}

}