package de.btu.openinfra.backend;

import java.util.List;

import org.glassfish.jersey.server.wadl.config.WadlGeneratorConfig;
import org.glassfish.jersey.server.wadl.config.WadlGeneratorDescription;
import org.glassfish.jersey.server.wadl.internal.generators.WadlGeneratorApplicationDoc;
import org.glassfish.jersey.server.wadl.internal.generators.WadlGeneratorGrammarsSupport;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.WadlGeneratorResourceDocSupport;

/**
 * This class is used to extend the build-in generation of the WADL by Java-DOC
 * enriched information.
 *
 * This class is intrinsingly part of the REST API. But it is also configured
 * by the {@see OpenInfraApplication} class. We decided to place it in this
 * package next to where it is registered.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class OpenInfraWadlConfig extends WadlGeneratorConfig {

	@Override
	public List<WadlGeneratorDescription> configure() {
        return generator(WadlGeneratorApplicationDoc.class)
                .prop("applicationDocsStream", "application-doc.xml")
                .generator(WadlGeneratorGrammarsSupport.class)
                .prop("grammarsStream", "application-grammars.xml")
                .prop("overrideGrammars", true)
                .generator(WadlGeneratorResourceDocSupport.class)
                .prop("resourceDocStream", "resourcedoc.xml")
                .descriptions();
	}

}
