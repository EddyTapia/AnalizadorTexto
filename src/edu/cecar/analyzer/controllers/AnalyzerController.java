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
    public String[] findCommonStrings(int count, String path) throws FileNotFoundException, IOException {
        String[] lines = ReaderWriterService.readTextFile(path);

        List<String> words = new ArrayList<String>();

        for (int i = 0; i < lines.length; i++) {
            if (lines[i].trim() != "") {
                String[] line = lines[i].split(" ");
                for (String word : line) {
                    words.add(word);
                }
            }
        }

        //HashMap<String, Long> ocurrences = words.stream().collect(Collectors.groupingBy(w -> w, Collectors.counting()));
        HashMap<String, Integer> mapWords = new HashMap<String, Integer>();
        for (String word : words) {
            mapWords.put(word, Collections.frequency(words, word));
        }

        String[] sortedWords = mapWords
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (w1, w2) -> w2, LinkedHashMap::new))
                .keySet().toArray(new String[mapWords.size()]);

        return Arrays.copyOfRange(sortedWords, 0, count);

    }

    public String findLongestLine(String path) throws FileNotFoundException, IOException {
        String[] lines = ReaderWriterService.readTextFile(path);

        return Arrays.stream(lines).max(Comparator.comparingInt(String::length)).get();
    }

    public void countMatches(String word, String path) throws FileNotFoundException, IOException {
        String[] lines = ReaderWriterService.readTextFile(path);

        for (int i = 0; i < lines.length; i++) {
            int matches = 0;
            String[] words = lines[i].split(" ");
            for (String w : words) {
                if (w.equals(word)) {
                    matches++;
                }
            }

            lines[i] = String.valueOf(matches) + " " + lines[i];
        }

        path = path.substring(0, path.lastIndexOf(".")) + ".modificado.txt";
        ReaderWriterService.createTextFile(path, lines);
    }

    public void palabra_Mas_Larga(int numero, String path) throws FileNotFoundException, IOException {
        String palabra_mas_larga = "";

        File fichero = new File(path);
        FileReader fl = new FileReader(fichero);
        BufferedReader reader_buffer = new BufferedReader(fl);
        String archivo = null;
        int tamaño_pal_larga = 0;
        int tamaño_linea;
        while ((archivo = reader_buffer.readLine()) != null) {
            tamaño_linea = archivo.length();
            if (tamaño_linea > tamaño_pal_larga) {
                tamaño_pal_larga = tamaño_linea;
                palabra_mas_larga = archivo;
            }
        }
        System.out.println("Resultado: " + palabra_mas_larga);
    }

    public void palabra_Mas_Corta(int numero, String path) throws FileNotFoundException, IOException {
        String palabra_mas_corta = "";

        File fichero = new File(path);
        FileReader fl = new FileReader(fichero);
        BufferedReader reader_buffer = new BufferedReader(fl);
        String archivo = null;
        int tamaño_pal_corta = 0;
        int tamaño_linea;
        while ((archivo = reader_buffer.readLine()) != null) {
            tamaño_linea = archivo.length();
            if (tamaño_linea < tamaño_pal_corta) {
                tamaño_pal_corta = tamaño_linea;
                palabra_mas_corta = archivo;
            }
        }
        System.out.println("Resultado: " + palabra_mas_corta);
    }

}
