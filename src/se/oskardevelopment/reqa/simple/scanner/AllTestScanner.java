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

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.reflections.util.ClasspathHelper;

/**
 * AllTestScanner is used to scan for all tests found in any package
 * that's used or depended on in the current project.
 */
public class AllTestScanner extends Scanner {

	@Override
	public Set<Class<?>> findClasses() {
		List<Package> pkgs = Arrays.asList(Package.getPackages());
		List<URL> urls = new ArrayList<URL>();
		for(Package pkg : pkgs) {
			urls.addAll(ClasspathHelper.forPackage(pkg.getName()));
		}
		return Scanner.findClasses(urls);
	}

}
