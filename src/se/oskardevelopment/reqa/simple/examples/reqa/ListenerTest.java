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
package se.oskardevelopment.reqa.simple.examples.reqa;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;

import se.oskardevelopment.reqa.simple.annotations.Verifiable;
import se.oskardevelopment.reqa.simple.listener.ReqaListener;
import se.oskardevelopment.reqa.simple.listener.SessionListener;
import se.oskardevelopment.reqa.simple.listener.TestListener;
import se.oskardevelopment.reqa.simple.model.Session;
import se.oskardevelopment.reqa.simple.model.Tested;

public class ListenerTest {
	
	private Session session;

	@Before
	public void setup() {
		this.session = mock(Session.class);
	}
	
	@Test
	@Verifiable(verifies = "SessionListener manages the Session during a test's life cycle")
	public void verifySessionListenersManagingOfSession() {
		// given:
		Description description = Description.createTestDescription(ListenerTest.class, "setup");
		SessionListener listener = new SessionListener();
		
		// expect:
		assertEquals(null, listener.getSession());
		
		// when:
		listener.runStarted(session);
		
		// then:
		assertEquals(session, listener.getSession());
		
		// when:
		listener.beforeTest(description);
		
		// then:
		verify(session).testStarted(description);
		
		// when:
		listener.afterTest(description);
		
		// then:
		verify(session).testFinished(description);
	}
	
	@Test
	@Verifiable(verifies = "ReqaListener handles TestListeners during a test session")
	public void verifyReqaListener() throws Exception {
		// given:
		TestListener listener = mock(TestListener.class);
		ReqaListener reqaListener = new ReqaListener(session,  Arrays.asList(listener));
		Description description = Description.createTestDescription(ListenerTest.class, "setup");
		when(session.getTests()).thenReturn(new ArrayList<Tested>());
		
		// when:
		reqaListener.testRunStarted(null);
		
		// then:
		verify(listener).runStarted(session);
		
		// when:
		reqaListener.testStarted(description);
		
		// then:
		verify(listener).beforeTest(description);
		
		// when:
		reqaListener.testFinished(description);
		
		// then:
		verify(listener).afterTest(description);
		
		// when:
		reqaListener.testRunFinished(null);
		
		// then:
		verify(listener).runFinished(null);
		verify(session).verify();
	}
	
	
	
}
