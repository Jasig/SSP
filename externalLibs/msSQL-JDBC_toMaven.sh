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

# The SqlServer JDBC driver must be downloaded and separately due to licensing 
# restrictions.
#
# You can get the driver from:  http://msdn.microsoft.com/data/jdbc
#
# Extract the driver, and then install it to your local maven repository (~/.m2)
# with the following line. (It will allow you to build the project.)

mvn install:install-file -Dfile=sqljdbc4-3.0.jar -DgroupId=com.microsoft.sqlserver -DartifactId=sqlserver-jdbc4 -Dversion=3.0 -Dpackaging=jar

# Alternately, if you are just running SSP (not building it), you can drop the 
# JAR file in your application server's "lib" directory.

# If running Groovy scripts from the command line, you must put the JAR file
# in your Groovy user "lib" directory.
