
- [Pre-requisites](#pre-requisites)
- [Setup Project](#setup-project)
- [Integration With Perfecto](#integration-with-perfecto)
- [Running the test](#running-the-test)
- [Help](#help)

## Pre-requisites

* [Install Visual Studio Community Edition 2019](https://visualstudio.microsoft.com/downloads/) or later
* [.NET Framework 4.8 Developer Pack](https://dotnet.microsoft.com/download/dotnet-framework/net48)

</br>  

## Setup Project

* Unzip the C# project. 
* Launch Visual Studio.
* Click on Open a project or solution.
* Navigate to the UnitTesting.sln file in Visual studio and open it.
* Right click on dependencies and select manage nuget packages.
* Install Appium.WebDriver and Microsoft.VisualStudio.UnitTesting under Browse section.
  </br>

## Integration With Perfecto

1. Open native.cs under UnitTesting Solution explorer folder.
   
2. Replace <\<cloud name>> with your perfecto cloud name (e.g. demo is the cloudName of demo.perfectomobile.com).

3. Replace <\<security token>> with your perfecto security token.

4. Set device capabilities.

5. Set [Perfecto Media repository path](https://developers.perfectomobile.com/display/TT/Upload+a+file+to+the+repository+via+API+using+Postman+or+cURL) of App under test.

6. Set the unique identifier of your app.

7. Set other capabilities if required.
   
8. Add your code as applicable.

</br>

## Running the test

* Click on Build -> Build Solution in Visual Studio.
* Click on Test -> Run All Tests to execute the native test in C#.

</br>

## Help

Please reach out to [Perfecto support](https://support.perfecto.io) in case of any support.