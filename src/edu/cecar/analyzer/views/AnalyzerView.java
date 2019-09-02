
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
        HashMap<String, Object> options = validate(args);

        if (options != null && this.controller != null) {
            this.controller.analize(options);
        }
    }

    public void showMessage(String[] message) {
        for (String line : message) {
            System.out.println(line);
        }
    }

    private HashMap<String, Object> validate(String[] args) {
        if (args.length == 0) {
            System.out.println("Se requiere un argumento.");
            return null;
        }

        // Obtener el path
        String path = args[args.length - 1].trim();

        if (!path.endsWith(".txt")) {
            System.out.println("El nombre del archivo de texto no es válido.");
            return null;
        }

        HashMap<String, Object> options = new HashMap<String, Object>();
        // Si es mayor que 1 es porque tiene opciones
        if (args.length > 1) {
            String[] optionsAndTheirArgs = Arrays.copyOfRange(args, 0, args.length - 1);

            String bufferedOption = null;
            for (int i = 0; i < optionsAndTheirArgs.length; i++) {

                String currentOptionOrArg = optionsAndTheirArgs[i];

                // Prevenir la repetición de opciones
                if (options.containsKey(currentOptionOrArg)) {
                    System.out.println("Las opciones no se deben repetir.");
                    return null;
                }

                // Prevenir opciones mutuamente exclusivas
                if (currentOptionOrArg == MOST_LONG_LINE && options.containsKey(MOST_SHORT_LINE) ||
                    currentOptionOrArg == MOST_SHORT_LINE && options.containsKey(MOST_LONG_LINE)) {
                    System.out.println("La opciones -l y -s son mutuamente exclusivas.");
                    return null;
                }

                // Asegurar que se trate de una opcion disponible
                if (!Arrays.asList(AVAILABLE_OPTIONS).contains(currentOptionOrArg)) {

                    if (bufferedOption == null) {
                        System.out.println("La option " + currentOptionOrArg + " no está disponible.");
                        return null;
                    }

                    Object arg = null;

                    // Se trata de un argumento de una opción
                    if (currentOptionOrArg != QUANTITY) {
                        // Asegurarse de que el argumento sea entero.
                        if (currentOptionOrArg.contains(".")) {
                            System.out.println("El argumento de la opción " + bufferedOption + " debe ser entero.");
                            return null;
                        }
                        try {
                            arg = Integer.parseInt(currentOptionOrArg);
                        } catch(NumberFormatException e) {
                            System.out.println("Tipo no válido para la opción " + bufferedOption);
                            return null;
                        }
                    } else {
                        // El argumento de la opción -c no debe comenzar un guión medio
                        // ya que la cadena no se encierra entre comillas y sería ambigua
                        // porque podría tratarse de una opción, independientemente de que
                        // actualmente esté disponible o no, pues, en el futuro podría estarlo.
                        if (currentOptionOrArg.startsWith("-")) {
                            System.out.println("La opción " + currentOptionOrArg + " no está disponible.");
                            return null;
                        }
                        arg = currentOptionOrArg;
                    }
                    options.replace(bufferedOption, arg);
                    bufferedOption = null;
                    continue;
                }

                options.put(currentOptionOrArg, null);
                bufferedOption = currentOptionOrArg;
            }
        }

        options.put("-path", path);

        return options;
    }
}