package org.example.controller;

import org.example.algorithm.BanditAlgorithm;
import org.example.algorithm.ETCAlgorithm;
import org.example.algorithm.EpsilonGreedyAlgorithm;
import org.example.algorithm.UCBAlgorithm;

public class AlgorithmFactory {

    public static BanditAlgorithm createAlgorithm(AlgorithmRequest request) {
        if (request.getAlgorithm().equals(Algorithm.UCB)) {
            return new UCBAlgorithm(request.getUcbFraction());
        } else if (request.getAlgorithm().equals(Algorithm.ETC)) {
            return new ETCAlgorithm(request.getM());
        } else if (request.getAlgorithm().equals(Algorithm.EpsilonGreedy)) {
            return new EpsilonGreedyAlgorithm(request.getEpsilon());
        } else {
            throw new IllegalArgumentException("Algorithm type unknown " + request.getAlgorithm());
        }
    }

}
