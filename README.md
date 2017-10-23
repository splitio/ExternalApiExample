# Split Api Example

This repository contains examples for using the External Split API using JAVA 8

_Gradle needs to be installed_

## For running examples for Split

_gradle runSplitExample -PadminToken="ADMIN_TOKEN"_

This script will **CREATE and **DELETE** a Split of traffic type ***user*** in your Organization. If your Organization
does not have a traffic type ***user***, change it on *SplitExample.java*.

## For running examples for Split Definition

_gradle runSplitDefinitionExample -PadminToken="ADMIN_TOKEN"_

It is worth noting that it will perform a series of actions, including creating some Splits in the ***Staging*** environment. If you want
to change the environment or the traffic type, change it on *SplitDefinitionExample.java*

