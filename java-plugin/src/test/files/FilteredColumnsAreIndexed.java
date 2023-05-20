package fr.greencodeinitiative.java.checks;

import java.sql.*;
import javax.persistence.*;


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

		private MyEntity subEntity5;

		@ManyToOne
		public MyEntity getSubEntity5() { // Noncompliant {{Add @Index on foreign key}}
			return this.subEntity5;
		}

		private MyEntity subEntity6;
		@ManyToOne
		@Index(name = "indexSubEntity6", columnNames = "subEntity6Id")
		public MyEntity getSubEntity6() { // Compliant
			return this.subEntity6;
		}

		@ManyToMany
		@JoinTable(
		    name = "JOIN_TABLE",
		    joinColumns = @JoinColumn(name="ENT_ID", referencedColumnName="ENT_ID"),
		    inverseJoinColumns = @JoinColumn(name="PROJ_ID", referencedColumnName="PROJ_ID")
		)
		private List<Project> projects; // Noncompliant {{Add @Index on foreign key}}
		
		@ManyToMany
		@JoinTable(
		    name = "JOIN_TABLE",
		    joinColumns = @JoinColumn(name="ENT_ID", referencedColumnName="ENT_ID"),
		    inverseJoinColumns = @JoinColumn(name="PROJ_ID", referencedColumnName="PROJ_ID"),
    		indexes = {
		        @Index(name = "idx_ent_id", columnList = "ent_id"),
		        @Index(name = "idx_proj_id", columnList = "project_id")
		    }
		)
		private List<Project> projects; // Compliant
			}
}