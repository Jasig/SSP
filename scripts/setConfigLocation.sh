#
# Licensed to Jasig under one or more contributor license
# agreements. See the NOTICE file distributed with this work
# for additional information regarding copyright ownership.
# Jasig licenses this file to you under the Apache License,
# Version 2.0 (the "License"); you may not use this file
# except in compliance with the License. You may obtain a
# copy of the License at:
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on
# an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied. See the License for the
# specific language governing permissions and limitations
# under the License.
#

# This script is only needed in a development environment for setting the environment
# variable that has the location of the test configuration directory for your console.
# Usage scenarios:  before running mvn, or in tomcat startup script
#
# This is the unix version of the script.  It has been tested with bash.
#
# Optionally, you can simply set the environment variable below in your global
# environment instead of having to run it in each console.

export SSP_CONFIGDIR=/usr/local/etc/ssp
export SSP_TESTCONFIGDIR=/usr/local/etc/ssp

echo The configuration file for SSP should be in:
echo $SSP_TESTCONFIGDIR for the TEST environment, and $SSP_CONFIGDIR for the DEPLOY environment
echo Please verify the file exists and is populated with the correct values.
