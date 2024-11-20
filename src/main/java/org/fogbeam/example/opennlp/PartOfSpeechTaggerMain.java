package org.fogbeam.example.opennlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

/**
 * Clase principal para la etiquetación de partes de la oración (POS tagging).
 * Utiliza OpenNLP para etiquetar tokens con sus respectivas categorías gramaticales.
 */
public class PartOfSpeechTaggerMain {

    /**
     * Logger para mostrar mensajes de error.
     */
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PartOfSpeechTaggerMain.class.getName());

  /**
   * Método principal que carga un modelo de etiquetación de partes de la oración y procesa una oración de ejemplo.
   *
   * @param args Argumentos de línea de comandos (no se usan en este ejemplo).
   */
  public static void main(String[] args) {
    /**
     * Flujo de entrada para cargar el modelo de etiquetación POS.
     */
    InputStream modelIn = null;

    try {
      // Carga del modelo POS desde un archivo.
      // Descomentar la línea adecuada dependiendo del formato del modelo.
      // modelIn = new FileInputStream("models/en-pos.model");
      modelIn = new FileInputStream("models/en-pos-maxent.bin");

      /**
       * Carga el modelo de etiquetación de partes de la oración.
       */
      POSModel model = new POSModel(modelIn);

      /**
       * Crea un etiquetador POS basado en el modelo cargado.
       */
      POSTaggerME tagger = new POSTaggerME(model);

      /**
       * Oración de ejemplo a etiquetar.
       */
      String sent[] = new String[] {
              "Most", "large", "cities", "in", "the", "US", "had",
              "morning", "and", "afternoon", "newspapers", "."
      };

      /**
       * Etiqueta cada token de la oración con su categoría gramatical.
       */
      String tags[] = tagger.tag(sent);

      /**
       * Calcula las probabilidades asociadas a cada etiqueta.
       */
      double probs[] = tagger.probs();

      /**
       * Imprime cada token con su etiqueta y su probabilidad asociada.
       */
      for (int i = 0; i < sent.length; i++) {
        System.out.println(
                "Token [" + sent[i] + "] has POS [" + tags[i] + "] with probability = " + probs[i]);
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
    }

    System.out.println("done");
  }
}

