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
