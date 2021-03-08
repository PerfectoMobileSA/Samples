- [Sample Java Selenium App Project](#sample-java-selenium-app-project)
	- [Dependencies](#dependencies)
- [Getting Started](#getting-started)
	- [Running sample as is](#running-sample-as-is)
	- [CI integration](#ci-integration)
	- [Help](#help)

# Sample Java Selenium App Project

This sample Java Selenium project is designed to get you up and running within few simple steps.

Begin with installing the dependencies below, and continue with the Getting Started procedure below.

## Dependencies
Install the following dependencies on your machine prior as a pre-requisite:

* [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

* [Maven](https://maven.apache.org/)


**********************

# Getting Started

## Running sample as is

* Open `Sample.java` inside `android/ web` folder.</p>

* Search for the below line and replace `<<cloud name>>` with your perfecto cloud name (e.g. demo) </br>  
		&nbsp;&nbsp;	&nbsp;&nbsp; String cloudName = `"<<cloud name>>"`;
	</br>
	</p>
* Search for the below line and replace `<<<security token>>` with your perfecto [security token](https://developers.perfectomobile.com/display/PD/Generate+security+tokens) </br></p>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String securityToken = `"<<<security token>>"`;
	</br>
	</p>
* Set the device/ web capabilities.</p>

* Run the following maven commands from terminal/command prompt in the base Java directory:</p>
    

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- For Android test:

		mvn clean install -P android 

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- For Web test:

		mvn clean install -P web 

## CI integration

Run the following commands to integrate with Smart Reporting CI Dashboard:

Android:

    mvn clean install -P android -DjobName=${JOB_NAME} -DjobNumber=${BUILD_NUMBER} 

Web:

    mvn clean install -P web -DjobName=${JOB_NAME} -DjobNumber=${BUILD_NUMBER} 

where \${JOB_NAME} corresponds to job name and \${BUILD_NUMBER} corresponds to job number.
  
## Help

Please reach out to [Perfecto support](https://support.perfecto.io) in case of any support.