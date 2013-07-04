grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir	= "target/test-reports"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits( "global" ) {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {        
        grailsPlugins()
        grailsHome()

        mavenCentral()

    }

	dependencies {
		compile 'com.googlecode.jslint4java:jslint4java-ant:2.0.5'
	}

    plugins{
        build(':release:2.0.4', ':rest-client-builder:1.0.2') {
            export = false
        }
    }
}
