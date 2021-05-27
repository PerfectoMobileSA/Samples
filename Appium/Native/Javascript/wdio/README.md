- [JS Native app sample](#js-native-app-sample)
  - [Prerequisites](#Prerequisites)
  - [Getting started](#getting-started)
  - [Integration With Perfecto](#integration-with-perfecto)
  - [Running the test](#running-the-test)
  - [CI Integration](#ci-integration)
  - [Help](#help)
  

# JS Native app sample
The project demonstrates a sample native app automation with Perfecto

</br>

# Prerequisites

Make sure you have installed:

The latest version of Node.js 14.x appropriate for your platform
On Windows 7, the latest version of Node.js 12.x is required. Newer versions are not supported.

Install npm

## Getting started
- Navigate to wdio folder in terminal/ command prompt. 
- Run the below command to install node dependencies with this command:

      npm install

</br>

## Integration With Perfecto

1. Open conf.js within ios/ android folder in  terminal/ command prompt.
   
2. Replace <\<cloud name>> with your perfecto cloud name (e.g. demo is the cloudName of demo.perfectomobile.com).

3. Replace <\<security token>> with your perfecto security token.

4. Set device capabilities.

5. Set [Perfecto Media repository path](https://developers.perfectomobile.com/display/TT/Upload+a+file+to+the+repository+via+API+using+Postman+or+cURL) of App under test.

6. Set the unique identifier of your app.

7. Set other capabilities if required.
   
8. Add your code as applicable.

</br>

## Running the test

Navigate to wdio folder in terminal/ command prompt. 

Run the below command to execute the iOS sample:

    npm run ios

Run the below command to execute the android sample:

    npm run android


</br>

## CI Integration

Run the following commands to integrate with Smart Reporting CI Dashboard:

ios:

    jobName=${JOB_NAME} jobNumber=${BUILD_NUMBER} npm run ios

android:

    jobName=${JOB_NAME} jobNumber=${BUILD_NUMBER} npm run android

where \${JOB_NAME} corresponds to job name and \${BUILD_NUMBER} corresponds to job number.

## Help

Please reach out to [Perfecto support](https://support.perfecto.io) in case of any support.