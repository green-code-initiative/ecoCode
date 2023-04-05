import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

class TestClass {

	TestClass(TestClass tc) {
	}

	int compliantCase() {
		return "<svg id=\"a\"></svg>";
	}

	int idCanBeReduced() {
		return "<svg id=\"main-wrapper\"></svg>"; // Noncompliant {{Optimize your svg by using open source tools such as SVGGO, Compressor.io or SVG Cleaner}}
	}

	int superfluousTag() {
		return "<svg><metadata></metadata></svg>"; // Noncompliant {{Optimize your svg by using open source tools such as SVGGO, Compressor.io or SVG Cleaner}}
	}

	int duplicatedTags() {
		return "<svg><linearGradient inkscape:collect=\"always\" xlink:href=\"#linearGradient4405\" id=\"linearGradient2917\" gradientUnits=\"userSpaceOnUse\" gradientTransform=\"matrix(19.565,0,0,19.565,-95.619,79.961)\" x1=\"11.685\" y1=\"33.646\" x2=\"10.783\" y2=\"35.870\" /><linearGradient inkscape:collect=\"always\" xlink:href=\"#linearGradient4405\" id=\"linearGradient2917\" gradientUnits=\"userSpaceOnUse\" gradientTransform=\"matrix(19.565,0,0,19.565,-95.619,79.961)\" x1=\"11.685\" y1=\"33.646\" x2=\"10.783\" y2=\"35.870\" /></svg>"; // Noncompliant {{Optimize your svg by using open source tools such as SVGGO, Compressor.io or SVG Cleaner}}
	}

	int unapproximatedNumericValues() {
		return "<svg><linearGradient inkscape:collect=\"always\" xlink:href=\"#linearGradient4405\" id=\"linearGradient2917\" gradientUnits=\"userSpaceOnUse\" gradientTransform=\"matrix(19.565616,0,0,19.565616,-95.619433,79.96141)\" x1=\"11.685\" y1=\"33.646999\" x2=\"10.783\" y2=\"35.870998\" /></svg>"; // Noncompliant {{Optimize your svg by using open source tools such as SVGGO, Compressor.io or SVG Cleaner}}
	}

	int superfluousAttributes1() {
		return "<svg><div trucmuche=\"test\"></div></svg>"; // Noncompliant {{Optimize your svg by using open source tools such as SVGGO, Compressor.io or SVG Cleaner}}
	}

	int superfluousAttributes2() {
		return "<svg><div xmlns:dc=\"test\"></div></svg>"; // Noncompliant {{Optimize your svg by using open source tools such as SVGGO, Compressor.io or SVG Cleaner}}
	}
	
}