package com.bluesky.jct.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class GsonMessageBodyHandler implements MessageBodyWriter<Object>,
		MessageBodyReader<Object> {

	private static final String UTF_8 = "UTF-8";

	private Gson gson;

	private Gson getGson() {
		if (gson == null) {
			GsonBuilder gsonBuilder = new GsonBuilder()
			                .registerTypeAdapter(IntegerProperty.class, integerSerializer)
			                .registerTypeAdapter(IntegerProperty.class, integerDeserializer)
							.registerTypeAdapter(StringProperty.class, stringSerializer)
							.registerTypeAdapter(StringProperty.class, stringDeserializer);
			gson = gsonBuilder.create();
		}
		return gson;
	}
	
	
	@Override
	public boolean isReadable(Class<?> arg0, Type arg1, Annotation[] arg2, MediaType arg3) {
		return true;
	}
	

	@Override
	public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream) 
					throws IOException, WebApplicationException {
		InputStreamReader streamReader = new InputStreamReader(entityStream, UTF_8);
		
		try {
			Type jsonType;
			if (type.equals(genericType)) {
				jsonType = type;
			} else {
				jsonType = genericType;
			}
			return getGson().fromJson(streamReader, jsonType);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			streamReader.close();
		}
	}

	
	@Override
	public long getSize(Object arg0, Class<?> arg1, Type arg2, Annotation[] arg3, MediaType arg4) {
		return -1;
	}

	
	@Override
	public boolean isWriteable(Class<?> arg0, Type arg1, Annotation[] arg2,	MediaType arg3) {
		return true;
	}

	
	@Override
	public void writeTo(Object object, Class<?> type, Type genericType,	Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) 
					throws IOException,	WebApplicationException {
		OutputStreamWriter writer = new OutputStreamWriter(entityStream, UTF_8);
		
		try {
			Type jsonType;
			if (type.equals(genericType)) {
				jsonType = type;
			} else {
				jsonType = genericType;
			}
			getGson().toJson(object, jsonType, writer);
		} finally {
			writer.close();
		}
	}
	
	
	JsonSerializer<IntegerProperty> integerSerializer = new JsonSerializer<IntegerProperty>() {
		@Override
		public JsonElement serialize(IntegerProperty src, Type typeOfSrc, JsonSerializationContext context) {
			return src == null ? null : new JsonPrimitive(src.intValue());
			}

/**		
			if (src == null) {
				return null;
			} else {
	
				try {
//					String intString = src.toString();
					
//					return new SimpleIntegerProperty(new Integer(intString).intValue());
					return src == null ? null : new JsonPrimitive(src.toString());
	
				} catch (Exception e) {
					return null;
				}
			}
		}
*/		
	};

	
	JsonDeserializer<IntegerProperty> integerDeserializer = new JsonDeserializer<IntegerProperty>() {
		@Override
		public IntegerProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

			if (json == null) {
				return null;
			} else {

				try {
					String intString = json.getAsString();
					
					return new SimpleIntegerProperty(new Integer(intString).intValue());

				} catch (Exception e) {
					return new SimpleIntegerProperty(0);
				}
			}
		}
	};

	
	JsonSerializer<StringProperty> stringSerializer = new JsonSerializer<StringProperty>() {
		@Override
		public JsonElement serialize(StringProperty src, Type typeOfSrc, JsonSerializationContext context) {
			return src == null ? null : new JsonPrimitive(src.get());
		}
	};
	
	
	JsonDeserializer<StringProperty> stringDeserializer = new JsonDeserializer<StringProperty>() {
		@Override
		public StringProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
	
			if (json == null) {
				return null;
			} else {
	
				try {
					return new SimpleStringProperty(json.getAsString());
				} catch (Exception e) {
					return new SimpleStringProperty();
				}
			}
		}
	};
}