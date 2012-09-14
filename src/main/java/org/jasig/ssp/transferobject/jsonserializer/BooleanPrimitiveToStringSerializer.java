package org.jasig.ssp.transferobject.jsonserializer;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;

import java.io.IOException;

/**
 * Outputs Java booleans as JSON strings, i.e. "true" or "false", <em>with</em>
 * the wrapping quotes. Exists mainly to preserve backward compatibility
 * in classes which previously hand-rolled their own JSON serialization.
 */
public class BooleanPrimitiveToStringSerializer extends SerializerBase<Boolean> {
	public BooleanPrimitiveToStringSerializer() {
		super(Boolean.TYPE);
	}
	@Override
	public void serialize(Boolean value, JsonGenerator jgen,
						  SerializerProvider provider)
			throws IOException, JsonProcessingException {
		if ( value == null ) {
			jgen.writeString("false");
		} else {
			jgen.writeString(value.toString());
		}
	}
}
