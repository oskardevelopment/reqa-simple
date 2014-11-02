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

import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;

import se.oskardevelopment.reqa.simple.annotations.Verifiable;

/**
 * Scanner is used for all scanners that are used by the testers
 * to find all the classes that are to be tested in a test run.
 */
public abstract class Scanner {

	/**
	 * findClasses returns all the classes that are found by the scanner.
	 * Used by the testers to see what classes that are to be tested.
	 * @return Set<Class<?>> of all classes to be tested.
	 */
	public abstract Set<Class<?>> findClasses();

	/**
	 * findClasses all the classes that are found within a list of packages.
	 * @param packageLocations List<URL> of all URLs to the package locations
	 *  to be searched through.
	 * @return Set<Class<?>> of all unique classes that are to be tested found
	 * within the given packages.
	 */
	public static final Set<Class<?>> findClasses(List<URL> packageLocations) {
		Reflections reflections = new Reflections(new ConfigurationBuilder()
        .addUrls(packageLocations)
        .setScanners(new TypeAnnotationsScanner(), 
                     new SubTypesScanner(),
        			 new MethodAnnotationsScanner()));
		return Scanner.findClasses(reflections);
	}
	
	/**
	 * findClasses all the classes that are found by a reflections specification.
	 * @param reflections Reflections describing where to look for to be tested classes.
	 * @return Set<Class<?>> of all unique classes that are to be tested found by
	 * the describing reflections.
	 */
	public static final Set<Class<?>> findClasses(Reflections reflections) {
		Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(Verifiable.class);
		Set<Method> annotatedMethods = reflections.getMethodsAnnotatedWith(Verifiable.class);
		annotatedMethods.addAll(reflections.getMethodsAnnotatedWith(Test.class));
		for(Method method : annotatedMethods) {
			annotatedClasses.add(method.getDeclaringClass());
		}
		return annotatedClasses;
	}
	
	/**
	 * findClasses all the classes that are found that have a specific prefix.
	 * @param prefix String that is to be of all classes' packages to be tested.
	 * @return Set<Class<?>> of all unique classes within packages with the given prefix.
	 */
	public static final Set<Class<?>> findClasses(String prefix) {
		Reflections reflections = new Reflections(prefix, new TypeAnnotationsScanner(), 
            new SubTypesScanner(),
  			new MethodAnnotationsScanner());
		return Scanner.findClasses(reflections);
	}
	
}
