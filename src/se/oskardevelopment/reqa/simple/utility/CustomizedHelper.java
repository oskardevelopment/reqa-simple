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
package se.oskardevelopment.reqa.simple.utility;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * CustomizedHelper is an utility class used when
 * more specific requirements of customizing is needed
 * than what is supplied by the OutputHelper.
 */
public class CustomizedHelper extends OutputHelper {
	
	private Writer writer;
	private Reader reader;
	
	/**
	 * CustomizedHelper constructor used for customizing the helper.
	 * @param writer Writer used for all writing.
	 * @param reader Reader used for reading the Json.
	 */
	public CustomizedHelper(Writer writer, Reader reader) {
		this.writer = writer;
		this.reader = reader;
	}
	
	@Override
	public Writer getWriter() throws IOException {
		return writer;
	}
	
	@Override
	public Reader getReader() throws IOException {
		return reader;
	}

}
