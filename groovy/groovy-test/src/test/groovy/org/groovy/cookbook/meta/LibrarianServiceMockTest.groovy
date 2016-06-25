package org.groovy.cookbook.meta

import org.groovy.cookbook.meta.LibrarianService;
import org.groovy.cookbook.meta.LibraryDao;
import org.junit.Test;

class LibrarianServiceMockTest extends GroovyTestCase{
	
	
	@Test
	void testSearch(){
		LibrarianService librarianService = new LibrarianService()
		LibraryDao mockLibrarianDao = [
			search: {searchText ->
				if(searchText.startsWith('Norwegian')){
					["Norwegian Wood,Tokyo Blues",'Verbs And Essentials of Norwegian Grammar','A night as a Norwegian'].sort()
				}else{
					[]
				}
			}
		] as LibraryDao;
 
		librarianService.setLibraryDao(mockLibrarianDao)
		
		def results = librarianService.search('Norwegian');
		
		assertEquals 'A night as a Norwegian', results[0]
		assertEquals 'Norwegian Wood,Tokyo Blues', results[1]
	}
		
	

}
