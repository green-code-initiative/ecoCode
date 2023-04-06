package fr.greencodeinitiative.java.checks;

import java.sql.*;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Index;


class FilteredColumnsAreIndexed {
	
	@Entity
	class MyEntity {
		
		@ManyToOne
		private List<String> subEntities; // Noncompliant {{Add @Index on foreign key}}

		@ManyToOne
		@Index(name = "indexSubEntity2", columnNames = "subEntity2Id")
		private List<String> subEntities2; // Compliant 

		@OneToMany
		@Index(name = "indexSubEntity3", columnNames = "subEntity3Id")
		private MyEntity subEntity3; // Compliant 

		@OneToMany
		private MyEntity subEntity4; // Noncompliant {{Add @Index on foreign key}} 
	}
}