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

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * PrettyPrinter is used when converting a object to a pretty String.
 * Should be used in toString methods in objects that preferably are Gson objects. 
 */
public class PrettyPrinter {
	
	// PrettyPrinter's logger.
	private static final Logger LOGGER = LogManager.getLogger();
	
	/**
	 * prettify converts an object as a good looking String.
	 * @param object Object to be prettified.
	 * @return String representing the object.
	 */
	public static String prettify(Object object) {
		try {
			StringBuilder pretty = new StringBuilder(object.getClass().getSimpleName());
			pretty.append("(");
			Field[] fields = object.getClass().getFields();
			for(int i = 0; i < fields.length; i++) {
				String prettifiedField = prettifyField(fields[i], object);
				pretty.append(prettifiedField.trim().isEmpty() ? "" : (i != 0 ? ", " : "") + prettifiedField);
			}
			pretty.append(")");
			return pretty.toString();
		} catch(IllegalArgumentException | IllegalAccessException exception) {
			LOGGER.error("Could not prettify: {}, threw exception: {}", object.getClass(), exception);
			return OutputHelper.createGson().toJson(object);
		}
	}
	
	/**
	 * prettifyField prettifies a field to a good looking String.
	 * @param field Field that is to be prettified.
	 * @param object Object that owns the field.
	 * @return String representing the field.
	 * @throws IllegalArgumentException if use of field is errenous.
	 * @throws IllegalAccessException if use of field is errenous.
	 */
	private static String prettifyField(Field field, Object object) throws IllegalArgumentException, IllegalAccessException {
		Object value = field.get(object);
		Type type = field.getType();
		StringBuilder fieldBuilder = new StringBuilder();
		if(value != null) {
			fieldBuilder.append(field.getName());
			fieldBuilder.append(": ");
			if(type.getTypeName().equals(List.class.getName())) {
				List<?> list = (List<?>)value;
				if(list.isEmpty()) {
					return "";
				}
				fieldBuilder.append(prettifyList(list).toString());
			} else {
				fieldBuilder.append(field.get(object));
			}
		}
		return fieldBuilder.toString();
	}
	
	/**
	 * prettifyList prettifies a field that is a List to a good looking String.
	 * @param list List that is contained in a field.
	 * @return String representing the field.
	 * @throws IllegalArgumentException if use of a field is erroneous.
	 * @throws IllegalAccessException if use of a field is erroneous.
	 */
	private static String prettifyList(List<?> list) throws IllegalArgumentException, IllegalAccessException {
		StringBuilder prettyBuilder = new StringBuilder();
		prettyBuilder.append("[");
		Object[] objects = list.toArray();
		for(int i = 0; i < objects.length; i++) {
			if(i != 0) {
				prettyBuilder.append(", ");
			}
			Field importantField = objects[i].getClass().getFields()[0];
			prettyBuilder.append(objects[i].getClass().getSimpleName());
			prettyBuilder.append("(");
			prettyBuilder.append(importantField.getName());
			prettyBuilder.append(": \'");
			prettyBuilder.append(importantField.get(objects[i]));
			prettyBuilder.append("\')");
		}
		prettyBuilder.append("]");
		return prettyBuilder.toString();
	}
	
}
