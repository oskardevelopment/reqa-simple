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

import org.junit.Test;

import se.oskardevelopment.reqa.simple.annotations.Verifiable;
import se.oskardevelopment.reqa.simple.model.Tested;
import se.oskardevelopment.reqa.simple.model.Verified;

public class VerifiedTest {
	
	private static final String VERIFIED_ID = "A test ID";
	
	private Verified verified = new Verified(VERIFIED_ID);
	private Verified anotherVerifiedWithSameId = new Verified(VERIFIED_ID);
	private Verified anotherVerifiedDifferentId = new Verified("Another test ID");
	
	@Test
	@Verifiable(verifies = "Verified have equals and hashCode")
	public void verifyEquals() {
		assertTrue("Verified should equal itself", verified.equals(verified));
		assertTrue("Two Verified with different ID should not be equal", !verified.equals(anotherVerifiedDifferentId));
		assertTrue("Two Verified with same ID should be equal equal", verified.equals(anotherVerifiedWithSameId));
	}
	
	@Test
	@Verifiable(verifies = "Verified have equals and hashCode")
	public void verifyHashCode() {
		assertEquals(verified.hashCode(), verified.hashCode());
		assertTrue("Two Verified with different ID should not have same hashCode", verified.hashCode() != anotherVerifiedDifferentId.hashCode());
		assertTrue("Two Verified with same ID should have same hashCode", verified.hashCode() == anotherVerifiedWithSameId.hashCode());
	}
	
	@Test
	@Verifiable(verifies = "Verified have list of all tested")
	public void verifyTested() {
		//then:
		assertEquals(verified.getTested().isEmpty(), Boolean.TRUE);
		
		//when:
		verified.addTest(new Tested());
		int tested = verified.getTested().size();
		
		//then:
		assertEquals(verified.getTested().isEmpty(), Boolean.FALSE);
		assertEquals(tested, verified.getTested().size());
		assertEquals(tested, 1);

		//when:
		verified.addTest(new Tested());
		
		//then:
		assertEquals(verified.getTested().isEmpty(), Boolean.FALSE);
		assertTrue("Adding more tests should increase the amount tested", verified.getTested().size() > tested);
		assertEquals(tested + 1, verified.getTested().size());
	}
	
	@Test
	@Verifiable(verifies = "Verified tracks the duration of all tested")
	public void verifyDuration() {
		//given:
		long durationTime = 1337L;
		Tested testedWithDuration = new Tested();
		testedWithDuration.durationInMillis = durationTime;
		
		//then:
		assertEquals(0, verified.getDurationInMillis());

		//when:
		verified.addTest(testedWithDuration);
		
		//then:
		assertEquals(durationTime, verified.getDurationInMillis());
	}
}
