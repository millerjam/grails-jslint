jslint.options = "white"
//jslint.directory = "web-app/js/test"	
jslint.includes = "**/*.js"
jslint.excludes = ""
jslint.reports = {


    MyXmlReport('xml') {                    // The report name "MyXmlReport" is user-defined; Report type is 'xml'
        destfile = 'target/test-reports/jslint.xml'  // Set the 'outputFile' property of the (XML) Report
    }
    MyHtmlReport('report') {                  // Report type is 'html'
        destfile = 'target/test-reports/jslint.html'
    }
}