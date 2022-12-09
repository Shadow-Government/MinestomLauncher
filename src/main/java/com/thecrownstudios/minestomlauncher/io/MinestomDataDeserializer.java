package com.thecrownstudios.minestomlauncher.io;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.thecrownstudios.minestomlauncher.MinestomData;

import java.io.IOException;

public class MinestomDataDeserializer extends StdDeserializer<MinestomData> {

	public MinestomDataDeserializer() {
		super(MinestomData.class);
	}

	@Override
	public MinestomData deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JacksonException {
		ObjectCodec codec = parser.getCodec();
		JsonNode node = codec.readTree(parser);

		JsonNode networkDataNode	=	node.get("network");
		String ip					=	networkDataNode.get("ip").asText("127.0.0.1");
		int port					=	networkDataNode.get("port").asInt(25565);
		boolean	openToLan			=	networkDataNode.get("open_to_lan").asBoolean(false);

		JsonNode proxyDataNode		=	node.get("proxy");
		boolean proxy_enabled		=	proxyDataNode.get("enabled").asBoolean(false);
		String proxy_type			=	proxyDataNode.get("type").asText();
		String proxy_secret			=	proxyDataNode.get("secret").asText();

		JsonNode serverDataNode		=	node.get("server");
		int tick_per_second			=	serverDataNode.get("ticks_per_second").asInt(20);
		int chunk_view_distance		=	serverDataNode.get("chunk_view_distance").asInt(8);
		int entity_view_distance	=	serverDataNode.get("entity_view_distance").asInt(6);
		boolean online_mode			=	serverDataNode.get("online_mode").asBoolean(true);
		boolean optifine_support	=	serverDataNode.get("optifine_support").asBoolean(true);
		boolean terminal			=	serverDataNode.get("terminal").asBoolean(false);
		boolean benchmark			=	serverDataNode.get("benchmark").asBoolean(false);

		JsonNode instanceDataNode	=	node.get("instance");
		boolean instance_enabled	=	instanceDataNode.get("enabled").asBoolean(false);
		String instance_type		=	instanceDataNode.get("type").asText();

		MinestomData.Network networkData	=	new MinestomData.Network(
				ip,
				port,
				openToLan
		);

		MinestomData.Proxy proxyData		=	new MinestomData.Proxy(
				proxy_enabled,
				proxy_type,
				proxy_secret
		);

		MinestomData.Server serverData		=	new MinestomData.Server(
				tick_per_second,
				chunk_view_distance,
				entity_view_distance,
				online_mode,
				optifine_support,
				terminal,
				benchmark
		);

		MinestomData.Instance instanceData		=	new MinestomData.Instance(
				instance_enabled,
				instance_type
		);

		return new MinestomData(networkData, proxyData, serverData, instanceData);
	}

}