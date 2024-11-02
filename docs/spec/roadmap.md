---
title: "Roadmap of AnyGUI"
author: "Michael Mueller - michael.mueller@hftm.ch"
date: "2024-08-27"
---
# Roadmap of AnyGUI

The "birth" of AnyGUI is made according to this roadmap.<br />
_Unless otherwise noted, the responsibility applies to the Students._

<a name="at-every-milestone" id="at-every-milestone"></a>

## At every milestone {#at-every-milestone}

Every developer is responsible for implementing tests and documenting the implementations accordingly (!):

### Demo applications
A Demo Application is required for each possible plugin combination. This ensures compatibility and provides
a quick entry point for users of AnyGUI.

### Documentation

- Everyone who implements anything is responsible for adding the corresponding information to the developer, as well as 
  to the user documentations.
- Document implementation-related stuff in the Developer Guide
- Document the most important features in the Quick Start Guides 
- Document every feature in the User Manuals
- Create and implement vector graphics or images where possible
- Create a dedicated User Guide for each Demo Application
- Update ALL documents to keep the content consistent to any changes made in the Application that renders content untrue


### Testing

- Implement a unit test for every single implemented method/function
- To ensure consistency and commpatibility: Implement a unit test for every possible combination of methods which 
  reflects a use case
- Test all scenarios of value updating in both directions:
  - Test updating in GUI, when a value from the IO Plugin (e.g. in file) changes
  - Test updating at the endpoint, when a value update was triggered/sent in the GUI
  - Test concurrent updating scenarios in both directions and verify, if the result complies to the implemented and
    expected behavior
- Verify max. memory usage and max. binary size requirements
- Ensure no memory leak is implemented
- Verify browser compatibility




## Until 2024-09-07

1. [x] Specify the project idea abstractly. Responsible: Michael Mueller
2. [x] Present and explain the project. Responsible: Michael Mueller
3. [x] Clarify questions and ensure everyone understands the goal. Responsible: Everyone.
4. [x] Define responsibilities for the parts: Assign a team member role to each Student
5. [x] Project Setup of "hello world":

    - [x] Maven
    - [x] Evaluate libraries/technologies: Embedded Webserver, Webapplication-Framework, Logging, Testing, TOML parser
    - [x] Embed Webserver 
    - [x] Implement "Hello World" webpage
    - [x] The Port of the embedded Webserver is configurable
    - [x] JavaFX Webview, containing the Webserver's Webpage
    - [x] The application detects the IP Address of the Webserver
    - [x] The JavaFX window shows the address to connect to in the Header (https://\<ip\>:\<port\>)
    - [x] The JavaFX window opens automatically when the application starts
    - [x] A Logger Framework is implemented as well, which:
   
      - [x] logs according to the configured log level 
      - [x] places the logs into a subdirectory `/log/` which is located in the same directory as the binary of the
        application itself
      - [x] creates new logfiles when a new day starts
      - [x] names the logfiles: `<date>_anygui.log`
      - [x] places the Timestamp, Java class, Method, Log Level and the log Message in each log entry
      
    - [x] A Test Framework is implemented, which:
      - [x] Executes all test classes in a subdirectory
      - [x] A Very first Test is implemented to test the testing itself
6. [x] Ensure **each** student can clone/checkout and run this application state successfully


## Until 2024-09-21

Note: Just the very first day (Until 2024-09-07) was described entirely.
From now on, each team member is responsible for taking care fulfilling their own responsibilities!
Means, the roadmap does not contain every single task that needs to be completed, just the goals.
Each team member has to make sure to reach the goals, as well as all additional required tasks requested in the 
specification, the team member role doc, as well as in the doc "Termine, Modalitäten, Bewertungskriterien".<br />

It is also worth mentioning that team members with tasks like evaluating libraries/frameworks need to plan ahead of this
roadmap: They need to provide the results AHEAD of the roadmap deadlines so that their colleagues can use the results
to deliver on time.

1. [ ] Core / Architecture / Interfaces:
   - [ ] A TOML Parsing library is evaluated and implemented 
   - [ ] The configuration TOML file can be read and parsed: The Application searches for a file with the name 
     `anygui_config.tmol` in the same directory as the application is located. If it is not there, the one inside the 
     application's resource folder is used.
   - [ ] Implement notifications so that Widgets can notify/warn
2. [ ] Widget plugin: Textfield, can be added once or an infinite number of times by configuring it in the TOML file
3. [ ] IO plugin: 
   - [ ] File plugin:
     - [ ] With configurable polling interval to read it<br />
       Further configuration options:
       - [ ] If the configured file is missing at all
       - [ ] If the configured file is locked
       - [ ] ... (possible further required configurations, appearing during development)
4. Format plugin: 
   - [ ]  Properties file (key=value)
   - [ ] Implement configuration: Properties file 
   - [ ] Make configurable:
     - [ ] How to handle different Charactersets 
     - [ ] How to handle when the properties contain keys which are not configured (either delete or keep and warn)
     - [ ] How to handle if a configured key is missing in the properties file
     - [ ] ... (possible further required configurations, appearing during development)
5. [ ] Demo Application: Application which shows the Usage of the Textfield Widget Plugin together with the File Polling
   [ ] IO Plugin and the Properties (Key-Value) Format Plugin
6. [ ] Testing: Implement tests for:
   - [ ] Reading and writing to the Properties file
   - [ ] Ensuring that special characters are encoded correctly
   - [ ] Ensuring that the handling of the properties file (Characterset etc.) complies to available standards
   - [ ] Ensuring that proper fail and exception handling is implemented
   - ...

## Until 2024-10-19 
1. [ ] Core: A logo, a header and a footer can be configured.
2. [ ] Widget plugins:
   - [ ] Boolean widget
   - [ ] Text hints functionality: Allows configuring a text hint to each Widget (independent of the Widget type. This 
     functionality needs to be implemented in a way that it is available for all future Widgets as well.)
3. [ ] IO plugin: Extend the File IO Plugin with the option to monitor the file for changes (instead of polling)
4. [ ] Format plugin: A solution to configure the (upcoming) JSON Format Plugin is found (maybe using JSON schema makes sense?).
5. [ ] Demo Applications, Testing and Documentation: Proceed according to described under [At every milestone](#at-every-milestone)

## Until 2024-11-02 
1. Core:
   - [x] Sanity Checks of configfile
   - [ ] Warnings and Exceptions can be displayed
2. [ ] Widget plugins:
   - [ ] Integer and Float widget
   - [ ] Constraints: Allows specifying a min and max allowed value, as well as a range which marks the value with a warning 
     if it falls inside that range and shows an error if it falls outside it
3. [ ] IO plugin: The File IO Plugin can now be optionally configured to read from a different file than it writes to.
4. [ ] Format plugin: JSON
5. [ ] Demo Applications, Testing and Documentation: Proceed according to described under [At every milestone](#at-every-milestone)

## Until 2024-11-23 
1. [ ] Core: It is configurable (can be enabled/disabled): Showing a confirmation popup in the GUI, before made changes are 
   persisted/sent
2. [ ] Widget plugins:
   - [ ] Select-one widget
   - [ ] Select-many widget
3. [ ] IO plugin: (HTTP/S is in work)
4. [ ] Format plugin: CSV
5. [ ] Demo Applications, Testing and Documentation: Proceed according to described under [At every milestone](#at-every-milestone)

## Until 2025-01-11 
1. [ ] Core: A long-time test ran, which ensures that AnyGUI works reliably. (Needs some creative ideas on how to stress it 
   during this time.)
2. [ ] Widget plugins: 
   - [ ] Textarea
   - [ ] String-output (read only)
   - [ ] Constraints: All constraints specified in the specification are implemented. (Solutions to the challenges are found.)
3. [ ] IO plugin: HTTP/S
4. [ ] Format plugin: A solution to configure the (upcoming) TOML Format Plugin is found. 
5. [ ] Demo Applications, Testing and Documentation: Proceed according to described under [At every milestone](#at-every-milestone)


## Until 2025-03-01 
1. [ ] Core: Every found bug that was found at the long-time test is fixed.
2. [ ] Widget plugin: Table/CRUD widget
      - [ ] Multiple entries of a dataset (an Entry can consist of multiple values of various data types)
      - [ ] Allows creating, updating and deleting an entry and list available entries (CRUD functionality)
      - [ ] The available entries are displayed in a table
      - [ ] The value modifying can be done directly inside the table
3. [ ] IO plugin: (Websocket in work)
4. [ ] Format plugin: TOML
5. [ ] Demo Applications, Testing and Documentation: Proceed according to described under [At every milestone](#at-every-milestone)

## Until 2025-03-15 
1. [ ] Core: When the application is built, it creates a dedicated executable for each operating system.
2. [ ] Widget plugin: 
   - [ ] X-Y-Chart (read-only Widget)
   - [ ] Textarea output (read-only Widget), Each 'received' String is added to the Textarea, Can be configured to autoscroll the content
3. [ ] IO plugin: Websocket (WS and WSS)
4. [ ] Format plugin: A configuration is implemented, which uses the File IO Plugin and the TOML Format plugin which allows: 
   Creating / configuring / modifying an AnyGUI configfile, i.e. AnyGUI can be used to create an AnyGUI configfile.
5. [ ] Demo Applications, Testing and Documentation: Proceed according to described under [At every milestone](#at-every-milestone)

## Until 2025-03-22 
1. [ ] Core: Everything is finished, optimized (and bugfixed, if necessary)
2. [ ] Widget plugin: Gauge (read only Widget)
3. [ ] IO plugin: All available ones are finished, optimized (and bugfixed, if necessary)
4. [ ] Format plugin: All available ones are finished, optimized (and bugfixed, if necessary).
5. [ ] Demo Applications, Testing and Documentation: Proceed according to described under [At every milestone](#at-every-milestone)
6. [ ] Review and finish all documents