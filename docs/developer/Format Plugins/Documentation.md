# Format
## General
All format plugins have 2 methods (#sendToCore, #readFromCore) to map the communication paths between core and IOPlugin.

## PropertyFormatPlugin
The PropertyFormatPlugin was implemented as the first plugin. This receives a string in property format in the #sendToCore method and converts it into JSON, which is passed on to the core. In the #readFromCore method, a string in JSON format is expected, which in turn is transformed into the property format and passed on to the IO plugin.

### Example
Property-String:

```
devicename = test
value = test-value
```

JSON-String

```
{
  "devicename": "test",
  "value": "test-value"
}
```

## JSONFormatPlugin
A json schema file can be specified in the toml config: jsonSchemaFile  
The JSON input is validated via the defined JSON schema. If this is valid, the JSON is currently passed on 1:1, as communication with the core is also based on JSON.