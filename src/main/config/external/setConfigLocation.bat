# This script is only needed in a development environment for setting the environment
# variable that has the location of the test configuration directory.
#
# This is the windows version of the script.
#
# Optionally, you can simply set the environment variable below in your global
# environment instead of having to run it in each console.

set SSP_TESTCONFIGDIR=C:\\sspConf\\

echo The test configuration file for SSP should be in:
echo %SSP_TESTCONFIGDIR%
echo Please verify the file exists and is populated with the correct values.