package org.fogbeam.example.opennlp;

import java.io.*;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

/**
 * Clase principal para tokenizar el contenido de archivos de texto.
 * Utiliza OpenNLP para dividir texto en tokens.
 */
public class TokenizerMain {

  /**
   * Método principal del programa.
   * Procesa uno o más archivos proporcionados como argumentos y guarda los tokens en un archivo de salida.
   *
   * @param args Argumentos de línea de comandos que representan rutas a archivos de entrada.
   * @throws Exception Si ocurre un error durante la ejecución.
   */
  public static void main(String[] args) throws Exception {
    // Verifica que al menos un archivo de entrada ha sido proporcionado
    if (args.length == 0) {
      System.out.println("Por favor, proporciona uno o más archivos de entrada.");
      return;
    }

    /**
     * Carga el modelo de tokenización desde el archivo especificado.
     */
    InputStream modelIn = new FileInputStream("models/en-token.model");

    /**
     * Crea un objeto Tokenizer utilizando el modelo cargado.
     */
    TokenizerModel model = new TokenizerModel(modelIn);
    Tokenizer tokenizer = new TokenizerME(model);

    /**
     * Archivo de salida donde se escribirán los tokens generados.
     */
    BufferedWriter writer = new BufferedWriter(new FileWriter("output_tokens.txt"));

    try {
      // Procesar cada archivo proporcionado como argumento
      for (String filePath : args) {
        File inputFile = new File(filePath);

        /**
         * Verifica si el archivo de entrada existe y es válido.
         */
        if (inputFile.exists() && inputFile.isFile()) {
          System.out.println("Procesando archivo: " + filePath);

          // Leer el contenido del archivo
          String content = readFile(inputFile);

          /**
           * Tokeniza el contenido del archivo.
           */
          String[] tokens = tokenizer.tokenize(content);

          /**
           * Escribe los tokens generados en el archivo de salida.
           */
          writer.write("Tokens de: " + filePath + "\n");
          for (String token : tokens) {
            writer.write(token + "\n");
          }
          writer.write("\n-----\n");
        } else {
          System.out.println("El archivo " + filePath + " no existe o no es un archivo válido.");
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      // Cierra el archivo de salida y el modelo
      if (modelIn != null) {
        try {
          modelIn.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      try {
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    System.out.println("Proceso completado. Los tokens se han guardado en output_tokens.txt");
  }

  /**
   * Lee el contenido de un archivo y lo devuelve como una cadena de texto.
   *
   * @param file Archivo que se desea leer.
   * @return Contenido del archivo como una cadena.
   * @throws IOException Si ocurre un error al leer el archivo.
   */
  private static String readFile(File file) throws IOException {
    StringBuilder content = new StringBuilder();
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String line;
    while ((line = reader.readLine()) != null) {
      content.append(line).append("\n");
    }
    reader.close();
    return content.toString();
  }
}

