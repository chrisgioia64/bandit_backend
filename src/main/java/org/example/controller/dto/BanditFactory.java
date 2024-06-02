package org.example.controller.dto;

import org.example.bandit.StochasticBandit;
import org.example.distribution.BernoulliDistribution;
import org.example.distribution.Distribution;
import org.example.distribution.GaussianDistribution;

public class BanditFactory {

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
