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
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.Description;

import se.oskardevelopment.reqa.simple.annotations.Verifiable;
import se.oskardevelopment.reqa.simple.model.Tested;

public class TestedTest {

	@Test
	@Verifiable(verifies = "Tested can be updated")
	public void verifyThatTestedIsUpdated() {
		// given:
		Tested tested = new Tested();
		Tested failedTested = new Tested();
		failedTested.isSuccessful = false;
		
		// then:
		assertEquals(Boolean.TRUE, tested.isSuccessful());
		assertEquals(Boolean.FALSE, failedTested.isSuccessful());
		
		//when:
		tested.update(failedTested);
		
		// then:
		assertEquals(Boolean.FALSE, tested.isSuccessful());
		assertEquals(tested.isSuccessful(), failedTested.isSuccessful());
	}
	
	@Test
	@Verifiable(verifies = "Tested tracks measurements")
	public void verifyDurationIsFinishedSubtractedByStarted() {
		// given:
		Tested tested = new Tested();
		Long duration = 1337L;
		Long startedAt = 1337L;
		Long finishedAt = startedAt + duration;
		
		// when:
		tested.startedAt(startedAt);
		tested.finishedAt(finishedAt);
		
		// then:
		assertEquals(new Date(startedAt), tested.startedAt);
		assertEquals(new Date(finishedAt), tested.finishedAt);
		assertEquals(duration, tested.durationInMillis);
	}
	
	@Test
	@Verifiable(verifies = "Tested describes the test")
	public void verifyTestedVerifies() {
		// given:
		class EmbeddedTestClass{};
		Method currentMethod = EmbeddedTestClass.class.getEnclosingMethod();
		Annotation[] annotations = currentMethod.getAnnotationsByType(Verifiable.class);
		Description description = Description.createTestDescription(this.getClass(), currentMethod.getName(), annotations);
		Tested tested = new Tested(description);
		
		// when:
		List<Verifiable> verifies = tested.verifies();
		
		// then:
		assertTrue("The Tested needs to have a Verifiable.", !verifies.isEmpty());
		assertEquals(annotations.length, verifies.size());
		for(int i = 0; i < verifies.size() ; i++) {
			assertEquals(annotations[i], verifies.get(i));
		}
	}

	
}
