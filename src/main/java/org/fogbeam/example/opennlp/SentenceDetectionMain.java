package org.fogbeam.example.opennlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

/**
 * Clase principal para la detección de oraciones.
 * Utiliza OpenNLP para dividir texto en oraciones utilizando un modelo preentrenado.
 */
public class SentenceDetectionMain {

    /**
     * Logger para mostrar mensajes de error.
     */
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SentenceDetectionMain.class.getName());

  /**
   * Método principal que carga un modelo de detección de oraciones y procesa datos de ejemplo.
   *
   * @param args Argumentos de línea de comandos (no se usan en este ejemplo).
   * @throws Exception Si ocurre un error durante la ejecución.
   */
  public static void main(String[] args) throws Exception {
    /**
     * Flujo de entrada para el modelo de detección de oraciones.
     */
    InputStream modelIn = new FileInputStream("models/en-sent.model");

    /**
     * Flujo de entrada para los datos de ejemplo que serán procesados.
     */
    InputStream demoDataIn = new FileInputStream("demo_data/en-sent1.demo");

    try {
      /**
       * Carga el modelo de detección de oraciones.
       */
      SentenceModel model = new SentenceModel(modelIn);

      /**
       * Crea un detector de oraciones basado en el modelo cargado.
       */
      SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);

      /**
       * Convierte el flujo de entrada de los datos de ejemplo a una cadena.
       */
      String demoData = convertStreamToString(demoDataIn);

      /**
       * Detecta oraciones en los datos de ejemplo.
       */
      String sentences[] = sentenceDetector.sentDetect(demoData);

      /**
       * Imprime las oraciones detectadas en la consola.
       */
      for (String sentence : sentences) {
        System.out.println(sentence + "\n");
      }
    } catch (Exception e) {
      logger.severe("Error" + e.getMessage());
    } finally {
      /**
       * Cierra el flujo de entrada del modelo si está abierto.
       */
      if (modelIn != null) {
        try {
          modelIn.close();
        } catch (Exception e) {
          logger.severe("Error" + e.getMessage());
        }
      }

      /**
       * Cierra el flujo de entrada de los datos de ejemplo si está abierto.
       */
      if (demoDataIn != null) {
        try {
          demoDataIn.close();
        } catch (Exception e) {
          logger.severe("Error" + e.getMessage());
        }
      }
    }

    System.out.println("done");
  }

  /**
   * Convierte un flujo de entrada en una cadena de texto.
   *
   * @param is Flujo de entrada que se desea convertir.
   * @return El contenido del flujo de entrada como una cadena.
   */
  static String convertStreamToString(java.io.InputStream is) {
    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
    return s.hasNext() ? s.next() : "";
  }
}

