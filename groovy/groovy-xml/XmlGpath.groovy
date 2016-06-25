def groovyMoviez = '''<?xml version="1.0" ?>
    <movie-result>
        <movie id="tt0116288">
            <title>Groovy Days</title>
            <year>1996</year>
            <director>Peter Bay</director>
            <country>Denmark</country>
            <stars>Ken Vedsegaard, Sofie Gråbøl, Martin Brygmann</stars>
        </movie>
        <movie id="tt1189088">
            <title>Cool and Groovy</title>
            <year>1956</year>
            <director>Will Cowan</director>
            <country>USA</country>
            <stars>Anita Day, Buddy De Franco and Buddy DeFranco Quartet</stars>
        </movie>
        <movie id="tt1492859">
            <title>Groovy: The Colors of Pacita Abad</title>
            <year>2005</year>
            <director>Milo Sogueco</director>
            <country>Philippines</country>
            <stars/>
        </movie>
    </movie-result>
'''

def results = new XmlSlurper().parseText(groovyMoviez)
for (flick in results.movie) { 

    println "Movie with id ${flick.@id} is directed by ${flick.director}" 

}

results.movie*.title.each {println "- ${it}"}

results.movie.findAll { it.director.text().contains('Milo')}.each { println "- ${it.title}" }


println "========"

results.depthFirst().findAll { it.director.text().contains('Milo')}.each { println "- ${it.title}" }
