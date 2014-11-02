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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import se.oskardevelopment.reqa.simple.annotations.Verifiable;
import se.oskardevelopment.reqa.simple.examples.simplest.MathTest;
import se.oskardevelopment.reqa.simple.scanner.CurrentPackageScanner;
import se.oskardevelopment.reqa.simple.scanner.PackageScanner;

public class ScannerTest {

	@Test
	@Verifiable(verifies = "Scanner can get tests in a specific package",
	gist = "PackageScanner scanning a specific package, gives only tested in said package.")
	public void scanningPackageTestsOnlyPackage() {
		// given:
		Package specificPackage = MathTest.class.getPackage();
		String packageName = specificPackage.getName();
		int testClassCount = 1;
		
		// when:
		PackageScanner packageScanner = new PackageScanner(packageName);
		Set<Class<?>> foundTestClasses = packageScanner.findClasses();
		
		// then:
		assertTrue("There should be tests in the package!", !foundTestClasses.isEmpty());
		assertEquals(testClassCount, foundTestClasses.size());
		for(Class<?> klazz : foundTestClasses) {
			assertEquals(specificPackage, klazz.getPackage());
		}
	}
	
	@Test
	@Verifiable(verifies = "Scanner can get tests in current package",
	gist = "With CurrentPackageScanner, then all tested will be in current package.")
	public void scanningCurrentPackageTestsCurrentPackage() throws ClassNotFoundException {
		// given:
		Package currentPackage = this.getClass().getPackage();
		int testClassCount = new PackageScanner(currentPackage.getName()).findClasses().size();
		
		// when:
		CurrentPackageScanner packageScanner = new CurrentPackageScanner();
		Set<Class<?>> foundTestClasses = packageScanner.findClasses();
		
		// then:
		assertTrue("There should be tests in the current package!", !foundTestClasses.isEmpty());
		assertEquals(testClassCount, foundTestClasses.size());
		for(Class<?> klazz : foundTestClasses) {
			assertEquals(currentPackage, klazz.getPackage());
		}
	}

}
