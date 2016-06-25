@Grab('com.itextpdf:itextpdf:5.3.2')
import com.itextpdf.text.pdf.parser.*
import com.itextpdf.text.pdf.*
    
def pdf = new PdfReader('groovy2cookbook_chapter1.pdf')
def maxPages = pdf.numberOfPages + 1
PdfTextExtractor parser = new PdfTextExtractor()
        
(1..<maxPages).each {
  println parser.getTextFromPage(pdf, it)
}        