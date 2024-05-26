package org.example.controller;

import org.example.algorithm.BanditAlgorithm;
import org.example.algorithm.ETCAlgorithm;
import org.example.algorithm.EpsilonGreedyAlgorithm;
import org.example.algorithm.UCBAlgorithm;

import java.util.LinkedList;
import java.util.List;

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

    public static List<BanditAlgorithm> createAlgorithms(int n) {
        List<BanditAlgorithm> algorithms = new LinkedList<>();
        algorithms.add(new ETCAlgorithm(1));
        algorithms.add(new ETCAlgorithm(2));
        algorithms.add(new ETCAlgorithm(5));
        algorithms.add(new ETCAlgorithm(10));
        algorithms.add(new EpsilonGreedyAlgorithm(0));
        algorithms.add(new EpsilonGreedyAlgorithm(0.1));
        algorithms.add(new EpsilonGreedyAlgorithm(0.2));
        algorithms.add(new EpsilonGreedyAlgorithm(0.4));
        algorithms.add(new EpsilonGreedyAlgorithm(0.6));
        algorithms.add(new EpsilonGreedyAlgorithm(0.8));
        algorithms.add(new EpsilonGreedyAlgorithm(1.0));
        algorithms.add(new UCBAlgorithm(0.1));
        algorithms.add(new UCBAlgorithm(0.2));
        algorithms.add(new UCBAlgorithm(0.5));
        algorithms.add(new UCBAlgorithm(1.0));
        algorithms.add(new UCBAlgorithm(1.5));
        algorithms.add(new UCBAlgorithm(2.0));
        return algorithms;
    }

}
