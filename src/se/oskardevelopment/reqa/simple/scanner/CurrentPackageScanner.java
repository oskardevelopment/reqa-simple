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
package se.oskardevelopment.reqa.simple.scanner;

import java.util.Set;

/**
 * CurrentPackageScanner is used to scan for all tests found in the specific
 * package the tester is located in.
 */
public class CurrentPackageScanner extends Scanner {
	
	// Index of the calling stack trace.
	private static final Integer CALLING_STACK_TRACE_ELEMENT_INDEX = 2;
	
	// Calling clazz. The clazz instantiating the CurrentPackageScanner.
	public Class<?> callingClazz;

	/**
	 * Constructor of CurrentPackageScanner. Used to help the scanner
	 * to discover in what package its located in and should search within.
	 * @throws ClassNotFoundException thrown if the calling class can't be found
	 * from its stack trace.
	 */
	public CurrentPackageScanner() throws ClassNotFoundException {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		this.callingClazz = Class.forName(stackTraceElements[CALLING_STACK_TRACE_ELEMENT_INDEX].getClassName());
	}

	@Override
	public Set<Class<?>> findClasses() {
		return Scanner.findClasses(callingClazz.getPackage().getName());
	}

}