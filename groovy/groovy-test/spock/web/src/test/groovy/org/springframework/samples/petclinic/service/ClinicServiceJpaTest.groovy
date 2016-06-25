package org.springframework.samples.petclinic.service

import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(locations = ["classpath:spring/business-config.xml"])
@ActiveProfiles("jpa")
class ClinicServiceJpaTests extends AbstractClinicServiceTest {
}
