- [Perfecto Sample for Python](#perfecto-sample-for-python)
  - [Getting Started](#getting-started)
- [Native App](#native-app)
  - [Integration With Perfecto](#integration-with-perfecto)
  - [Running the project](#running-the-project)
  - [Help](#help)

# Perfecto Sample for Python

This sample shows how to integrate Perfecto using python language.

</br>

## Getting Started

Install the following:

1. Python 2+ or 3+
    
    * Windows: 
            Download latest python version from [here](https://www.python.org/downloads/windows/) and install.
    * Mac/ Linux:
            Run `python --version` to see the pre-installed python version. You can also install other versions from [here](https://www.python.org/downloads)

 2. Pip package manager:

    Follow the official documentation from [here](https://pip.pypa.io/en/stable/installing/) to install pip.

 3. Run the following command from terminal/ command prompt to install project dependencies:
    - Mac:
    
          pip3 install selenium==3.141.0
          pip3 install appium-Python-Client==1.0.2

    - Windows:
  
          pip install selenium==3.141.0
          pip install appium-Python-Client==1.0.2

    </br>

# Native App

## Integration With Perfecto

1. Open native.py
   
2. Replace <\<cloud name>> with your perfecto cloud name (e.g. demo is the cloudName of demo.perfectomobile.com).

3. Replace <\<security token>> with your perfecto security token.

4. Set device capabilities.

5. Set [Perfecto Media repository path](https://developers.perfectomobile.com/display/TT/Upload+a+file+to+the+repository+via+API+using+Postman+or+cURL) of App under test.

6. Set the unique identifier of your app.

7. Set other capabilities if required.
   
8. Add your code as applicable.

</br>

## Running the project

- Navigate to Python folder.

- Run the below from command line/ terminal to execute iOS sample:

  Mac: 
    
      python3 ios/native.py

  Windows:
  
      python ios/native.py

- Run the below from command line/ terminal to execute Android sample:

  Mac: 

      python3 android/native.py

  Windows: 
  
      python android/native.py

</br>

## Help

Please reach out to [Perfecto support](https://support.perfecto.io) in case of any support.