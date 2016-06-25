import groovy.xml.StreamingMarkupBuilder

def carXml = '''<?xml version="1.0" ?>
<cool-cars>
  <car manufacturer="Ferrari">
    <model>430 Scuderia</model>
  </car>
  <car manufacturer="Porsche">
    <model>911</model>
  </car>
  <car manufacturer="Lotus">
    <model>Elan</model>
  </car>
  <car manufacturer="Pagani">
    <model>Huayra</model>
  </car>
</cool-cars>'''

def coolCars = new XmlParser().parseText(carXml)

println "============================== MODIFY"

// Alter the model of Lotus
coolCars.car[2].model[0].value = 'Elise'

// Alter the type of Ferrari
coolCars.find { it.@manufacturer == 'Ferrari' }.model[0].value = 'Testarossa'

// Alter the maker (Change attribute)
coolCars.car[1].@manufacturer = 'Ford'

new XmlNodePrinter().print(coolCars)

println "============================== NOW DELETE"

coolCars.remove(coolCars.car[1])

coolCars.findAll { it.@manufacturer.startsWith('P') }.each { coolCars.remove(it) }

new XmlNodePrinter().print(coolCars)