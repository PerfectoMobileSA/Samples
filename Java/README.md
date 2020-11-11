### Sample Java Native App Project

This sample native app project is designed to get you up and running within few simple steps.

Begin with installing the dependencies below, and continue with the Getting Started procedure below.

### Dependencies
There are several prerequisite dependencies you should install on your machine prior to starting to work with this project:

* [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

* [Maven](https://maven.apache.org/)


**********************

# Getting Started

## Running sample as is

* Open `PerfectoNativeSample.java` file in the `ios` or `android` folder.</p>

* Search for the below line and replace `<<cloud name>>` with your perfecto cloud name (e.g. demo) </br>  
		&nbsp;&nbsp;	&nbsp;&nbsp; String cloudName = `"<<cloud name>>"`;
	</br>
	</p>
* Search for the below line and replace `<<<security token>>` with your perfecto [security token](https://developers.perfectomobile.com/display/PD/Generate+security+tokens) </br></p>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; String securityToken = `"<<<security token>>"`;
	</br>
	</p>
* Set the device capabilities</p>
	
* Set [Perfecto Media repository path](https://developers.perfectomobile.com/display/TT/Upload+a+file+to+the+repository+via+API+using+Postman+or+cURL) of App under test.</p>

* If you have uploaded your own app update the test case</p>

* Run the following maven commands from terminal/command prompt in the base directory:</p>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - For iOS test:

   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; a. If credentials are hardcoded:

    		mvn test -P ios -Dexec.cleanupDaemonThreads=false
    
  
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;b. If credentials are passed as parameters:
		
		mvn clean install test  -P ios -DcloudName=<<cloud name>> -DsecurityToken=<<security token>> -Dexec.cleanupDaemonThreads=false
			
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Note: </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the cloud name of demo.perfectomobile.com)</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2. Replace <<security token>> with your perfecto security token.<p></br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- For Android test:

   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; a. If credentials are hardcoded:

    		mvn test -P android -Dexec.cleanupDaemonThreads=false
    
  
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;b. If credentials are passed as parameters:
		
		mvn clean install test  -P android -DcloudName=<<cloud name>> -DsecurityToken=<<security token>> -Dexec.cleanupDaemonThreads=false


&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Note: </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 1. Replace <<cloud name>> with your perfecto cloud name (e.g. demo is the cloud name of demo.perfectomobile.com)</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2. Replace <<security token>> with your perfecto security token.


