package com.thecrownstudios.minestomlauncher;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public record MinestomData(
        @JsonProperty("network")
        @NotNull Network networkData,

        @JsonProperty("proxy")
        @NotNull Proxy proxyData,

        @JsonProperty("server")
        @NotNull Server serverData,

        @JsonProperty("instance")
        @NotNull Instance instanceData
) {

    public MinestomData() {
        this(
                new MinestomData.Network(),
                new MinestomData.Proxy(),
                new MinestomData.Server(),
                new MinestomData.Instance()
        );
    }

    @JsonRootName("network")
    public record Network(
            @JsonProperty("ip")
            @NotNull String ip,

            @JsonProperty("port")
            @Range(from = 1025, to = 65536) int port,

            @JsonProperty("open_to_Lan")
            boolean openToLan
    ) {
        public Network() {
            this(
                    "127.0.0.1",
                    25565,
                    false
            );
        }
    }

    @JsonRootName("proxy")
    public record Proxy(
            @JsonProperty("enabled")
            boolean enabled,

            @JsonProperty("type")
            @NotNull String type,

            @JsonProperty("secret")
            @NotNull String secret
    ) {
        public Proxy() {
            this(
                    false,
                    "",
                    ""
            );
        }
    }

    @JsonRootName("server")
    public record Server(
            @JsonProperty("ticks_per_second")
            @Range(from = 1,  to = 128) int ticksPerSecond,

            @JsonProperty("chunk_view_distance")
            @Range(from = 2,  to = 32) int chunkViewDistance,

            @JsonProperty("entity_view_distance")
            @Range(from = 2,  to = 32) int entityViewDistance,

            @JsonProperty("online_mode")
            boolean onlineMode,

            @JsonProperty("optifine_support")
            boolean optifineSupport,

            @JsonProperty("terminal")
            boolean terminal,

            @JsonProperty("benchmark")
            boolean benchmark
    ) {
        public Server() {
            this(
                    20,
                    8,
                    6,
                    true,
                    true,
                    false,
                    false
            );
        }
    }

    @JsonRootName("instance")
    public record Instance(
            @JsonProperty("enabled")
            boolean enabled,

            @JsonProperty("type")
            @NotNull String typeName
    ) {
        public Instance() {
            this(
                    false,
                    ""
            );
        }
    }

}