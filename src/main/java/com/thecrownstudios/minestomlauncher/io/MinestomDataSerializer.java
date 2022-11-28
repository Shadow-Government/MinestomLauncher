package com.thecrownstudios.minestomlauncher.io;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.thecrownstudios.minestomlauncher.MinestomData;

import java.io.IOException;

public class MinestomDataSerializer extends StdSerializer<MinestomData> {

	public MinestomDataSerializer() {
		super(MinestomData.class);
	}

	@Override
	public void serialize(MinestomData minestomData, JsonGenerator generator, SerializerProvider provider) throws IOException {
		MinestomData.Network networkData	=	minestomData.networkData();
		MinestomData.Proxy proxyData		=	minestomData.proxyData();
		MinestomData.Server serverData		=	minestomData.serverData();

		generator.writeStartObject();
		generator.writeObjectFieldStart("network");
		generator.writeStringField("ip", "127.0.0.1");
		generator.writeNumberField("port", 25565);
		generator.writeBooleanField("open_to_lan", false);
		generator.writeEndObject();

		generator.writeObjectFieldStart("proxy");
		generator.writeBooleanField("enabled", false);
		generator.writeStringField("type", "");
		generator.writeStringField("secret", "");
		generator.writeEndObject();

		generator.writeObjectFieldStart("server");
		generator.writeNumberField("ticks_per_second", 20);
		generator.writeNumberField("chunk_view_distance", 8);
		generator.writeNumberField("entity_view_distance", 6);
		generator.writeBooleanField("online_mode", false);
		generator.writeBooleanField("optifine_support", true);
		generator.writeBooleanField("terminal", false);
		generator.writeBooleanField("benchmark", false);
		generator.writeEndObject();
		generator.writeEndObject();
	}

}