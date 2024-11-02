---
title: "Team member roles for initial development of AnyGUI"
author: "Michael Mueller - michael.mueller@hftm.ch"
date: "2024-08-27"
---

# Team member Roles

_There's intentionally no project lead. Everybody is responsible by themselves to contribute to the project._

The fact that each team member has to contribute a certain amount of time and effort balances possible imbalances in
the roles out: As everybody has to move over and help overloaded colleagues, when done with all own tasks too soon.


## Core, JavaFX, embedded Webserver, entry-point, building and interface developer

- Number of required students for this role: 3 - 5
- Suitable for students with specialization: SW, (SY, WI)
- Responsibilities:
  - You're responsible that AnyGUI can be executed, i.e. you build the main-entry logic and maintain it throughout the
    development of AnyGUI.
  - You develop and maintain the JavaFX application part
  - You implement and maintain the embedded webserver
  - You Specify and implement interfaces between the Plugins and ensure they're semantically correct and future proof
    for upcoming plugins.
  - You constantly optimize the Building/Deploying, so that other developers can efficiently build and run AnyGUI and
    that AnyGUI can be used as convenient as possible. (e.g. find ways to build simple to use binaries, 
    like .exe for Windows)
  - You implement configuration interfaces in all the software parts you are responsible for and implement them together 
    with the configuration handler developer 
  - [Responsibilities of each team member](#Responsibilities-of-each-team-member) apply to you as well


## Webapplication / Businesslogic developer

- Number of required students for this role: 4 - 6
- Suitable for students with specialization: SW
- Responsibilities:
  - You implement the webapplication
  - You are responsible for how the GUI generally is styled and looks
  - You implement the configurations which affect the webapplication, e.g. the configuration of a specific logo or text
  - You work closely with the Widget Plugins developers to implement the widgets according to the configuration
  - You work closely with the configuration handler developer and the Widget Plugins developers to deal with the most 
    complicated configuration part of the application: The used widgets configurations, as well as the configured constraints.
  - [Responsibilities of each team member](#Responsibilities-of-each-team-member) apply to you as well


## Configuration handler developer

- Number of required students for this role: 2
- Suitable for students with specialization: SW, (SY, WI)
- Responsibilities:
  - You implement the `anygui_config.toml` reading and parsing
  - You build an interface to all parts of AnyGUI to push each configuration entry to the corresponding application part
  - You implement a verification logic to ensure the configuration is valid
  - You constantly update the entire logic and communicate with the other team members to ensure the configuration
    never becomes the project-bottleneck
  - [Responsibilities of each team member](#Responsibilities-of-each-team-member) apply to you as well


## Widget Plugins developer

- Number of required students for this role: 5 - 6
- Suitable for students with specialization: SW
- Responsibilities:
  - You work closely with the Webapplication / Businesslogic developers to ensure the Widgets are handled properly
    and consistently across all Widget Plugins, while the most complicated part will probably be the handling
    of the configured constraints.
  - You implement and maintain an interface from and to the related parts of AnyGUI: Embedded Webserver, Format Plugins,
    IO Plugins.
  - You implement the configurations, together with the configuration handler developer and ensure
    to provide a consistent interface across all Widget Plugins
  - [Responsibilities of each team member](#Responsibilities-of-each-team-member) apply to you as well


## Format Plugins developer

- Number of required students for this role: 2 - 4
- Suitable for students with specialization: SW
- Responsibilities:
  - You implement all the Format Plugins and ensure they're always in a consistent state, even when the interfaces change
  - You implement the required encoding and decoding
  - You ensure to implement these encodings/decodings to comply with the corresponding standards
  - You implement the configuration of the Format Plugins, together with the configuration handler developer and ensure
    to provide a consistent interface across all Format Plugins
  - You're ready to take the challenge of finding a solution which allows configuring the value mapping of various data 
    formats.
  - You ensure compatibility to other Plugins and implement a compatibility list in each Format Plugin
  - [Responsibilities of each team member](#Responsibilities-of-each-team-member) apply to you as well


## IO Plugins developer

- Number of required students for this role: 2
- Suitable for students with specialization: SW, (SY, WI)
- Responsibilities:
  - You implement all the IO Plugins and ensure they're always in a consistent state, even when the interfaces change
  - You implement a control interface to communicate with the Webapplication / Businesslogic
  - You implement the configuration of the IO Plugins, together with the configuration handler developer and ensure to provide a consistent interface across all IO Plugins
  - You ensure compatibility to other Plugins and implement a compatibility list in each IO Plugin
  - [Responsibilities of each team member](#Responsibilities-of-each-team-member) apply to you as well


## Technology / Library evaluation, Compliance, Validation, Quality and Automation

- Number of required students for this role: 3 - 4
- Suitable for students with specialization: (SW), SY, WI
- Responsibilities:
  - You evaluate the required technologies and libraries and implement example codes or tests to demonstrate the
    functioning. This allows the other developers to implement the required plugins or features in AnyGUI
    without any incidents and minimizes risks of wasting time by using the wrong technologies or libraries.<br />
    **Important: With this task, you need to always be at least one step ahead of all the others, as you need to provide
    an evaluation-document and example code with the recommended technologies/libraries right _before_ they are needed.**
  - You ensure and document _constantly_ that the implementations meet the standards:
    E.g. that we handle characters and character escapings according to the corresponding standard.
    Or that a Websocket implementation is inline with the corresponding standard.
  - You verify the requirements according to the specification and the roadmap _constantly_ to ensure they're met.
    You automate these verifications whenever possible and document the results in the Git repository.
    If they're no longer meet, you actively start to investigate it, develop and document a solution in the Git
    repository and inform the corresponding developer/s to fix it.
  - You're responsible to implement automation mechanisms which ensure code quality, consistency and correctness.
    For instance, you implement GitHub actions which ensure all tests pass before a Pull Request is merged.
  - [Responsibilities of each team member](#Responsibilities-of-each-team-member) apply to you as well


## Responsibilities of each team member

In addition to the specific responsibilities, each team member is responsible for:

1. Implementing tests:
    - Implement a unit test for every single implemented method/function
    - To ensure consistency and compatibility: Implement a unit test for every possible combination of methods which
      reflects a use case
    - Test all scenarios of value updating in both directions
    - Test IO Plugins as well. For instance, a HTTPS endpoint is required to properly implement a unit test for the
      HTTPS IO plugin. This needs to be implemented in a way that the unit test includes this when it is executed.
2. Document made implementations accordingly: Quick Start Guide, User Manual, Developer Manual
3. Implement a Demo Application for each implemented plugin and possible combination with other plugins
4. Create a Documentation for each made Demo Application
5. Review code of others (Review, comment and approve Pull Requests of other team members).<br />
   It is important to review pull requests of others asap to avoid drifts and resulting merge conflicts as
   much as possible.
6. Act flexible according to simple rules:
   a) The only thing that counts is _directly_ adding value to the project.
   b) Only contributing to the GitHub repository is considered as directly adding value.
   c) Implement tests and write documentations is part of each developer.
   d) Once a developer is done with anything: Take over tasks from others (!)
7. If you find something contradicting or entirely unclear in the specification/roadmap or another document, don't
   hesitate to write to: michael.mueller@hftm.ch