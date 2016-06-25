package org.springframework.samples.petclinic.model

import javax.validation.ConstraintViolation
import javax.validation.Validator

import org.junit.Assert
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.*

@ContextConfiguration(locations = ["ValidatorTest-config.xml"])
class ValidatorTestConfig extends Specification{
	
	@Autowired
	private Validator validator;

	def "validate empty first name"() {
		setup:
			Person person = new Person();
			person.setFirstName("");
			person.setLastName("smith");
		when:
			Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);
		then:
			Assert.assertEquals(1, constraintViolations.size());
			ConstraintViolation<Person> violation =  constraintViolations.iterator().next();
			Assert.assertEquals(violation.getPropertyPath().toString(), "firstName");
	}
}
