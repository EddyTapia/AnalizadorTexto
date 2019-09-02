package edu.cecar.analyzer;

import edu.cecar.analyzer.controllers.AnalyzerController;
import edu.cecar.analyzer.views.AnalyzerView;

public class Program {
    public static void main(String[] args){
        new AnalyzerController(new AnalyzerView(args));
    }
}