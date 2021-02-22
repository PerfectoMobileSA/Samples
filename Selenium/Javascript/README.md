- [JS Selenium sample](#js-selenium-sample)
  - [Getting started](#getting-started)
  - [Integration With Perfecto](#integration-with-perfecto)
  - [Running the test](#running-the-test)
  - [CI Integration](#ci-integration)
  - [Help](#help)
  

# JS Selenium sample
The project demonstrates a sample selenium automation with Perfecto

</br>

## Getting started
- Navigate to protractor folder in terminal/ command prompt. 
- Run the below command to install node dependencies with this command:

      npm install

</br>

## Integration With Perfecto

1. Open sample.js in terminal/ command prompt.
   
2. Replace <\<cloud name>> with your perfecto cloud name (e.g. demo is the cloudName of demo.perfectomobile.com).

3. Replace <\<security token>> with your perfecto security token.

4. Set preferred device/ web capabilities.
   
5. Add your code as applicable.

</br>

## Running the test

Run the below command to execute the android sample:

    npm run android

Run the below command to execute the web sample:

    npm run web
</br>

## CI Integration

Run the following commands to integrate with Smart Reporting CI Dashboard:

Android:

    jobName=${JOB_NAME} jobNumber=${BUILD_NUMBER} npm run android

Web:

    jobName=${JOB_NAME} jobNumber=${BUILD_NUMBER} npm run web

where \${JOB_NAME} corresponds to job name and \${BUILD_NUMBER} corresponds to job number.

## Help

Please reach out to [Perfecto support](https://support.perfecto.io) in case of any support.