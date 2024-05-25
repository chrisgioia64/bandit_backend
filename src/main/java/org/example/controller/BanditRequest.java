package org.example.controller;

import java.util.LinkedList;
import java.util.List;

public class BanditRequest {

    private List<DistributionRequest> distributionRequest;

    public BanditRequest() {
        distributionRequest = new LinkedList<>();
    }

    public List<DistributionRequest> getDistributionRequest() {
        return distributionRequest;
    }

    public void setDistributionRequest(List<DistributionRequest> distributionRequest) {
        this.distributionRequest = distributionRequest;
    }
}
