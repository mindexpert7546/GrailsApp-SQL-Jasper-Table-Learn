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