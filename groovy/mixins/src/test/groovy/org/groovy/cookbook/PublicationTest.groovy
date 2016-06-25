package org.groovy.cookbook

import org.junit.Test
import groovy.time.DatumDependentDuration

class PublicationTest {

  @Test
  void testPublications() {

    def monthly = new DatumDependentDuration(0, 1, 0, 0, 0, 0, 0)
    def daily = new DatumDependentDuration(0, 0, 1, 0, 0, 0, 0)

    def groovyMag = new OnlineMagazine(
                      id: 'GRMAG', 
                      title: 'GroovyMag'
                    ).with {
                      url = new URL('http://grailsmag.com/')
                      issuePeriod = monthly
                      it
                    }

    def time = new MultimediaMagazine(
                 id: 'TIME', 
                 title: 'Time'
               ).with {
                 pageCount = 60
                 url = new URL('http://www.time.com')
                 issuePeriod = monthly
                 it
               }

    def pravda = new Newspaper(
                   id: 'PRVD',
                   title: 'Pravda'
                 ).with {
                   pageCount = 8
                   issuePeriod = daily 
                   it
                 }

    def groovy2cookbook = new Book(
                            id: 'GR2CBOOK',
                            title: 'Groovy 2 Cookbook',
                            pageCount: 384
                          )

    def groovyMag2 = new OnlineMagazine().with {
                      id = 'GRMAG' 
                      title = 'GroovyMag'
                      url = new URL('http://grailsmag.com/')
                      issuePeriod = monthly
                      it
                    }

    assert groovy2cookbook.pageCount == 384
    assert time.pageCount == 60
    assert time.url.toString() == 'http://www.time.com'

  }
  
}
