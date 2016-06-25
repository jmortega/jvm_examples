package org.groovy.cookbook.meta

import org.groovy.cookbook.meta.Ned
import org.groovy.cookbook.meta.NedFlandersCategory;
import org.groovy.cookbook.meta.NedStarkCategory;

class NedsTest extends GroovyTestCase{
	
	void testNeds() {
		def ned = new Ned()
		assertEquals "My name is Ned Doe", ned.fullName("Doe")
		
		use(NedFlandersCategory){
			assert "Hi-dilly-ho,_neighborinos!",ned.fullName("Flanders")
		}
		//Check that outside the category everything is normal
		assertEquals "My name is Ned Doe", ned.fullName("Doe")
		
		use(NedStarkCategory){
			assert "Winter is coming",ned.fullName("Oh no,I'm mocked")
		}
		
		assertEquals "My name is Ned Normal", ned.fullName("Normal")
		
	}
	
}
