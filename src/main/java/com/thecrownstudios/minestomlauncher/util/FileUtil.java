package com.thecrownstudios.minestomlauncher.util;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.thecrownstudios.minestomlauncher.MinestomData;
import com.thecrownstudios.minestomlauncher.io.MinestomDataDeserializer;
import com.thecrownstudios.minestomlauncher.io.MinestomDataSerializer;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public final class FileUtil {

    private FileUtil() {}

    public static @NotNull ObjectTriple<FileResult, MinestomData, Exception> loadMinestomDataFromFile(@NotNull String file) {
        File minestomDataFile = new File(file);
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("MinestomDataSerializer");

        module.addDeserializer(MinestomData.class, new MinestomDataDeserializer());
        module.addSerializer(MinestomData.class, new MinestomDataSerializer());
        mapper.registerModule(module);

        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        ObjectReader reader = mapper.reader();

        // if file has been created write the default
        // data into the config and return it
        if (createFileIfNeeded(minestomDataFile)) {
            try {
                MinestomData minestomData = new MinestomData();
                writer.writeValue(minestomDataFile, new MinestomData());
                return ObjectTriple.of(FileResult.CREATED, minestomData, null);
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }

        // if the file already exists deserialize it
        try {
            MinestomData minestomData = reader.readValue(minestomDataFile, MinestomData.class);
            return ObjectTriple.of(FileResult.EXISTING, minestomData, null);
        } catch (Exception exc) {
            return ObjectTriple.of(FileResult.MALFORMED, null, exc);
        }
    }

    private static boolean createFileIfNeeded(@NotNull File file) {
        //if (!file.exists()) {
        try {
            return file.createNewFile();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
        //}

        //return false;
    }

}