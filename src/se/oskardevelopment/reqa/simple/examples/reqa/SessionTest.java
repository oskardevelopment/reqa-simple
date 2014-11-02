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
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;

import se.oskardevelopment.reqa.simple.annotations.Verifiable;
import se.oskardevelopment.reqa.simple.model.Session;
import se.oskardevelopment.reqa.simple.model.Tested;

public class SessionTest {
	
	private static final String METHODS_CAN_BE_VERIFIED_REQ_ID = "Methods can be verified";
	
	private Session session;
	private Description description;
	
	@Before
	@Verifiable(verifies = METHODS_CAN_BE_VERIFIED_REQ_ID)
	public void setup() {
		class EmbeddedTestClass{};
		Method currentMethod = EmbeddedTestClass.class.getEnclosingMethod();
		Annotation[] annotations = currentMethod.getAnnotationsByType(Verifiable.class);
		session = new Session();
		description = Description.createTestDescription(SessionTest.class, currentMethod.getName(), annotations);
	}
	
	@Test
	@Verifiable(verifies = "Session stores tested")
	public void testedCanBeAdded() {
		// given:
		Description uniqueDescription = Description.createTestDescription(Method.class, "getName");
		Description anotherUniqueDescription = Description.createTestDescription(Method.class, "getAnnotations");
		
		// expect:
		assertEquals(Boolean.TRUE, session.getTests().isEmpty());
		
		// when:
		session.updateTested(new Tested(description));
		
		// then:
		assertEquals(1, session.getTests().size());
		
		// when:
		session.updateTested(new Tested(description));
		
		// then:
		assertEquals(1, session.getTests().size());
		
		// when:
		session.updateTested(new Tested(uniqueDescription));
		
		// then:
		assertEquals(2, session.getTests().size());
		
		// when:
		session.addDescription(description);
		
		// then:
		assertEquals(2, session.getTests().size());
		
		// when:
		session.addDescription(anotherUniqueDescription);
		
		// then:
		assertEquals(3, session.getTests().size());
	}
	
	@Test
	@Verifiable(verifies = "Session stores tested")
	public void testedCanBeUpdated() {
		// given:
		Failure failure = new Failure(description, null);
		Tested tested = new Tested(description);
		
		// expect:
		assertEquals(Boolean.TRUE, session.getTests().isEmpty());
		
		// when:
		session.updateTested(tested);
		
		// then:
		assertEquals(1, session.getTests().size());
		assertEquals(Boolean.TRUE, tested.isSuccessful());
		
		// when: 
		session.addFailure(tested.getDescription(), failure);
		
		// then:
		assertEquals(1, session.getTests().size());
		assertEquals(Boolean.FALSE, tested.isSuccessful());
	}
	
	@Test
	@Verifiable(verifies = "Session stores tested")
	public void sessionStartCanBeUpdated() {
		// expect:
		assertEquals(null, session.sessionStart);
		
		// when:
		session.testStarted(description);
		
		// then:
		assertTrue("A started session's sessionStart should not be null", null != session.sessionStart);
	}
	
	@Test
	@Verifiable(verifies = {"Verified session stores verifies", "Session stores tested"})
	public void verifiedSessionHaveVerified() {
		// given:
		Tested tested = new Tested(description);
		session.updateTested(tested);
		
		// expect:
		assertTrue("A non-verified session should not have any Verified", session.verifies.isEmpty());
		
		// when:
		session.verify();
		
		// then:
		assertTrue("A verified session should have verified", !session.verifies.isEmpty());
		assertEquals(1, session.verifies.size());
		assertEquals(1, session.verifies.get(0).tests.size());
		assertEquals(tested, session.verifies.get(0).tests.get(0));
		assertEquals(METHODS_CAN_BE_VERIFIED_REQ_ID, session.verifies.get(0).id);
	}

}
