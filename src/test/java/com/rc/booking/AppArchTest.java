package com.rc.booking;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = {
        "com.rc.booking"
})
public class AppArchTest {

    @ArchTest
    static final ArchRule shouldKeepArchitectureValid = layeredArchitecture()
            .consideringAllDependencies()
            .layer("Domain").definedBy("com.rc.booking.occupancy", "com.rc.booking.room")
            .layer("Service").definedBy("..dto", "..api")
            .layer("Api").definedBy("..rest")

            .whereLayer("Domain").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Api", "Domain");
}
