/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/**
 * Created by pspaude
 */
import groovy.io.FileType

println "SSP Script that identifies missing SSP Extjs files from ssp.jsb3 for minification.\n\n"

def console = System.console()
if (console) {
    def userInput = console.readLine ('List full path to SSP with no slash at the end :   ')
    def sspAppDirectory = userInput + "/src/main/webapp/app"
    def sspWebAppDirectory = sspAppDirectory[0..-5]
    def sspJsb = userInput + "/src/main/webapp/ssp.jsb3"
    println "\n Loading files from [$sspAppDirectory] ... "

    def actualFileList = []
    def dir = new File(sspAppDirectory)
    dir.eachFileRecurse (FileType.FILES) { file ->
        actualFileList << file
    }

    //println " List of Actual files in app directory: "
    //actualFileList.each {
    //   println it.path
    //}
    println (" Actual filenames loaded!\n")

    println (" Loading files from [$sspJsb] ...")
    def currentMinifiedFileList = []
    def fileName = ""
    def minifiedFile = new File(sspJsb)
    minifiedFile.eachLine { line ->

        if (line.contains("name")) {
            if (line.contains("all-classes.js") || line.contains("app.js") ||
                    line.contains("All Classes") || line.contains("Application - Production")) {
                //println(" Skipping line: [$line]")
            } else {
                fileName = line.split("\"")[3].trim()
                //println (" FILE = $fileName")
            }

        } else if (line.contains("path")) {
            if (line.split("\"")[3].trim()) {
                //println (" FULLPATH = " + sspWebAppDirectory + line.split("\"")[3].trim() + fileName)
                currentMinifiedFileList << (sspWebAppDirectory + line.split("\"")[3].trim() + fileName)
            }
        } else {
            //do nothing
        }
    }

    //println " List of Files in ssp.jsb3 minified file: "
    //currentMinifiedFileList.each {
    //   println " " + it
    //}
    println (" Minified file filenames loaded!\n")

    println " Comparing filenames ... "
    println " There are: [$actualFileList.size] actual versus [$currentMinifiedFileList.size] in ssp.jsb3. \n"
    def outputFile = "missing_files_from_ssp_minified.txt"
    new File(outputFile).withWriter { out ->
        actualFileList.each {
            if (!currentMinifiedFileList.contains(it.path)) {
                //println " MISSING: [" + it.path.split("webapp")[1] + "]"
                out.println it.path.split("webapp")[1]
            }
        }
    }
    println (" Filename comparison complete!")
    println (" Missing filenames outputted to $outputFile in the directory where this script was ran. \n")

    println "End!"
} else {
    println "\n\n *** Error cannot get console! ***"
}