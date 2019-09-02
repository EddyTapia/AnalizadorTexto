package edu.cecar.analyzer.controllers;

import java.util.HashMap;

import edu.cecar.analyzer.views.AnalyzerView;

public final class AnalyzerController {
    private final AnalyzerView view;

    public AnalyzerController(AnalyzerView view) {
        this.view = view;

        this.view.addController(this);
        this.view.initialize();
    }

    public void analize(HashMap<String, Object> options, String path) {
        
    }
}