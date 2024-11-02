package ch.hftm.plugin.format;

import ch.hftm.config.ConfigKeysConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moandjiezana.toml.Toml;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.Set;

public class JSONFormatPlugin extends FormatPlugin {
    private static final Logger log = LoggerFactory.getLogger(JSONFormatPlugin.class);

    private String schemaFileName;

    public JSONFormatPlugin(Toml config) {
        parseConfig(config);
    }

    @Override
    public void parseConfig(Toml config) {
        this.schemaFileName = config.getString(ConfigKeysConstants.FORMAT_JSON_SCHEMA_FILE);

    }

    @Override
    public String sendToCore(String content) {
        if (!validateSchema(content)) {
            throw new IllegalArgumentException("JSON-Input is not valid with the given JSON-Schema");
        }

        return content;
    }

    @Override
    public String readFromCore(String json) {
        if (!validateSchema(json)) {
            throw new IllegalArgumentException("JSON-Input is not valid with the given JSON-Schema");
        }
        return json;
    }

    boolean validateSchema(String content) {
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        JsonSchema jsonSchema = factory.getSchema(getClass().getResourceAsStream("/" + schemaFileName));
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode jsonNode = mapper.readTree(new StringReader(content));
            Set<ValidationMessage> errors = jsonSchema.validate(jsonNode);

            if (!errors.isEmpty()) {
                errors.stream()
                        .map(ValidationMessage::toString)
                        .forEach(e -> log.warn("JSON-SCHEMA validation error", e));
                return false;
            }

        } catch (IOException e) {
            log.error("Exception in JSON Schema validation", e);
            return false;
        }

        return true;
    }

    public static Set<String> retrieveExpectedColumns() {
        Set<String> expectedColumns = FormatPlugin.retrieveExpectedColumns();
        return expectedColumns;
    }
}
