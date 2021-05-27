- [Protractor Selenium JS sample](#protractor-selenium-js-sample)
  - [Prerequisite](#prerequisite)
  - [Getting started](#getting-started)
  - [Integration With Perfecto](#integration-with-perfecto)
  - [Running the test](#running-the-test)
  - [CI Integration](#ci-integration)
  - [Help](#help)
  

# Protractor Selenium JS sample
The project demonstrates a sample Protractor selenium automation with Perfecto

</br>

## Prerequisite

The latest version of Node.js 14.x appropriate for your platform
On Windows 7, the latest version of Node.js 12.x is required. Newer versions are not supported.

Install [npm](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm).

## Getting started
- Navigate to protractor folder in terminal/ command prompt. 
- Run the below command to install node dependencies with this command:

      npm install

</br>

## Integration With Perfecto

1. Open android_conf.js/web_config.js within protractor folder in terminal/ command prompt.
   
2. Replace <\<cloud name>> with your perfecto cloud name (e.g. demo is the cloudName of demo.perfectomobile.com).

3. Replace <\<security token>> with your perfecto security token.

4. Set preferred device/ web capabilities.
   
5. Add your code as applicable.

</br>

## Running the test
Navigate to protractor folder in terminal/ command prompt. 
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