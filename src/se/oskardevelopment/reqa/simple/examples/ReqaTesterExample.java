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
package se.oskardevelopment.reqa.simple.examples;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import se.oskardevelopment.reqa.simple.ReqaTester;
import se.oskardevelopment.reqa.simple.annotations.Verifiable;
import se.oskardevelopment.reqa.simple.examples.reqa.ScannerTest;
import se.oskardevelopment.reqa.simple.model.Session;
import se.oskardevelopment.reqa.simple.model.Verified;
import se.oskardevelopment.reqa.simple.presenter.Json;
import se.oskardevelopment.reqa.simple.presenter.SessionSaver;
import se.oskardevelopment.reqa.simple.scanner.CurrentPackageScanner;
import se.oskardevelopment.reqa.simple.scanner.PackageScanner;

public class ReqaTesterExample {
	
	@Test
	@Verifiable(verifies = "TEST1EX", gist = "A test example to show the ReQA functionality.")
	public void testExample() {
		// given:
		int one = 1;
		int two = 2;
		int three = 3;
		
		// when:
		int sum = one + two;
		
		// then:
		assertEquals(three, sum);
	}
	
	public static void main(String[] e) {
		ReqaTester reqaTester = null;
		Session session = null;
		
		// Test and verify everything in the project and don't specify a listener.
		session = new ReqaTester().sessionRun();
		System.out.println(session.getVerifies().size()); // 16
		System.out.println(session.getTests().size()); // 20
		
		// Test and verify everything in this class
		reqaTester = new ReqaTester();
		reqaTester.run(ReqaTesterExample.class);
		session = reqaTester.getLastSession();
		Verified verified = session.getVerifies().get(0);
		System.out.println(session.getVerifies().size()); // 1
		System.out.println(session.getTests().size()); // 1
		System.out.println(verified.getTested().size()); // 1
		System.out.println(verified.id); // TEST1EX
		System.out.println(verified.getTested().get(0).test); // ReqaTesterExample.testExample
		
		// Test and verify everything in examples.reqa package
		// and present it with a Json presenter and save session to file.
		session = new ReqaTester(new PackageScanner(ScannerTest.class.getPackage().getName()), new Json(),
				new SessionSaver()).sessionRun();
		System.out.println(session.getVerifies().size()); // 12
		System.out.println(session.getTests().size()); // 15
	}
	
}
