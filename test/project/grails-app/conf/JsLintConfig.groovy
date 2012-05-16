jslint.options = "white,browser,sloppy,maxerr=10000"
jslint.directory = "web-app/js"
jslint.includes = "**/*.js"
jslint.excludes = "**/*.min.js, **/i18n/**/*.js, **/prototype/*.js,**/*-min.js,**/*.pack.js"
jslint.haltOnFailure = false
jslint.preDef = "\$"
jslint.reports = {


    MyXmlReport('xml') {                    // The report name "MyXmlReport" is user-defined; Report type is 'xml'
        destfile = 'target/test-reports/jslint.xml'  // Set the 'outputFile' property of the (XML) Report
    }
    MyHtmlReport('report') {                  // Report type is 'html'
        destfile = 'target/test-reports/jslint.html'
    }
}