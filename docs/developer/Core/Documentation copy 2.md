# Core



## Sanity Checks of configfile

Configuration file sanity checks
Each plugin is checked in the PluginValidator.
If the configuration is invalid, an IllegalArgumentException is thrown.
If a plugin is developed, the PluginValidator must be adapted

Incompatibility check with incompatibilityMap
Purpose: To ensure that incompatible plugins are not registered together.
Structure: Each plugin has an incompatibilityMap that lists other incompatible plugins with a maximum number of instances:
0 = no instances allowed.
1 = maximum of one instance allowed.
2+ = up to this number allowed.
Abstract plugins can also be included in the map to ensure that the plugin is not compatible with any plugin of a specific type
It is possible for a plugin to include itself in the incompatibilityMap to ensure that only a single instance of the plugin is loaded.
plugin is loaded. This also applies to Abstact plugins

```java
  incompatibilityMap.put(ApplicationPlugin.class, 0);
```