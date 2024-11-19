
package org.fogbeam.example.opennlp;

import java.io.*;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class TokenizerMain {
  public static void main(String[] args) throws Exception {
    // Verifica que al menos un archivo de entrada ha sido proporcionado
    if (args.length == 0) {
      System.out.println("Por favor, proporciona uno o más archivos de entrada.");
      return;
    }

    // Cargar el modelo de tokenización
    InputStream modelIn = new FileInputStream("models/en-token.model");

    // Crear el objeto Tokenizer
    TokenizerModel model = new TokenizerModel(modelIn);
    Tokenizer tokenizer = new TokenizerME(model);

    // El archivo de salida donde se guardarán los tokens
    BufferedWriter writer = new BufferedWriter(new FileWriter("output_tokens.txt"));

    try {
      // Procesar cada archivo proporcionado como argumento
      for (String filePath : args) {
        File inputFile = new File(filePath);
        if (inputFile.exists() && inputFile.isFile()) {
          System.out.println("Procesando archivo: " + filePath);

          // Leer el contenido del archivo
          String content = readFile(inputFile);

          // Tokenizar el contenido
          String[] tokens = tokenizer.tokenize(content);

          // Escribir los tokens al archivo de salida
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
      // Cerrar el archivo de salida y el modelo
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

  // Método para leer el contenido de un archivo
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
