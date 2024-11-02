package ch.hftm.plugin;

public enum PluginType {
    APPLICATION("application"),
    WIDGET("widget"),
    IO("io"),
    FORMAT("format");

    private String typeName;

    PluginType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public static PluginType fromString(String typeName) {
        for (PluginType type : PluginType.values()) {
            if (type.typeName.equalsIgnoreCase(typeName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown plugin type: " + typeName);
    }
}
