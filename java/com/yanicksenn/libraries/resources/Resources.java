package com.yanicksenn.libraries.resources;

import com.google.common.collect.ImmutableSet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public final class Resources {

    /**
     * Parses the resources at the given path line by line. Empty lines and comments are being ignored and don't end up
     * in the result list.
     */
    public static ImmutableSet<String> parseList(Resource resource) throws IOException {
        Objects.requireNonNull(resource);
        String resourcePath = resource.getResourcePath();
        try (var is = resource.getModule().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new FileNotFoundException("Cannot find resource '" + resourcePath + "'");
            }

            ImmutableSet.Builder<String> names = ImmutableSet.builder();
            try (var isr = new InputStreamReader(is); var br = new BufferedReader(isr)) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.isBlank() || isComment(line)) {
                        continue;
                    }

                    names.add(line);
                }
            }

            return names.build();
        }
    }

    public static byte[] parseRaw(Resource resource) throws IOException {
        Objects.requireNonNull(resource);
        String resourcePath = resource.getResourcePath();
        try (var is = resource.getModule().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new FileNotFoundException("Cannot find resource '" + resourcePath + "'");
            }

            return is.readAllBytes();
        }
    }

    private static boolean isComment(String line) {
        return line.matches("\\S*#.*");
    }

    private Resources() {
        throw new AssertionError();
    }
}
