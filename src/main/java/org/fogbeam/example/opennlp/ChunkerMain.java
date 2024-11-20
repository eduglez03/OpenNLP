package org.fogbeam.example.opennlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;

/**
 * @brief Clase principal para realizar chunking en texto usando el modelo de OpenNLP.
 * @details El chunking identifica frases nominales, verbales, y otros tipos de chunks en una secuencia de tokens.
 */
public class ChunkerMain {

  /**
   * @brief Método principal que ejecuta el chunking sobre una oración.
   * @param args Argumentos de línea de comandos (no utilizados en este ejemplo).
   * @throws Exception Lanza excepciones en caso de errores durante la ejecución.
   */
  public static void main(String[] args) throws Exception {
    /**
     * @brief Flujo de entrada para cargar el modelo de chunking.
     */
    InputStream modelIn = null;

    /**
     * @brief Modelo de chunking cargado desde el archivo.
     */
    ChunkerModel model = null;

    try {
      /**
       * @brief Carga el modelo de chunking desde un archivo.
       */
      modelIn = new FileInputStream("models/en-chunker.model");
      model = new ChunkerModel(modelIn);

      /**
       * @brief Crea una instancia de ChunkerME para realizar chunking.
       */
      ChunkerME chunker = new ChunkerME(model);

      /**
       * @brief Tokens de ejemplo que representan una oración.
       * @details Normalmente estos se generarían usando un tokenizador.
       */
      String sent[] = new String[] {
              "Rockwell", "International", "Corp.", "'s", "Tulsa", "unit", "said",
              "it", "signed", "a", "tentative", "agreement", "extending", "its",
              "contract", "with", "Boeing", "Co.", "to", "provide", "structural",
              "parts", "for", "Boeing", "'s", "747", "jetliners", "."
      };

      /**
       * @brief Etiquetas POS (Part-Of-Speech) para los tokens.
       * @details Estas etiquetas serían generadas por un POS Tagger.
       */
      String pos[] = new String[] {
              "NNP", "NNP", "NNP", "POS", "NNP", "NN", "VBD", "PRP", "VBD", "DT",
              "JJ", "NN", "VBG", "PRP$", "NN", "IN", "NNP", "NNP", "TO", "VB",
              "JJ", "NNS", "IN", "NNP", "POS", "CD", "NNS", "."
      };

      /**
       * @brief Realiza el chunking sobre los tokens y sus etiquetas POS.
       * @return Un array con las etiquetas de chunking para cada token.
       */
      String tag[] = chunker.chunk(sent, pos);

      /**
       * @brief Probabilidades asociadas a las etiquetas de chunking.
       */
      double probs[] = chunker.probs();

      /**
       * @brief Imprime los resultados del chunking: tokens, etiquetas y probabilidades.
       * @details Las etiquetas de chunking incluyen:
       * - B-CHUNK: Primer token de un chunk.
       * - I-CHUNK: Tokens subsecuentes dentro del mismo chunk.
       */
      for (int i = 0; i < sent.length; i++) {
        System.out.println("Token [" + sent[i] + "] has chunk tag [" + tag[i] + "] with probability = " + probs[i]);
      }

    } catch (IOException e) {
      /**
       * @brief Maneja errores al cargar el modelo.
       */
      System.err.println("Error al cargar el modelo de chunking. Por favor, revise el archivo de entrada.");
    } finally {
      /**
       * @brief Libera el recurso del modelo.
       */
      if (modelIn != null) {
        try {
          modelIn.close();
        } catch (IOException e) {
          // Ignorar error al cerrar
        }
      }
    }

    /**
     * @brief Indica que el proceso ha finalizado.
     */
    System.out.println("done");
  }
}
