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


package org.pentaho.di.core.row;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.pentaho.di.core.KettleClientEnvironment;
import org.pentaho.di.core.row.value.ValueMetaFactory;
import org.pentaho.di.junit.rules.RestorePDIEnvironment;

import static org.junit.Assert.assertEquals;

public class RowMetaAddRemoveValueTest {
  @ClassRule public static RestorePDIEnvironment env = new RestorePDIEnvironment();

  @BeforeClass
  public static void setUpClass() throws Exception {
    KettleClientEnvironment.init();
  }

  @Test
  public void testAddRemoveValue() throws Exception {

    RowMetaInterface rowMeta = new RowMeta();

    // Add values

    ValueMetaInterface a = ValueMetaFactory.createValueMeta( "a", ValueMetaInterface.TYPE_STRING );
    rowMeta.addValueMeta( a );
    assertEquals( 1, rowMeta.size() );
    ValueMetaInterface b = ValueMetaFactory.createValueMeta( "b", ValueMetaInterface.TYPE_INTEGER );
    rowMeta.addValueMeta( b );
    assertEquals( 2, rowMeta.size() );
    ValueMetaInterface c = ValueMetaFactory.createValueMeta( "c", ValueMetaInterface.TYPE_DATE );
    rowMeta.addValueMeta( c );
    assertEquals( 3, rowMeta.size() );

    assertEquals( 0, rowMeta.indexOfValue( "a" ) );
    assertEquals( 1, rowMeta.indexOfValue( "b" ) );
    assertEquals( 2, rowMeta.indexOfValue( "c" ) );

    ValueMetaInterface d = ValueMetaFactory.createValueMeta( "d", ValueMetaInterface.TYPE_NUMBER );
    rowMeta.addValueMeta( 0, d );
    assertEquals( 4, rowMeta.size() );

    assertEquals( 0, rowMeta.indexOfValue( "d" ) );
    assertEquals( 1, rowMeta.indexOfValue( "a" ) );
    assertEquals( 2, rowMeta.indexOfValue( "b" ) );
    assertEquals( 3, rowMeta.indexOfValue( "c" ) );

    ValueMetaInterface e = ValueMetaFactory.createValueMeta( "e", ValueMetaInterface.TYPE_BIGNUMBER );
    rowMeta.addValueMeta( 2, e );
    assertEquals( 5, rowMeta.size() );

    assertEquals( 0, rowMeta.indexOfValue( "d" ) );
    assertEquals( 1, rowMeta.indexOfValue( "a" ) );
    assertEquals( 2, rowMeta.indexOfValue( "e" ) );
    assertEquals( 3, rowMeta.indexOfValue( "b" ) );
    assertEquals( 4, rowMeta.indexOfValue( "c" ) );

    // Remove values in reverse order
    rowMeta.removeValueMeta( "e" );
    assertEquals( 4, rowMeta.size() );
    assertEquals( 0, rowMeta.indexOfValue( "d" ) );
    assertEquals( 1, rowMeta.indexOfValue( "a" ) );
    assertEquals( 2, rowMeta.indexOfValue( "b" ) );
    assertEquals( 3, rowMeta.indexOfValue( "c" ) );

    rowMeta.removeValueMeta( "d" );
    assertEquals( 3, rowMeta.size() );
    assertEquals( 0, rowMeta.indexOfValue( "a" ) );
    assertEquals( 1, rowMeta.indexOfValue( "b" ) );
    assertEquals( 2, rowMeta.indexOfValue( "c" ) );

    rowMeta.removeValueMeta( "c" );
    assertEquals( 2, rowMeta.size() );
    assertEquals( 0, rowMeta.indexOfValue( "a" ) );
    assertEquals( 1, rowMeta.indexOfValue( "b" ) );

    rowMeta.removeValueMeta( "b" );
    assertEquals( 1, rowMeta.size() );
    assertEquals( 0, rowMeta.indexOfValue( "a" ) );

    rowMeta.removeValueMeta( "a" );
    assertEquals( 0, rowMeta.size() );

  }

  @Test
  public void testAddRemoveRenameValue() throws Exception {

    RowMetaInterface rowMeta = new RowMeta();

    // Add values

    ValueMetaInterface a = ValueMetaFactory.createValueMeta( "a", ValueMetaInterface.TYPE_STRING );
    rowMeta.addValueMeta( a );
    assertEquals( 1, rowMeta.size() );
    ValueMetaInterface b = ValueMetaFactory.createValueMeta( "a", ValueMetaInterface.TYPE_INTEGER );
    rowMeta.addValueMeta( b );
    assertEquals( 2, rowMeta.size() );
    ValueMetaInterface c = ValueMetaFactory.createValueMeta( "a", ValueMetaInterface.TYPE_DATE );
    rowMeta.addValueMeta( c );
    assertEquals( 3, rowMeta.size() );

    assertEquals( 0, rowMeta.indexOfValue( "a" ) );
    assertEquals( 1, rowMeta.indexOfValue( "a_1" ) );
    assertEquals( 2, rowMeta.indexOfValue( "a_2" ) );

    ValueMetaInterface d = ValueMetaFactory.createValueMeta( "a", ValueMetaInterface.TYPE_NUMBER );
    rowMeta.addValueMeta( 0, d );
    assertEquals( 4, rowMeta.size() );

    assertEquals( 0, rowMeta.indexOfValue( "a_3" ) );
    assertEquals( 1, rowMeta.indexOfValue( "a" ) );
    assertEquals( 2, rowMeta.indexOfValue( "a_1" ) );
    assertEquals( 3, rowMeta.indexOfValue( "a_2" ) );

    ValueMetaInterface e = ValueMetaFactory.createValueMeta( "a", ValueMetaInterface.TYPE_BIGNUMBER );
    rowMeta.addValueMeta( 2, e );
    assertEquals( 5, rowMeta.size() );

    assertEquals( 0, rowMeta.indexOfValue( "a_3" ) );
    assertEquals( 1, rowMeta.indexOfValue( "a" ) );
    assertEquals( 2, rowMeta.indexOfValue( "a_4" ) );
    assertEquals( 3, rowMeta.indexOfValue( "a_1" ) );
    assertEquals( 4, rowMeta.indexOfValue( "a_2" ) );

    // Remove values in reverse order
    rowMeta.removeValueMeta( "a_4" );
    assertEquals( 4, rowMeta.size() );
    assertEquals( 0, rowMeta.indexOfValue( "a_3" ) );
    assertEquals( 1, rowMeta.indexOfValue( "a" ) );
    assertEquals( 2, rowMeta.indexOfValue( "a_1" ) );
    assertEquals( 3, rowMeta.indexOfValue( "a_2" ) );

    rowMeta.removeValueMeta( "a_3" );
    assertEquals( 3, rowMeta.size() );
    assertEquals( 0, rowMeta.indexOfValue( "a" ) );
    assertEquals( 1, rowMeta.indexOfValue( "a_1" ) );
    assertEquals( 2, rowMeta.indexOfValue( "a_2" ) );

    rowMeta.removeValueMeta( "a_2" );
    assertEquals( 2, rowMeta.size() );
    assertEquals( 0, rowMeta.indexOfValue( "a" ) );
    assertEquals( 1, rowMeta.indexOfValue( "a_1" ) );

    rowMeta.removeValueMeta( "a_1" );
    assertEquals( 1, rowMeta.size() );
    assertEquals( 0, rowMeta.indexOfValue( "a" ) );

    rowMeta.removeValueMeta( "a" );
    assertEquals( 0, rowMeta.size() );

  }

  @Test
  public void testAddRemoveValueCaseInsensitive() throws Exception {

    RowMetaInterface rowMeta = new RowMeta();

    // Add values

    ValueMetaInterface a = ValueMetaFactory.createValueMeta( "A", ValueMetaInterface.TYPE_STRING );
    rowMeta.addValueMeta( a );
    assertEquals( 1, rowMeta.size() );
    ValueMetaInterface b = ValueMetaFactory.createValueMeta( "b", ValueMetaInterface.TYPE_INTEGER );
    rowMeta.addValueMeta( b );
    assertEquals( 2, rowMeta.size() );
    ValueMetaInterface c = ValueMetaFactory.createValueMeta( "C", ValueMetaInterface.TYPE_DATE );
    rowMeta.addValueMeta( c );
    assertEquals( 3, rowMeta.size() );

    assertEquals( 0, rowMeta.indexOfValue( "a" ) );
    assertEquals( 1, rowMeta.indexOfValue( "B" ) );
    assertEquals( 2, rowMeta.indexOfValue( "c" ) );

    ValueMetaInterface d = ValueMetaFactory.createValueMeta( "d", ValueMetaInterface.TYPE_NUMBER );
    rowMeta.addValueMeta( 0, d );
    assertEquals( 4, rowMeta.size() );

    assertEquals( 0, rowMeta.indexOfValue( "D" ) );
    assertEquals( 1, rowMeta.indexOfValue( "a" ) );
    assertEquals( 2, rowMeta.indexOfValue( "B" ) );
    assertEquals( 3, rowMeta.indexOfValue( "c" ) );

    ValueMetaInterface e = ValueMetaFactory.createValueMeta( "E", ValueMetaInterface.TYPE_BIGNUMBER );
    rowMeta.addValueMeta( 2, e );
    assertEquals( 5, rowMeta.size() );

    assertEquals( 0, rowMeta.indexOfValue( "D" ) );
    assertEquals( 1, rowMeta.indexOfValue( "a" ) );
    assertEquals( 2, rowMeta.indexOfValue( "e" ) );
    assertEquals( 3, rowMeta.indexOfValue( "b" ) );
    assertEquals( 4, rowMeta.indexOfValue( "c" ) );

    // Remove values in reverse order
    rowMeta.removeValueMeta( "e" );
    assertEquals( 4, rowMeta.size() );
    assertEquals( 0, rowMeta.indexOfValue( "d" ) );
    assertEquals( 1, rowMeta.indexOfValue( "A" ) );
    assertEquals( 2, rowMeta.indexOfValue( "b" ) );
    assertEquals( 3, rowMeta.indexOfValue( "C" ) );

    rowMeta.removeValueMeta( "D" );
    assertEquals( 3, rowMeta.size() );
    assertEquals( 0, rowMeta.indexOfValue( "a" ) );
    assertEquals( 1, rowMeta.indexOfValue( "B" ) );
    assertEquals( 2, rowMeta.indexOfValue( "c" ) );

    rowMeta.removeValueMeta( "c" );
    assertEquals( 2, rowMeta.size() );
    assertEquals( 0, rowMeta.indexOfValue( "a" ) );
    assertEquals( 1, rowMeta.indexOfValue( "B" ) );

    rowMeta.removeValueMeta( "b" );
    assertEquals( 1, rowMeta.size() );
    assertEquals( 0, rowMeta.indexOfValue( "a" ) );

    rowMeta.removeValueMeta( "a" );
    assertEquals( 0, rowMeta.size() );

  }

}
