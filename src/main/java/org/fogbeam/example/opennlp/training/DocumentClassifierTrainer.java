
package org.fogbeam.example.opennlp.training;


import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;


public class DocumentClassifierTrainer {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DocumentClassifierTrainer.class.getName());

  public static void main( String[] args ) throws Exception {
    DoccatModel model = null;
    InputStream dataIn = null;
    try {
      dataIn = new FileInputStream( "training_data/en-doccat.train" );
      ObjectStream<String> lineStream = new PlainTextByLineStream(
              dataIn, "UTF-8" );
      ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(
              lineStream );
      model = DocumentCategorizerME.train( "en", sampleStream );
    }
    catch (Exception e) {
      logger.severe("Error" + e.getMessage());
    }
    finally {
      if( dataIn != null ) {
        try {
          dataIn.close();
        }
        catch (Exception e) {
          logger.severe("Error" + e.getMessage());
        }
      }
    }
    OutputStream modelOut = null;
    String modelFile = "models/en-doccat.model";
    try {
      modelOut = new BufferedOutputStream( new FileOutputStream(
              modelFile ) );
      model.serialize( modelOut );
    }
    catch (Exception e) {
      logger.severe("Error" + e.getMessage());
    }
    finally {
      if( modelOut != null ) {
        try {
          modelOut.close();
        }
        catch (Exception e) {
          logger.severe("Error" + e.getMessage());
        }
      }
    }


    System.out.println( "done" );
  }
}
