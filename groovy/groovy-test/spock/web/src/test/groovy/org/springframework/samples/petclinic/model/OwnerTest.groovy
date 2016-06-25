package org.springframework.samples.petclinic.model

import spock.lang.Specification

class OwnerTest extends Specification {
	
	def "test pet and owner" () {
		given:
			def p = new Pet()
			def o = new Owner()
		
		when:
		    p.setName("fido")
		then:
		    o.getPet("fido") == null
			o.getPet("Fido") == null
		when:
			o.addPet(p)
		then:
			o.getPet("fido").equals(p)
			o.getPet("Fido").equals(p)		   
	}
 
}
