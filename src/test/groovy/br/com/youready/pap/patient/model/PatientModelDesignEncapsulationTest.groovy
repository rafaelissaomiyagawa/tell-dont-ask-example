package br.com.youready.pap.patient.model

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import spock.lang.Specification

class PatientModelDesignEncapsulationTest extends Specification {

    final String packageName = "br.com.youready.pap.patient.model"
    JavaClasses importedClasses;

    def setup() {
        importedClasses = new ClassFileImporter().withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                                                 .importPackages(packageName)
    }

    def "Patient is the entity (or API) of the model"() {
        when:
        ArchRule rule = ArchRuleDefinition.classes()
                                          .that()
                                          .haveSimpleName("Patient")
                                          .should()
                                          .bePublic()
        then:
        rule.check(importedClasses)
    }

    def "All other classes must be package protected"() {
        when:
        ArchRule rule = ArchRuleDefinition.classes()
                                          .that()
                                          .doNotHaveSimpleName("Patient")
                                          .should()
                                          .bePackagePrivate()
        then:
        rule.check(importedClasses)
    }
}
