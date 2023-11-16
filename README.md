# GrailsApp-SQL-Jasper-Table-Learn
To achieve the functionality of Jasper data into table by using sql database with grails application

## ReportService 
```
package com.iinterchange

import grails.transaction.Transactional

import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource

@Transactional
class JasperReportService {

    def grailsApplication


    
    def generateCombsReport(def data,Map<String, Object> parameters) {
        // Compile the JasperReport template
        def reportPath = grailsApplication.parentContext.getResource("./reports/dailyTimeMenu.jrxml").file.toString()
        def jasperReport = JasperCompileManager.compileReport(reportPath)

        // Fill the report with data and parameters
        def jasperPrint = JasperFillManager.fillReport(
            jasperReport,
            parameters,
            new JRBeanCollectionDataSource(data) //here data - is refer list 
        )

        return jasperPrint
    }
}

```

## Controller : 

```
package com.iinterchange
import grails.transaction.Transactional
import net.sf.jasperreports.engine.JasperExportManager
import com.iinterchange.DayTimeMenu
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource

@Transactional
class DayTimeMenuController {

   def jasperReportService
  

    def index() {
         // Fetch data from the database or any other source
        def menuDetailsList = DayTimeMenu.list()
        JRBeanCollectionDataSource menuDataSource= new JRBeanCollectionDataSource(menuDetailsList)

        // Create parameters map
        def fileParams = [:]

        if (menuDetailsList) {
            // If data is not empty, fill parameters
            fileParams.put("mainDataSet",menuDataSource)
            // Add more parameters as needed
        } else {
            // Add more default values as needed
        }
     
         //  def generateCombsReport(def data,Map<String, Object> parameters)
        // Generate the Jasper report using the service
        def jasperPrint = jasperReportService.generateCombsReport(menuDetailsList, fileParams)

        // Export the report to PDF and send it as the response
        response.setHeader('Content-Disposition', 'inline; filename=ProductReport.pdf')
        response.setContentType('application/pdf')
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.outputStream)
    }
}

```

## Domain Class : 

```
package com.iinterchange

class DayTimeMenu {
    String date
    Integer item_id 
    String item_name 
    Double price 
    Integer unit_sold 
    static constraints = {
        date blank: false
        item_id unique: true, blank: false
        item_name blank: false
        price blank: false, range:0.0..1000.00
        unit_sold range: 50..1000
    }
}

```

