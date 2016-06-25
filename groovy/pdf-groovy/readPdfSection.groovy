@Grab('com.itextpdf:itextpdf:5.3.2')
import com.itextpdf.text.pdf.parser.*
import com.itextpdf.text.pdf.*
import com.itextpdf.text.Rectangle

def rect = new Rectangle(0, 550, 1000, 800)
def pdf = new PdfReader('groovy2cookbook_chapter1.pdf')

PdfTextExtractor parser = new PdfTextExtractor()

def strategy = new FilteredTextRenderListener(
        new LocationTextExtractionStrategy(),
        new RegionTextRenderFilter(rect))

println parser.getTextFromPage(pdf, 1, strategy)

