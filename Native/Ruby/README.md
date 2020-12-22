- [Perfecto Sample for Ruby](#perfecto-sample-for-ruby)
  - [Getting Started](#getting-started)
- [Native App](#native-app)
  - [Integration With Perfecto](#integration-with-perfecto)
  - [Running the project](#running-the-project)
  - [Help](#help)

# Perfecto Sample for Ruby

This sample shows how to integrate with Perfecto using ruby language. 

</br>

## Getting Started

- Install Ruby from [here](https://www.ruby-lang.org/en/documentation/installation/)

- Windows only: Run the following commands in command prompt.

      gem install ffi
      gem uninstall -aIx eventmachine
      gem install eventmachine --platform=ruby

- Run the following commands in command line/ terminal:

      gem install appium_lib

</br>

# Native App

## Integration With Perfecto

1. Open native.rb
   
2. Replace <\<cloud name>> with your perfecto cloud name (e.g. demo is the cloudName of demo.perfectomobile.com).

3. Replace <\<security token>> with your perfecto security token.

4. Set device capabilities.

5. Set [Perfecto Media repository path](https://developers.perfectomobile.com/display/TT/Upload+a+file+to+the+repository+via+API+using+Postman+or+cURL) of App under test.

6. Set the unique identifier of your app.

7. Set other capabilities if required.
   
8. Add your code as applicable.

 </br>


## Running the project

- Navigate to Ruby folder.

- Run the below from command line/ terminal to execute iOS sample:

  `ruby ios/native.rb`

- Run the below from command line/ terminal to execute Android sample:

  `ruby android/native.rb`

</br>

## Help

Please reach out to [Perfecto support](https://support.perfecto.io) in case of any support.