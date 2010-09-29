includeTargets << grailsScript("Init")

target(jslint: "Run jslint on javascript files") {
	def jsOptions = ""
	def jsDir = "web-app/js"
	def includesParam = "**/*.js"	
	def excludesParam = ""
	
	parseArguments()
	ant.taskdef ( name : 'jslint' , classname : 'com.googlecode.jslint4java.ant.JSLintTask' , classpath : "lib/jslint4java-1.3.3.jar")
	def jslintConfig = loadConfig()
	
	if(jslintConfig?.jslint) {
		println "loading params from JsLintConfig"		
		jsOptions = jslintConfig.jslint.options	? jslintConfig.jslint.options : jsOptions
		jsDir = jslintConfig.jslint.directory ? jslintConfig.jslint.directory : jsDir
		includesParam = jslintConfig.jslint.includes ? jslintConfig.jslint.includes : includesParam
		excludesParam = jslintConfig.jslint.excludes ? jslintConfig.jslint.excludes : excludesParam
	}
	
	jsOptions = argsMap["o"] ? argsMap["o"] : jsOptions
	jsDir = argsMap["d"] ? argsMap["d"] : jsDir
	includesParam = argsMap["i"] ? argsMap["i"] : includesParam
	excludesParam = argsMap["e"] ? argsMap["e"] : excludesParam
		
	println "running jslint on:" + jsDir
	if(jsOptions) {
		ant.jslint( options:jsOptions ) {
			formatter ( type : "plain" )
			fileset ( dir : jsDir, includes: includesParam, excludes: excludesParam ) 
		}
	} else {
		ant.jslint() {
			formatter ( type : "plain" )
			fileset ( dir : jsDir, includes: includesParam, excludes: excludesParam ) 
		}
	}
	println "jslint done."
}

loadConfig = {
		GroovyClassLoader loader = new GroovyClassLoader(getClass().getClassLoader())
		def configFile = new File("$basedir/grails-app/conf/JsLintConfig.groovy")
		def securityConfig = null
		if( configFile.size() > 0 ) {
			Class clazz = loader.parseClass(configFile)
			securityConfig = new ConfigSlurper().parse(clazz)
		}
		return securityConfig
	}

setDefaultTarget(jslint)

