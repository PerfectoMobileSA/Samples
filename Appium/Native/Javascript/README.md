- [JS Native app sample](#js-native-app-sample)
  - [Getting started](#getting-started)
  - [Integration With Perfecto](#integration-with-perfecto)
  - [Running the test](#running-the-test)
  - [Help](#help)
  

# JS Native app sample
The project demonstrates a sample native app automation with Perfecto

</br>

## Getting started
- Navigate to Javascript Folder. 
- Run the below command to install node dependencies with this command:

      npm install

</br>

## Integration With Perfecto

1. Open native.js within ios/ android folder.
   
2. Replace <\<cloud name>> with your perfecto cloud name (e.g. demo is the cloudName of demo.perfectomobile.com).

3. Replace <\<security token>> with your perfecto security token.

4. Set device capabilities.

5. Set [Perfecto Media repository path](https://developers.perfectomobile.com/display/TT/Upload+a+file+to+the+repository+via+API+using+Postman+or+cURL) of App under test.

6. Set the unique identifier of your app.

7. Set other capabilities if required.
   
8. Add your code as applicable.

</br>

## Running the test


Run the below command to execute ios sample:

    npm run ios

Run the below command to execute android sample:

    npm run android 

</br>

## Help

Please reach out to [Perfecto support](https://support.perfecto.io) in case of any support.