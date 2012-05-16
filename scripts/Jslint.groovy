includeTargets << grailsScript("Init")

target(jslint: "Run jslint on javascript files") {
  def jsOptions = ""
  def jsDir = "web-app/js"
  def includesParam = "**/*.js"
  def excludesParam = ""
  def haltOnFailure = null
  def preDef = ""

  parseArguments()
  ant.taskdef(name: 'jslint', classname: 'com.googlecode.jslint4java.ant.JSLintTask')
  def jslintConfig = loadConfig()

  if (jslintConfig?.jslint)
  {
    println "loading params from JsLintConfig"
    jsOptions = jslintConfig.jslint.options ? jslintConfig.jslint.options : jsOptions
    jsDir = jslintConfig.jslint.directory ? jslintConfig.jslint.directory : jsDir
    includesParam = jslintConfig.jslint.includes ? jslintConfig.jslint.includes : includesParam
    excludesParam = jslintConfig.jslint.excludes ? jslintConfig.jslint.excludes : excludesParam
    haltOnFailure = jslintConfig.jslint.haltOnFailure =~ /true|false/ ? jslintConfig.jslint.haltOnFailure : haltOnFailure
    preDef = jslintConfig.jslint.preDef ?: preDef
  }

  preDef = preDef.replaceAll(/\$/,/\$\$/)

  jsOptions = argsMap["o"] ? argsMap["o"] : jsOptions
  jsDir = argsMap["d"] ? argsMap["d"] : jsDir
  includesParam = argsMap["i"] ? argsMap["i"] : includesParam
  excludesParam = argsMap["e"] ? argsMap["e"] : excludesParam
  haltOnFailure = argsMap["h"] =~ /true|false/ ? argsMap["h"] : haltOnFailure


  def options = [:]
  if (jsOptions) options.put("options", jsOptions)
  if (haltOnFailure != null) options.put("haltOnFailure", Boolean.valueOf(haltOnFailure))

  def reports = getConfiguredReports(jslintConfig?.jslint)

  println "Running jslint on:" + jsDir
  ant.jslint(options) {
    ant.predef(preDef)
    reports.each { r ->
	  println "Creating ${r.type} report at ${r.destfile}"
      formatter(type: r.type, destfile: r.destfile)
    }


    fileset(dir: jsDir, includes: includesParam, excludes: excludesParam)
  }
  println "jslint done."
}

loadConfig = {
  GroovyClassLoader loader = new GroovyClassLoader(getClass().getClassLoader())
  def configFile = new File("$basedir/grails-app/conf/JsLintConfig.groovy")
  def securityConfig = null
  if (configFile.size() > 0)
  {
    Class clazz = loader.parseClass(configFile)
    securityConfig = new ConfigSlurper().parse(clazz)
  }
  return securityConfig
}

setDefaultTarget(jslint)


private class ReportsDslDelegate
{
  List reports = []

  def methodMissing(String name, args)
  {
    assert args.size() == 2, "Report name [$name] must specify two parameters: a report type(String or Class) and a Closure"
    assert args[0] instanceof String || args[0] instanceof Class, "Report name [$name] must specify a String or Class report type"
    assert args[0], "The report definition for [$name] must specify the report type that is not empty or null"
    assert args[1] instanceof Closure, "Report name [$name] must be followed by a Closure"
    def reportClosure = args[1]
    def report = new Expando()
    report.type = args[0] instanceof String ? args[0] : args[0].name
    reportClosure.delegate = report
    reportClosure.resolveStrategy = Closure.DELEGATE_FIRST
    reportClosure.call()
    reports << report.properties
  }
}

private List getConfiguredReports(config)
{
  if (config.reports)
  {
    assert config.reports instanceof Closure, "The reports property value must be a Closure"
    def closure = config.reports
    def delegate = new ReportsDslDelegate()
    closure.resolveStrategy = Closure.DELEGATE_FIRST
    closure.delegate = delegate
    closure.call()
    return delegate.reports
  }

}