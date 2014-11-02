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
package se.oskardevelopment.reqa.simple.presenter;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.oskardevelopment.reqa.simple.model.Session;
import se.oskardevelopment.reqa.simple.model.Verified;
import se.oskardevelopment.reqa.simple.utility.OutputHelper;

/**
 * The Json presenter is used to tranform a Session
 * to Json format and presenting it by printing the
 * Session to Log4j info log.
 */
public class Json extends Presenter<List<String>> {
	
	// Json's logger.
	private static final Logger LOGGER = LogManager.getLogger();
	
	// OuputHelper that helps transforming session to Json. 
	protected OutputHelper output = new OutputHelper();

	@Override
	protected List<String> createResult(Session session) {
		List<String> results = new ArrayList<String>();
		List<Verified> verifies = session.getVerifies();
		for(Verified verified : verifies) {
			results.add(output.toJson(verified));
		}
		return results;
	}
	
	@Override
	protected void presentResult(List<String> results) {
		for(String result : results) {
			LOGGER.info(result);
		}
	}
	
}