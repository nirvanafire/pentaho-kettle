/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.di.trans.steps.fixedinput;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.step.BaseStepData;
import org.pentaho.di.trans.step.StepDataInterface;

/**
 * @author Matt
 * @since 24-jan-2005
 *
 */
public class FixedInputData extends BaseStepData implements StepDataInterface {

  public FileInputStream fis;
  public FileChannel fc;
  public ByteBuffer bb;
  public RowMetaInterface outputRowMeta;
  public RowMetaInterface convertRowMeta;

  public byte[] byteBuffer;
  public int startBuffer;
  public int endBuffer;
  public int bufferSize;

  public byte[] delimiter;
  public byte[] enclosure;

  public int preferredBufferSize;
  public String filename;
  public int lineWidth;
  public boolean stopReading;
  public int stepNumber;
  public int totalNumberOfSteps;
  public long fileSize;
  public long rowsToRead;
  private int loadPoint;

  public FixedInputData() {
    super();
    byteBuffer = new byte[] {};
    startBuffer = 0;
    endBuffer = 0;
  }

  // Resize
  public void resizeByteBuffer() {
    // What's the new size?
    // It's (endBuffer-startBuffer)+size !!
    // That way we can at least read one full block of data using NIO
    //
    if ( bufferSize == 0 ) {
      bufferSize = 0;
      loadPoint = 0;
    } else {
      bufferSize = bufferSize - startBuffer; // 50.000 - 49.996 = 4
      loadPoint = bufferSize; // 4
    }
    int newSize = bufferSize + preferredBufferSize;
    byte[] newByteBuffer = new byte[newSize];

    // copy over the old data...
    for ( int i = 0; i < bufferSize; i++ ) {
      newByteBuffer[i] = byteBuffer[i + startBuffer];
    }

    // replace the old byte buffer...
    byteBuffer = newByteBuffer;

    // Adjust start and end point of data in the byte buffer
    //
    endBuffer -= startBuffer;
    startBuffer = 0;
  }

  public void readBufferFromFile() throws IOException {
    bb.position( 0 );
    int n = fc.read( bb );
    if ( n == -1 ) {
      // Nothing more to be found in the file...
      stopReading = true;
    } else {
      // adjust the highest used position...
      //
      bufferSize += n;

      // Store the data in our byte array
      //
      bb.position( 0 );
      bb.get( byteBuffer, loadPoint, n );
    }
  }

}
