package org.studentsuccessplan.ssp.transferobject.jsonserializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class CodeAndPropertySerializer extends JsonSerializer<CodeAndProperty> {

	@Override
	public void serialize(final CodeAndProperty value,
			final JsonGenerator generator, final SerializerProvider provider)
			throws IOException, JsonProcessingException {
		generator.writeStartObject();
		generator.writeFieldName("code");
		generator.writeString(value.getCode());
		generator.writeFieldName("title");
		generator.writeString(value.getTitle());
		generator.writeEndObject();
	}

}
