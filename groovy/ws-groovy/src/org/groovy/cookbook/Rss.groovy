@Grab('rome:rome-fetcher:1.0')
def URL = "http://lambda-the-ultimate.org/rss.xml"
//def URL = "http://blogs.oracle.com/java/feed/entries/rss"
def ATOM = "http://blogs.oracle.com/java/feed/entries/atom"


import com.sun.syndication.fetcher.*
import com.sun.syndication.fetcher.impl.*
import com.sun.syndication.feed.synd.SyndFeed

class FeedParser {

    def readFeed( url ) {
        
        def xmlFeed = new XmlParser(false, true).parse(url);
        
        def feedList = []
        
        if (!isAtom(xmlFeed)) {
            (0..< xmlFeed.channel.item.size()).each {

                def item = xmlFeed.channel.item.get(it);
                
                RSSFeedEntry feed = new RSSFeedEntry( item.title.text(), item.link.text(),
                     item.description.text(), item.pubDate.text() )
                 feedList << feed
            }
        } else {
            (0..< xmlFeed.entry.size()).each {
                def entry = xmlFeed.entry.get(it);
                feedList << new AtomFeedEntry( entry.title.text(), entry.author.text(), entry.link.text(), entry.published.text())     
            }

        }

        feedList
    }

    def isAtom(groovy.util.Node node) { 
        def rootElementName = node.name()
        if (rootElementName instanceof groovy.xml.QName) {
            
            return rootElementName.getLocalPart().equals("feed") && 
                rootElementName.getNamespaceURI().equals("http://www.w3.org/2005/Atom");
        }
        false
        
    }
}

abstract class  FeedEntry {

}

@groovy.transform.Canonical
class RSSFeedEntry extends FeedEntry {
  String title
  String link
  String desc
  String pubDate
}

@groovy.transform.Canonical
class AtomFeedEntry extends FeedEntry {
  String title
  String author
  String link
  String pubDate
}



if (true) {
    // Use *Groovy* only code
    def feed = new FeedParser().readFeed (URL)

    feed.each {

        println "${it.title}"

    }
}


// Use Rome framework
FeedFetcher feedFetcher = new HttpURLFeedFetcher();
SyndFeed feed = feedFetcher.retrieveFeed(new URL(ATOM));


feed.entries.each {
   println " -> ${it.title} "
}

println " == ${feed.entries?.size()}"

