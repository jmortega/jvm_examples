package org.springframework.samples.petclinic.service

import static org.junit.Assert.*

import org.joda.time.DateTime
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.samples.petclinic.model.Owner
import org.springframework.samples.petclinic.model.Pet
import org.springframework.samples.petclinic.model.PetType
import org.springframework.samples.petclinic.model.Vet
import org.springframework.samples.petclinic.model.Visit
import org.springframework.samples.petclinic.util.EntityUtils
import org.springframework.transaction.annotation.Transactional

import spock.lang.*
abstract class AbstractClinicServiceTest extends Specification {
	
	@Autowired
	ClinicService clinicService;
	
	@Transactional
	def "find owners"(){
		when:
			def owners = clinicService.findOwnerByLastName('Davis')
		then:
			assertEquals(2,owners.size())
		when:
			owners = clinicService.findOwnerByLastName("Daviss")
		then:
			assertEquals(0,owners.size())
	}
	
	@Transactional
	def "find single owners"() {
		when:
			Owner owner1 = this.clinicService.findOwnerById(1);
		then:
			assertTrue(owner1.getLastName().startsWith("Franklin"));
		when:
		Owner owner10 = this.clinicService.findOwnerById(10);
		then:
			assertEquals("Carlos", owner10.getFirstName());
			assertEquals(owner1.getPets().size(), 1);
	}
	
	@Transactional
	def  "insert a owner"() {
		when:
			Collection<Owner> owners = this.clinicService.findOwnerByLastName("Schultz");
			int found = owners.size();
		then:
			found == 0
		when:	
			Owner owner = new Owner();
			owner.setFirstName("Sam");
			owner.setLastName("Schultz");
			owner.setAddress("4, Evans Street");
			owner.setCity("Wollongong");
			owner.setTelephone("4444444444");
			this.clinicService.saveOwner(owner);
			owners = this.clinicService.findOwnerByLastName("Schultz");
		then:
			assertNotEquals("Owner Id should have been generated", owner.getId().longValue(), 0);
			assertEquals("Verifying number of owners after inserting a new one.", found + 1, owners.size());
	}
	
	@Transactional
	def "update a owner"()  {
		when:
			Owner o1 = this.clinicService.findOwnerById(1);
			String old = o1.getLastName();
			o1.setLastName(old + "X");
			this.clinicService.saveOwner(o1);
			o1 = this.clinicService.findOwnerById(1);
		then:
			assertEquals(old + "X", o1.getLastName());
	}

	def "find a pet"() {
		when:
			def types = this.clinicService.findPetTypes();
			def pet7 = this.clinicService.findPetById(7);
		then:
			assertTrue(pet7.getName().startsWith("Samantha"));
			assertEquals(EntityUtils.getById(types, PetType.class, 1).getId(), pet7.getType().getId());
			assertEquals("Jean", pet7.getOwner().getFirstName());
		when:
			Pet pet6 = this.clinicService.findPetById(6);
		then:
			assertEquals("George", pet6.getName());
			assertEquals(EntityUtils.getById(types, PetType.class, 4).getId(), pet6.getType().getId());
			assertEquals("Peter", pet6.getOwner().getFirstName());
	}
	
	
	def "getPetTypes"() {
		when:
			def petTypes = this.clinicService.findPetTypes();
			def petType1 = EntityUtils.getById(petTypes, PetType.class, 1);
			def petType4 = EntityUtils.getById(petTypes, PetType.class, 4);
		then:
			assertEquals("cat", petType1.getName());
			assertEquals("snake", petType4.getName());
	}

	@Transactional
	def  "insert a Pet"() {
		when:
			def owner6 = this.clinicService.findOwnerById(6);
			int found = owner6.getPets().size();
			def pet = new Pet();
			pet.setName("bowser");
			def types = this.clinicService.findPetTypes();
			pet.setType(EntityUtils.getById(types, PetType.class, 2));
			pet.setBirthDate(new DateTime());
			owner6.addPet(pet);
		then:
			assertEquals(found + 1, owner6.getPets().size());
		when:
			this.clinicService.savePet(pet);
			this.clinicService.saveOwner(owner6);
			owner6 = this.clinicService.findOwnerById(6);
		then:
			assertEquals(found + 1, owner6.getPets().size());
			assertNotNull("Pet Id should have been generated", pet.getId());
	}

	@Transactional
	def "update a pet"() throws Exception {
		when:
			Pet pet7 = this.clinicService.findPetById(7);
			String old = pet7.getName();
			pet7.setName(old + "X");
			this.clinicService.savePet(pet7);
			pet7 = this.clinicService.findPetById(7);
		then:
			assertEquals(old + "X", pet7.getName());
	}

	
	def "find vets"() {
		when:
			def vets = this.clinicService.findVets();
			Vet v1 = EntityUtils.getById(vets, Vet.class, 2);
			Vet v2 = EntityUtils.getById(vets, Vet.class, 3);
		then:
			assertEquals("Leary", v1.getLastName());
			assertEquals(1, v1.getNrOfSpecialties());
			assertEquals("radiology", (v1.getSpecialties().get(0)).getName());
			assertEquals("Douglas", v2.getLastName());
			assertEquals(2, v2.getNrOfSpecialties());
			assertEquals("dentistry", (v2.getSpecialties().get(0)).getName());
			assertEquals("surgery", (v2.getSpecialties().get(1)).getName());
	}

	@Transactional
	def "insert a visit"() {
		when:
			def pet7 = this.clinicService.findPetById(7);
			int found = pet7.getVisits().size();
			Visit visit = new Visit();
			pet7.addVisit(visit);
			visit.setDescription("test");
			// both storeVisit and storePet are necessary to cover all ORM tools
			this.clinicService.saveVisit(visit);
			this.clinicService.savePet(pet7);
			pet7 = this.clinicService.findPetById(7);
		then:
			assertEquals(found + 1, pet7.getVisits().size());
			assertNotNull("Visit Id should have been generated", visit.getId());
	}

}
