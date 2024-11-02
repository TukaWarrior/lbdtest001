package ch.hftm.plugin;

import com.moandjiezana.toml.Toml;

import java.util.LinkedHashMap;

public abstract class Plugin {
    private final PluginType type; // Field to store the specific plugin type
    // Map to store plugin incompatibilities with other plugins
    // Key: Plugin class, Value: Max number of compatible instances (0: none, 1: one instance, 2: two instances, etc.)
    // Used to check plugin compatibility
    protected LinkedHashMap<Class<? extends Plugin>, Integer> incompatibilityMap = new LinkedHashMap<>();


    // Constructor to initialize the plugin with its type
    public Plugin(PluginType type) {
        this.type = type;
    }

    // Getter for Plugin-Typ
    public PluginType getType() {
        return type;
    }

    public abstract void parseConfig(Toml config);

    // Returns compatibility map for inter-plugin compatibility checks
    public LinkedHashMap<Class<? extends Plugin>, Integer> getIncompatibilityMap() {
        return incompatibilityMap;
    }
}
