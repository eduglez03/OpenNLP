package org.fogbeam.example.opennlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

/**
 * @brief Clase principal para la detección de nombres (Named Entity Recognition) utilizando OpenNLP.
 */
public class NameFinderMain {

  /**
   * @brief Método principal que realiza la detección de nombres en un conjunto de tokens.
   * @param args Argumentos de línea de comandos (no utilizados en este ejemplo).
   * @throws Exception Lanza excepciones si ocurren errores durante la ejecución.
   */
  public static void main(String[] args) throws Exception {
    /**
     * @brief Carga el modelo de detección de nombres desde un archivo.
     * @details El modelo es utilizado para identificar entidades nombradas como nombres de personas.
     */
    InputStream modelIn = new FileInputStream("models/en-ner-person.model");
    // InputStream modelIn = new FileInputStream("models/en-ner-person.bin");

    try {
      /**
       * @brief Inicializa el modelo de detección de nombres.
       */
      TokenNameFinderModel model = new TokenNameFinderModel(modelIn);

      /**
       * @brief Crea un detector de nombres basado en el modelo cargado.
       */
      NameFinderME nameFinder = new NameFinderME(model);

      /**
       * @brief Tokens que representan un fragmento de texto para analizar.
       */
      String[] tokens = { //"A", "guy", "named",
              // "Mr.",
              "Phillip",
              "Rhodes",
              "is",
              "presenting",
              "at",
              "some",
              "meeting",
              "."};

      /**
       * @brief Detecta entidades nombradas en el conjunto de tokens.
       * @details Devuelve un array de objetos `Span` que representan las posiciones de los nombres encontrados.
       */
      Span[] names = nameFinder.find(tokens);

      /**
       * @brief Itera sobre las entidades nombradas detectadas y las imprime en la salida estándar.
       */
      for (Span ns : names) {
        System.out.println("ns: " + ns.toString());

        // Si deseas realizar alguna acción adicional con el nombre encontrado:
        // ...
      }

      /**
       * @brief Limpia los datos adaptativos del detector de nombres.
       * @details Esto asegura que el modelo no retenga información del texto procesado previamente.
       */
      nameFinder.clearAdaptiveData();

    } catch (IOException e) {
      /**
       * @brief Captura y maneja errores de entrada/salida, como la carga del modelo.
       */
      e.printStackTrace();
    } finally {
      /**
       * @brief Cierra el flujo de entrada del modelo si está abierto.
       */
      if (modelIn != null) {
        try {
          modelIn.close();
        } catch (IOException e) {
          // Error al cerrar el flujo, no se toma acción adicional.
        }
      }
    }

    /**
     * @brief Indica que el proceso ha finalizado.
     */
    System.out.println("done");
  }
}

/**
 * @brief Fragmento comentado para generar nombres a partir de los tokens detectados.
 * @details Puede usarse para reconstruir las entidades nombradas detectadas desde los índices de `Span`.
 *
 * // StringBuilder sb = new StringBuilder();
 * // for (int i = ns.getStart(); i < ns.getEnd(); i++) {
 * //     sb.append(tokens[i]).append(" ");
 * // }
 * // System.out.println("The name is: " + sb.toString());
 */
