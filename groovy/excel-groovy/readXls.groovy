@Grab('org.apache.poi:poi:3.8')
@Grab('org.apache.poi:poi-ooxml:3.8')
@GrabExclude('xml-apis:xml-apis')
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFSheet

def excelFile = new File('Workbook1.xlsx')
excelFile.withInputStream { is ->
  workbook = new XSSFWorkbook(is)       
  (0..<workbook.numberOfSheets).each { sheetNumber ->
    XSSFSheet sheet = workbook.getSheetAt( sheetNumber )
    sheet.rowIterator().each { row ->
      row.cellIterator().each { cell ->
        println cell.toString()
      }
    }
  }
}


