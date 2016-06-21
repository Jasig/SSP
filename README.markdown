<!--

    Licensed to Apereo under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Apereo licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License.  You may obtain a
    copy of the License at the following location:

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

-->
# Welcome to the Student Success Portal - Open Source Edition  

### To start...  
 *  developing, look at the documentation in [Developer Install Instructions](https://wiki.jasig.org/display/SSP/SSP+Developer+Installation+Instructions)  
 *  installing, look at the documentation in [Install Docs](https://wiki.jasig.org/display/SSP/SSP+Installation+Documents)   
 
 Please refer to the up to date documentation in [SSP Wiki Home] (https://wiki.jasig.org/display/SSP/Home) 
and if a solution can't be found, the ssp email lists in [SSP Email Lists] (https://wiki.jasig.org/display/SSP/SSP+Email+Lists)

#### Requirements                                                                
*  JDK 1.7.X - Just a JRE may not be sufficient, a full JDK is recommended
*  Servlet 2.5 Container - Tomcat 6.0 is recommended
*  Maven 2.2.1 or later
*  Sencha Tools 2.0.0 or later
*  RDBMS - Postgres 9.1 or later and SQL Server 2008r2 and later are supported
*  [SSP-Platform] (https://github.com/Jasig/SSP-Platform)

SSP uses Maven for its project configuration and build system and Liquibase for it's database management and versioning.

#### Descriptions of files and directories at the root of this project:  
[doc](SSP-Open-Source-Project/tree/master/doc/) - documentation that is relevant to business rules, development, and installation.

[src](SSP-Open-Source-Project/tree/master/src/) - source code to build the application  
[pom.xml](SSP-Open-Source-Project/tree/master/pom.xml) - maven build file  

[externalLibs](SSP-Open-Source-Project/tree/master/externalLibs/) - libraries that are not included in Maven Central and will need to be installed locally  

[scripts](SSP-Open-Source-Project/tree/master/scripts/) - useful scripts for working with the source code as well as maintaining the database  

.classpath - Eclipse project metadata  
.project  
.settings  

.gitignore - files/directories for git to ignore  
README - This document  





