@REM
@REM Licensed to Jasig under one or more contributor license
@REM agreements. See the NOTICE file distributed with this work
@REM for additional information regarding copyright ownership.
@REM Jasig licenses this file to you under the Apache License,
@REM Version 2.0 (the "License"); you may not use this file
@REM except in compliance with the License. You may obtain a
@REM copy of the License at:
@REM
@REM http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on
@REM an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied. See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM

# This script is only needed in a development environment for setting the environment
# variable that has the location of the test configuration directory for your console.
# Usage scenarios:  before running mvn, or in tomcat startup script
#
# This is the windows version of the script.
#
# Optionally, you can simply set the environment variable below in your global
# environment instead of having to run it in each console.

set SSP_CONFIGDIR=C:\\sspConf
set SSP_TESTCONFIGDIR=C:\\sspConf

# Uncomment the following to allow SSP to run in absence of uportal in the dev environment
#set spring.profiles.active=dev-standalone

echo The test configuration file for SSP should be in:
echo %SSP_TESTCONFIGDIR% for the TEST environment, and %SSP_CONFIGDIR% for the DEPLOY environment
echo Please verify the file exists and is populated with the correct values.