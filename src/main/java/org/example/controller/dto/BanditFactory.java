package org.example.controller.dto;

import org.example.bandit.StochasticBandit;
import org.example.distribution.BernoulliDistribution;
import org.example.distribution.Distribution;
import org.example.distribution.GaussianDistribution;

import java.util.LinkedList;
import java.util.List;

public class BanditFactory {

    public static BanditRequest getFromExperimentFrontendDataType(ExperimentFrontendDataType type) {
        BanditRequest request = new BanditRequest();
        List<DistributionRequest> list = new LinkedList<>();
        for (ExperimentFrontendDataType.Distribution distribution : type.getDistributions()) {
            DistributionRequest distRequest = new DistributionRequest();
            distRequest.setMean(distribution.getMean() != null ? distribution.getMean() : 0);
            distRequest.setSd(distribution.getStdDev() != null ? distribution.getStdDev() : 0);
            distRequest.setP(distribution.getP() != null ? distribution.getP() : 0);
            distRequest.setDistributionType(DistributionType.valueOf(distribution.getType().toUpperCase()));
            list.add(distRequest);
        }
        request.setDistributionRequest(list);
        return request;
    }

    public static StochasticBandit createBandit(BanditRequest request) {
        int k = request.getDistributionRequest().size();
        Distribution[] dist = new Distribution[k];
        for (int i = 0; i < k; i++) {
            dist[i] = createDistribution(request.getDistributionRequest().get(i));
        }
        return new StochasticBandit(dist);
    }

    public static Distribution createDistribution(DistributionRequest distRequest) {
        if (distRequest.getDistributionType().equals(DistributionType.BERNOULLI)) {
            return new BernoulliDistribution(distRequest.getP());
        } else if (distRequest.getDistributionType().equals(DistributionType.NORMAL)) {
            return new GaussianDistribution(distRequest.getMean(), distRequest.getSd());
        } else {
            throw new IllegalArgumentException("unknown distribution type");
        }
    }

}
