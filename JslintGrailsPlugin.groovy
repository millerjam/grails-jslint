class JslintGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.2.1 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]
    
    def scopes = [excludes:'war']

    def author = "James Miller"
    def authorEmail = "jamesmiller01@gmail.com"
    def title = "JsLint for Grails"
    def description = '''\\
A Grails command line script to run JsLint on javascript files. This will run jslint on the web-app/js 
directory when you type in grails jslint.
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/jslint"

    def doWithWebDescriptor = { xml ->
    }

    def doWithSpring = {
    }

    def doWithDynamicMethods = { ctx ->
    }

    def doWithApplicationContext = { applicationContext ->
    }

    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }
}
