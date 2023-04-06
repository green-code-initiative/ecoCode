package fr.greencodeinitiative.java.checks;

import java.sql.*;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Index;


class FilteredColumnsAreIndexed {
	
	@Entity
	class MyEntity {
		
		@ManyToOne
		private MyEntity subEntities; // Noncompliant {{Add @Index on foreign key}}

		@ManyToOne
		@Index(name = "indexSubEntity2", columnNames = "subEntity2Id")
		private MyEntity subEntities2; // Compliant

		@OneToMany
		@Index(name = "indexSubEntity3", columnNames = "subEntity3Id")
		private List<String> subEntity3; // Compliant

		@OneToMany
		private List<String>  subEntity4; // Noncompliant {{Add @Index on foreign key}}
	}
}