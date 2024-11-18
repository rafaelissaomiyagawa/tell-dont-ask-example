package br.com.youready.pap.patient.model;

import lombok.Getter;

import java.time.Instant;

@Getter
public class Patient {
    private final String name;
    private final ManchesterProtocolClassification classification;
    private final Instant arrivalTime;

    public Patient(String name, ManchesterProtocolClassification classification) {
        this.name = name;
        this.classification = classification;
        this.arrivalTime = Instant.now();
    }
}
