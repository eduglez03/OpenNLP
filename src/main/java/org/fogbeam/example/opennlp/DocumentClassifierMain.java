package org.fogbeam.example.opennlp;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Logger;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;

/**
 * @brief Clase principal para clasificar documentos usando un modelo de OpenNLP.
 */
public class DocumentClassifierMain {

  private static final Logger logger = Logger.getLogger(DocumentClassifierMain.class.getName());

  /**
   * @brief Método principal que ejecuta la clasificación de un documento basado en un modelo de categorización.
   * @param args Argumentos de línea de comandos (no utilizados en este ejemplo).
   * @throws Exception Lanza excepciones si ocurren errores durante la ejecución.
   */
  public static void main(String[] args) throws Exception {

    /**
     * @brief Flujo de entrada para cargar el modelo de categorización de documentos.
     */
    InputStream is = null;
    try {
      /**
       * @brief Carga el modelo de categorización desde un archivo.
       * @details El modelo especificado debe estar previamente entrenado para identificar categorías.
       */
      is = new FileInputStream("models/en-doccat.model");

      /**
       * @brief Crea una instancia del modelo de categorización de documentos.
       */
      DoccatModel m = new DoccatModel(is);

      /**
       * @brief Texto de entrada que será clasificado.
       */
      String inputText = "What happens if we have declining bottom-line revenue?";

      /**
       * @brief Inicializa el categorizador de documentos basado en el modelo cargado.
       */
      DocumentCategorizerME myCategorizer = new DocumentCategorizerME(m);

      /**
       * @brief Clasifica el texto de entrada y obtiene los resultados de probabilidad para cada categoría.
       */
      double[] outcomes = myCategorizer.categorize(inputText);

      /**
       * @brief Determina la mejor categoría según las probabilidades calculadas.
       */
      String category = myCategorizer.getBestCategory(outcomes);

      /**
       * @brief Imprime la categoría asignada al texto de entrada.
       */
      System.out.println("Input classified as: " + category);

    } catch (Exception e) {
        logger.severe("Error en la clasificación de documentos: " + e.getMessage());
  } finally {
      /**
       * @brief Cierra el flujo de entrada del modelo para liberar recursos.
       */
      if (is != null) {
        is.close();
      }
    }

    /**
     * @brief Indica que el proceso ha finalizado.
     */
    System.out.println("done");
  }
}
