// script to create the infrastructure for an object:  database, dao, model, service, controller, factory, to

// Steps:
// set paths of example objects to copy from
// check paths to see if any are missing
// for each path, copy file, replace modelname


/**
 * A file which is to be used as a Template for generating classes to support a model
 */
class ExampleFile {
    String srcPath
    List<String> appPath
    List<String> packagePath
    String modelName
    String pattern

    File file

    ExampleFile(String srcPath, List<String> appPath, List<String> packagePath, String modelName, String pattern){
        this.srcPath = srcPath
        this.appPath = appPath
        this.packagePath = packagePath
        this.modelName = modelName
        this.pattern = pattern
    }
    
    String getPath(){
        return pathWithModelName(modelName)
    }

    String pathWithModelName(String modelName){
        return srcPath + appPath.join("/") + "/" + packagePath.join("/") + "/" + modelName + pattern
    }

    File getFile(){
        if(!file){
            file = new File(getPath())
        }
        return file
    }

    boolean isReadable(){
       return (getFile().exists() && file.canRead()) 
    }

    String toString(){
        return getPath() + " " + ((isReadable())?": accessable":"inaccessable")
    }
}

class Templateer{
    /*
     * Set some paths to access the files
     */
    List<String> appPath = ["edu", "sinclair", "ssp"]
    String basePath = "/data/code/infinum/javaWorkspace/ssp/"
    
    private String javaMainPath = basePath + "src/main/java/"
    private String javaTestPath = basePath + "src/test/java/"
    private String resourcesMainPath = basePath + "src/main/resources/"
    private String resourcesTestPath = basePath + "src/test/resources/"

    // The liquibase changelog to add the table.
    String liquibaseChangeLogLocation =  resourcesMainPath + appPath + "database/00.01.0.xml"

    //Turn a string into a camel cased version of itself
    String camelCased(String val){
        StringBuilder sb = new StringBuilder()
        sb.append(val[0].toLowerCase())
        sb.append(val[1 .. val.size()-1])
        return sb.toString()
    }

    /*
     * The model to use as an example, the new model, and the subpackage to place the files
     * Action to perform: create the files, or delete them.
     * Whether to just print results or actually perform them
     */
    public run(String modelName, String newModelName, List<String> subpackage, boolean create, boolean dryRun){
        def exampleFiles = [new ExampleFile(javaMainPath, appPath, ["dao"] + subpackage, modelName, "Dao.java"),
                    new ExampleFile(javaMainPath, appPath, ["model"] + subpackage, modelName, ".java"),
                    new ExampleFile(javaMainPath, appPath, ["service"] + subpackage, modelName, "Service.java"),
                    new ExampleFile(javaMainPath, appPath, ["service"] + subpackage + ["impl"], modelName, "ServiceImpl.java"),
                    new ExampleFile(javaMainPath, appPath, ["web", "api"] + subpackage, modelName, "Controller.java"),
                    new ExampleFile(javaMainPath, appPath, ["factory"] + subpackage, modelName, "TOFactory.java"),
                    new ExampleFile(javaMainPath, appPath, ["factory"] + subpackage + ["impl"], modelName, "TOFactoryImpl.java"),
                    new ExampleFile(javaMainPath, appPath, ["transferobject"] + subpackage, modelName, "TO.java"),
                    new ExampleFile(javaTestPath, appPath, ["dao"] + subpackage, modelName, "DaoTest.java"),
                    new ExampleFile(javaTestPath, appPath, ["service"] + subpackage, modelName, "ServiceTest.java"),
                    new ExampleFile(javaTestPath, appPath, ["factory"] + subpackage, modelName, "TOFactoryTest.java")
                    ]
        
        println "\n\n"
        exampleFiles.each { exampleFile -> 
            println "\n" + exampleFile
            
            if(!exampleFile.isReadable()){
                return
            }

            String newFileName = exampleFile.pathWithModelName(newModelName)
            File newFile = new File(newFileName)
    
            if(create){
                if(newFile.exists()){
                    println "The file $newFileName already exists, skipping"
                }else{
                    println "Writing $newFileName"
                    exampleFile.getFile().eachLine { String line ->
                        String newLine = line.replaceAll(modelName, newModelName)
                        newLine = newLine.replaceAll(camelCased(modelName), camelCased(newModelName))
                        if(dryRun){
                            println newLine 
                        }else{
                            newFile.append(newLine + "\n")
                        }
                    }
                }
            }else{
                if(newFile.exists()){
                    println "Deleting " + newFileName
                    if(!dryRun){
                        newFile.delete()
                    }
                }else{
                    println "File did not exist, cannot delete"
                }
            }
        }   
    }
}


//Syntax and args:
//run(String modelName, String newModelName, List<String> subpackage, boolean create, boolean dryRun){
new Templateer().run("Challenge", "ChildCareArrangement", ["reference"], false, false)





