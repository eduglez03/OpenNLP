
package org.fogbeam.example.opennlp.training;


import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.postag.WordTagSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;


public class PartOfSpeechTaggerTrainer {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PartOfSpeechTaggerTrainer.class.getName());

  public static void main( String[] args ) {
    POSModel model = null;
    InputStream dataIn = null;
    try {
      dataIn = new FileInputStream( "training_data/en-pos.train" );
      ObjectStream<String> lineStream = new PlainTextByLineStream(
              dataIn, "UTF-8" );
      ObjectStream<POSSample> sampleStream = new WordTagSampleStream(
              lineStream );
      model = POSTaggerME.train( "en", sampleStream,
              TrainingParameters.defaultParams(), null, null );
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
    String modelFile = "models/en-pos.model";
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
