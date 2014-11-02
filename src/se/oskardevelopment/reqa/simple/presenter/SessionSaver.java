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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.oskardevelopment.reqa.simple.model.Session;
import se.oskardevelopment.reqa.simple.utility.OutputHelper;

/**
 * SessionSaver is a presenter that is used for appending
 * a session to a file.
 * Should be added as a presenter if there's a demand
 * for tracking history or saving a session for future use.
 */
public class SessionSaver extends AbstractPresenter<List<Session>> {
	
	// SessionSaver's logger.
	private static final Logger LOGGER = LogManager.getLogger();
	
	// Helper used for saving.
	protected OutputHelper helper = new OutputHelper();

	@Override
	protected List<Session> createResult(Session session) {
		LOGGER.debug("Saving session {}.", session);
		List<Session> sessions = new ArrayList<Session>();
		try {
			sessions.addAll(helper.appendSession(session));
		} catch (IOException e) {
			LOGGER.error("Failed to save session!", e);
		}
		return sessions;
	}

}