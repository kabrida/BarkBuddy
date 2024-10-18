package syksy2024.backend.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Height {
    private String metric;

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

}
