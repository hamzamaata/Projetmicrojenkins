package com.projetmicroservice.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PatientTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Patient getPatientSample1() {
        return new Patient().id(1L).nom("nom1").prenom("prenom1").historiqueMedical("historiqueMedical1");
    }

    public static Patient getPatientSample2() {
        return new Patient().id(2L).nom("nom2").prenom("prenom2").historiqueMedical("historiqueMedical2");
    }

    public static Patient getPatientRandomSampleGenerator() {
        return new Patient()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .prenom(UUID.randomUUID().toString())
            .historiqueMedical(UUID.randomUUID().toString());
    }
}
