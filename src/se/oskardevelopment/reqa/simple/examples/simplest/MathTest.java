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
package se.oskardevelopment.reqa.simple.examples.simplest;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import se.oskardevelopment.reqa.simple.annotations.Verifiable;

public class MathTest {
	
	@Test
	@Verifiable(verifies = "Java can subtract numbers.", 
			gist = "By subtracting ten with five you get a total value of five.")
	public void subtractionWithPositiveValues() {
		// given:
		int ten = 10;
		int five = 5;
		
		// when:
		int total = ten - five;
		
		// then:
		assertEquals(total, five);
	}

	@Test
	@Verifiable(verifies = "Java can subtract numbers.", 
			gist = "By subtracting minus five with minus twenty you get fifteen.")
	public void subtractionWithNegativeValues() {
		// given:
		int minusTwenty = -20;
		int minusFive = -5;
		int fifteen = 15;
		
		// when:
		int total = minusFive - minusTwenty;
		
		// then:
		assertEquals(total, fifteen);
	}
	
	@Test
	@Verifiable(verifies = "Java can add numbers.", 
			gist = "By adding twenty and twenty two you get forty two.")
	public void addWithPositiveValues() {
		// given:
		int twenty = 20;
		int twentyTwo = 22;
		int fortyTwo = 42;
		
		// when:
		int total = twenty + twentyTwo;
		
		// then:
		assertEquals(total, fortyTwo);
	}
	
	@Test
	@Verifiable(verifies = "MATH2PI")
	public void mathLibaryHasPi() {
		// expect:
		assertEquals(3, (int)Math.PI);
	}

}
