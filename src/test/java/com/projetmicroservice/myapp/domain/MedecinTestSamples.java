package com.projetmicroservice.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MedecinTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Medecin getMedecinSample1() {
        return new Medecin().id(1L).nom("nom1").prenom("prenom1").role("role1");
    }

    public static Medecin getMedecinSample2() {
        return new Medecin().id(2L).nom("nom2").prenom("prenom2").role("role2");
    }

    public static Medecin getMedecinRandomSampleGenerator() {
        return new Medecin()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .prenom(UUID.randomUUID().toString())
            .role(UUID.randomUUID().toString());
    }
}
