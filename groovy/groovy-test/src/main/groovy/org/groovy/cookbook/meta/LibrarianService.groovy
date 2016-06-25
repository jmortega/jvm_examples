package org.groovy.cookbook.meta

class LibrarianService {

	LibraryDao libraryDao
	
	def setLibraryDao(LibraryDao libraryDao){
		this.libraryDao = libraryDao;
	}
	
	def search(String search){
		libraryDao.search(search)
	}
}
