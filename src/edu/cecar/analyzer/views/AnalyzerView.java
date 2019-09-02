
package edu.cecar.analyzer.views;

import java.util.Arrays;
import java.util.HashMap;

import edu.cecar.analyzer.controllers.AnalyzerController;

public final class AnalyzerView {

    private static final String QUANTITY = "-c";
    private static final String MOST_COMMON_WORD = "-d";
    private static final String MOST_LONG_LINE = "-l";
    private static final String MOST_SHORT_LINE = "-s";

    private static final String[] AVAILABLE_OPTIONS = new String[] {
        QUANTITY, MOST_COMMON_WORD, MOST_LONG_LINE, MOST_SHORT_LINE
    };

    private final String[] args;
    private AnalyzerController controller;

    public AnalyzerView(String[] args) {
        this.args = args;
    }

    public void addController(AnalyzerController controller) {
        this.controller = controller;
    }

    public void initialize() {
        String path = this.getTextFilePath(args);
        HashMap<String, Object> options = getOptions(args);

        if (this.controller != null) {
            this.controller.analize(options, path);
        }
    }

    public void showInfo(String[] message) {
        for (String line : message) {
            System.out.println(line);
        }
    }

    public void exitWithError(String error) {
        System.err.println(error);
        System.exit(1);
    }
    
    private String getTextFilePath(String[] args) {

        if (args == null || args.length == 0) {
            this.exitWithError("No se especificó ninguna ruta de achivo.");
        }

        // Obtener el path
        String path = args[args.length - 1].trim();
        if (!path.endsWith(".txt")) {
            this.exitWithError("El nombre del archivo de texto no es válido.");      
        }

        return path;
    }

    private HashMap<String, Object> getOptions(String[] args) {
        HashMap<String, Object> options = new HashMap<String, Object>();

        // Si es mayor que 1 es porque tiene opciones
        if (args.length > 1) {
            String[] input = Arrays.copyOfRange(args, 0, args.length - 1);

            String bufferedOption = null;
            for (int i = 0; i < input.length; i++) {
                String currentInput = input[i].trim();

                // Prevenir la repetición de opciones
                if (options.containsKey(currentInput)) {
                    this.exitWithError("Las opciones no se deben repetir.");                
                }

                // Prevenir opciones mutuamente exclusivas
                if (currentInput.equals(MOST_LONG_LINE) && options.containsKey(MOST_SHORT_LINE) ||
                    currentInput.equals(MOST_SHORT_LINE) && options.containsKey(MOST_LONG_LINE)) {
                    this.exitWithError("La opciones -l y -s son mutuamente exclusivas.");              
                }

                if (!Arrays.asList(AVAILABLE_OPTIONS).contains(currentInput)) {

                    if (bufferedOption == null) {
                        this.exitWithError("La option " + currentInput + " no está disponible.");
                    }

                    // Se trata de un argumento de una opción
                    Object arg = currentInput;
                    if (!currentInput.equals(QUANTITY)) {

                        // Asegurarse de que el argumento sea entero.
                        if (currentInput.contains(".")) {
                            this.exitWithError("El argumento de la opción " + bufferedOption + " debe ser entero.");
                        }

                        try {
                            arg = Integer.parseInt(currentInput);
                        } catch(NumberFormatException e) {
                            this.exitWithError("Tipo no válido para la opción " + bufferedOption);
                        }

                    }

                    if (currentInput.equals(MOST_COMMON_WORD)) {
                        options.replace(bufferedOption, arg);
                    } else {
                        options.put(bufferedOption, arg);
                    }

                    bufferedOption = null;
                    continue;
                } 
                
                if (bufferedOption != null && 
                   (bufferedOption.equals(MOST_LONG_LINE) || bufferedOption.equals(MOST_SHORT_LINE))) {
                    this.exitWithError("La opción " + bufferedOption + " requiere un argumento.");
                }

                if (currentInput.equals(MOST_COMMON_WORD)) {
                    options.put(currentInput, null);
                } 
                bufferedOption = currentInput;

                // Si es la última opción y es -l ó -s debe fallar (faltaría el argumento requirido)
                if (i == input.length -1 && 
                   (currentInput.equals(MOST_LONG_LINE) || currentInput.equals(MOST_SHORT_LINE))) {
                    this.exitWithError("La opción " + currentInput + " requiere un argumento.");
                }
            }
        }

        return options;
    }
}