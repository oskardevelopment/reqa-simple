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

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import se.oskardevelopment.reqa.simple.ReqaTester;
import se.oskardevelopment.reqa.simple.model.Session;
import se.oskardevelopment.reqa.simple.model.Tested;
import se.oskardevelopment.reqa.simple.model.Verified;
import se.oskardevelopment.reqa.simple.presenter.Presenter;

public class PresenterExample {
	
	private static final Logger LOGGER = LogManager.getLogger();
	public static String HTML_FILE = "report.html";
	
	public static void main(String[] args) {
		// Anyone can add their own presenters to the ReqaTester's run.
		new ReqaTester(new HtmlPresenter()).sessionRun();
	}
	
	/**
	 * Example of a customized Presenter that creates HTML representation
	 * of the Session and saves it as a HTML document.
	 */
	public static class HtmlPresenter extends Presenter<String> {

		private static final String SYSTEM_SEPARATOR = "line.separator";
		
		@Override
		protected String createResult(Session session) {
			StringBuilder htmlBuilder = new StringBuilder();
			String title = "Requirement Quality Assurance (ReQA) report at " + 
					GregorianCalendar.getInstance().getTime()+".";
			append(htmlBuilder, "<!DOCTYPE html>");
			append(htmlBuilder, "<html>");
			append(htmlBuilder, "<head><title>" + title + "</title></head>");
			addH1(session, htmlBuilder);
			addVerifiable(session, htmlBuilder);
			append(htmlBuilder, "</html>");
			return htmlBuilder.toString();
		}
		
		protected void addH1(Session session, StringBuilder htmlBuilder) {
			append(htmlBuilder, "<h1>Requirement Quality Assurance (ReQA) report</h1>");
			append(htmlBuilder, "<b>"+ new Date() + "</b>");
			append(htmlBuilder, "<p>Verifiable count: "+session.getVerifies().size()+"</p>");
			append(htmlBuilder, "<p>Test count: "+session.getTests().size()+"</p>");
		}

		protected void addVerifiable(Session session, StringBuilder htmlBuilder) {
			if(!session.getVerifies().isEmpty()) {
				append(htmlBuilder, "<h2>Verifiable</h2>");
				for(Verified verifiable : session.verifies) {
					append(htmlBuilder, "<h4>"+verifiable.id+"</h4>");
					append(htmlBuilder, "<p>"+getVerification(verifiable)+"</p>");
					append(htmlBuilder, "<p>Duration: "+verifiable.getDurationInMillis()+" ms</p>");
					List<Tested> tests = verifiable.tests;
					append(htmlBuilder, "<ul>");
					for(Tested test : tests) {
						append(htmlBuilder, "<li>"+test.getPath()+": <b>" + getTestResult(test) + "</b></li>");
					}
					append(htmlBuilder, "</ul>");
				}
			}
		}
		
		private String getVerification(Verified verifiable) {
			return (verifiable.isVerified ? "Successful" : "Failure");
		}
		
		private String getTestResult(Tested test) {
			return (test.isSuccessful() ? "Success" : "Failed");
		}
		
		private void append(StringBuilder builder, String line) {
			builder.append(line);
			builder.append(System.getProperty(SYSTEM_SEPARATOR));
		}
		
		@Override
		public void presentResult(String result) {
			LOGGER.debug(result);
			PrintWriter writer;
			try {
				writer = new PrintWriter(HTML_FILE, "UTF-8");
				writer.print(result);
				writer.close();
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}	
		}

	}


}
