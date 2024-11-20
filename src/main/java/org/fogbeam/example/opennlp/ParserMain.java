package org.fogbeam.example.opennlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

/**
 * @brief Clase principal para realizar análisis sintáctico (parsing) utilizando OpenNLP.
 */
public class ParserMain {
  private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ParserMain.class.getName());

  /**
   * @brief Método principal que realiza el análisis sintáctico de una oración de ejemplo.
   * @param args Argumentos de línea de comandos (no se utilizan en este ejemplo).
   * @throws Exception Lanza excepciones en caso de errores durante la ejecución.
   */
  public static void main(String[] args) throws Exception {
    /**
     * @brief Carga el modelo de análisis sintáctico desde un archivo binario.
     */
    InputStream modelIn = new FileInputStream("models/en-parser-chunking.bin");

    try {
      /**
       * @brief Inicializa el modelo de análisis sintáctico.
       */
      ParserModel model = new ParserModel(modelIn);

      /**
       * @brief Crea un analizador sintáctico utilizando el modelo cargado.
       */
      Parser parser = ParserFactory.create(model);

      /**
       * @brief Oración de ejemplo que se analizará sintácticamente.
       */
      String sentence = "The quick brown fox jumps over the lazy dog .";

      /**
       * @brief Realiza el análisis sintáctico de la oración.
       * @details Se genera un array de resultados del análisis sintáctico (Parse) con una sola interpretación.
       */
      Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);

      /**
       * @brief Obtiene el primer (y único) resultado del análisis sintáctico.
       */
      Parse parse = topParses[0];

      /**
       * @brief Imprime la representación textual del análisis sintáctico.
       */
      System.out.println(parse.toString());

      /**
       * @brief Muestra el árbol sintáctico generado en la salida estándar.
       */
      parse.showCodeTree();

    } catch (Exception e) {
      logger.severe("Error" + e.getMessage());
    } finally {
      /**
       * @brief Cierra el flujo de entrada del modelo si está abierto.
       */
      if (modelIn != null) {
        try {
          modelIn.close();
        } catch (IOException e) {
          // No se realiza ninguna acción en caso de error al cerrar.
        }
      }
    }

    /**
     * @brief Indica que el proceso ha finalizado.
     */
    System.out.println("done");
  }
}
