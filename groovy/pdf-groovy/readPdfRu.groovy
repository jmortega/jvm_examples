@Grab('com.itextpdf:itextpdf:5.3.2')
import com.itextpdf.text.pdf.parser.*
import com.itextpdf.text.pdf.*
    
def pdf = new PdfReader('groovy2cookbook_chapter1_ru.pdf')
def maxPages = pdf.numberOfPages + 1
PdfTextExtractor parser = new PdfTextExtractor()
        
new File('output.txt').withWriter('KOI8-R') { writer ->
  (1..<maxPages).each {
    writer << parser.getTextFromPage(pdf, it)
  }        
}