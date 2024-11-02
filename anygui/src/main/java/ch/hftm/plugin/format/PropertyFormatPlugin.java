package ch.hftm.plugin.format;

import ch.hftm.config.ConfigKeysConstants;
import ch.hftm.plugin.io.FileIOPlugin;

import ch.hftm.plugin.io.KeyValueIOPlugin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moandjiezana.toml.Toml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.*;

import java.util.stream.Collectors;

public class PropertyFormatPlugin extends FormatPlugin {

    private static final Logger log = LoggerFactory.getLogger(FileIOPlugin.class);
    private String charset;
    private List<String> allowedProperties;
    private HandleOfNotConfiguredKeys handleOfNotConfiguredKeys;
    private HandleOfMissingKeys handleOfMissingKeys;

    public PropertyFormatPlugin(Toml config) {
        super();
        parseConfig(config);
    }


    @Override
    public void parseConfig(Toml config) {
        charset = config.getString(ConfigKeysConstants.FORMAT_CHARSET);
        allowedProperties = config.getList(ConfigKeysConstants.FORMAT_ALLOWED_PROPERTIES);
        handleOfNotConfiguredKeys = HandleOfNotConfiguredKeys
                .getForType(config.getString(ConfigKeysConstants.FORMAT_HANDLE_OF_NOT_CONFIGURED_KEYS));
        handleOfMissingKeys = HandleOfMissingKeys
                .getForType(config.getString(ConfigKeysConstants.FORMAT_HANDLE_OF_MISSING_KEYS));
    }

    @Override
    public String toString() {
        return "PropertyFormatPlugin{" +
                "charset='" + charset + '\'' +
                ", allowedProperties=" + allowedProperties +
                ", handleOfNotConfiguredKeys=" + handleOfNotConfiguredKeys +
                ", handleOfMissingKeys=" + handleOfMissingKeys +
                '}';
    }

    @Override
    public String sendToCore(String content) {
        Properties properties = new Properties();
        try (InputStream inputStream = new ByteArrayInputStream(content.getBytes(Charset.forName(charset)))) {
            properties.load(inputStream);

            Set<Object> propertyKeys = properties.keySet();
            List<String> notConfiguredKeys = propertyKeys
                    .stream()
                    .filter(key -> !allowedProperties.contains((String) key))
                    .map(key -> (String) key)
                    .toList();

            if (!notConfiguredKeys.isEmpty()) {
                if (handleOfNotConfiguredKeys == HandleOfNotConfiguredKeys.KEEP_AND_WARN) {
                    log.warn("Properties-File contains not configured keys: {}", String.join(", ", notConfiguredKeys));

                }

                if (handleOfNotConfiguredKeys == HandleOfNotConfiguredKeys.DELETE) {
                    notConfiguredKeys.forEach(properties::remove);
                }
            }

            List<String> missingKeys = allowedProperties
                    .stream()
                    .filter(key -> !propertyKeys.contains(key))
                    .toList();

            if (!missingKeys.isEmpty()) {
                if (handleOfMissingKeys == HandleOfMissingKeys.WARN) {
                    log.warn("Properties-File does not contain the following required keys: {}", String.join(", ", missingKeys));

                }

                if (handleOfMissingKeys == HandleOfMissingKeys.EXCEPTION) {
                    throw new IllegalArgumentException("Properties-File does not contain following required keys:" +
                            String.join(", ", missingKeys));
                }
            }

            return new Gson().toJson(propertyKeys
                    .stream()
                    .collect(Collectors.toMap(k -> (String) k, k -> properties.getProperty((String) k))));
        } catch (IOException e) {
            log.error("IOException while reading properties", e);

        }

        return "{}";
    }

    @Override
    public String readFromCore(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> data = gson.fromJson(json, type);

        return data.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private enum HandleOfNotConfiguredKeys {
        DELETE("delete"),
        KEEP_AND_WARN("keepAndWarn");

        private final String type;

        HandleOfNotConfiguredKeys(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public static HandleOfNotConfiguredKeys getForType(String type) {
            return Arrays.stream(values())
                    .filter(h -> h.type.equals(type))
                    .findFirst()
                    .orElse(null);
        }
    }

    private enum HandleOfMissingKeys {
        WARN("warn"),
        EXCEPTION("exception");

        private final String type;

        HandleOfMissingKeys(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public static HandleOfMissingKeys getForType(String type) {
            return Arrays.stream(values())
                    .filter(h -> h.type.equals(type))
                    .findFirst()
                    .orElse(null);
        }
    }

    public static Set<String> retrieveExpectedColumns() {
        Set<String> expectedColumns = FormatPlugin.retrieveExpectedColumns();
        expectedColumns.add(ConfigKeysConstants.FORMAT_CHARSET);
        expectedColumns.add(ConfigKeysConstants.FORMAT_ALLOWED_PROPERTIES);
        expectedColumns.add(ConfigKeysConstants.FORMAT_HANDLE_OF_NOT_CONFIGURED_KEYS);
        expectedColumns.add(ConfigKeysConstants.FORMAT_HANDLE_OF_MISSING_KEYS);
        return expectedColumns;
    }
}
