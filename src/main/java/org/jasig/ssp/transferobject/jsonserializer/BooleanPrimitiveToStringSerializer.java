/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.transferobject.jsonserializer;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;


/**
 * Outputs Java booleans as JSON strings, i.e. "true" or "false", <em>with</em>
 * the wrapping quotes. Exists mainly to preserve backward compatibility
 * in classes which previously hand-rolled their own JSON serialization.
 */
public class BooleanPrimitiveToStringSerializer extends StdSerializer<Boolean> {
	public BooleanPrimitiveToStringSerializer() {
		super(Boolean.TYPE);
	}

	@Override
	public void serialize(Boolean value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		if ( value == null ) {
			jgen.writeString("false");
		} else {
			jgen.writeString(value.toString());
		}
	}
}