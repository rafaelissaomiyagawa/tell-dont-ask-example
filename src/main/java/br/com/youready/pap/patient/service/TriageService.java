package br.com.youready.pap.patient.service;

import br.com.youready.pap.patient.model.ManchesterProtocolClassification;
import br.com.youready.pap.patient.model.Patient;
import br.com.youready.pap.patient.repository.PatientRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class TriageService {

    private PatientRepository patientRepository;

    public List<String> getClassificatioList() {
        return Arrays.stream(ManchesterProtocolClassification.values())
                     .map(Enum::name)
                     .toList();
    }

    public Patient registerPatient(String name, ManchesterProtocolClassification classification) {
        Patient patient = new Patient(name, classification);

        patientRepository.save(patient);

        return patient;
    }

    public boolean isWaitingTimeExceeded(Patient patient) {
        Duration waitingTime = Duration.between(patient.getArrivalTime(), Instant.now());

        Duration maxWaitTime = switch (patient.getClassification()) {
            case IMMEDIATE -> Duration.ofMinutes(0);
            case VERY_URGENT -> Duration.ofMinutes(10);
            case URGENT -> Duration.ofMinutes(60);
            case STANDARD -> Duration.ofHours(2);
            case NON_URGENT -> Duration.ofHours(4);
        };

        return waitingTime.compareTo(maxWaitTime) > 0;
    }
}
