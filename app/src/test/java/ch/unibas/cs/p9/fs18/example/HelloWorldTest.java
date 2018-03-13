package ch.unibas.cs.p9.fs18.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

/**
 * An example test class.
 *
 * @author CS108-FS18
 */
public class HelloWorldTest {
  
  
  private ByteArrayOutputStream outStream = new ByteArrayOutputStream();
  private ByteArrayOutputStream errStream = new ByteArrayOutputStream();
  
  private PrintStream outBackup;
  private PrintStream errBackup;
  
  @Before
  public void redirectStdOutStdErr(){
    outBackup = System.out;
    errBackup = System.err;
    System.setOut(new PrintStream(outStream));
    System.setErr(new PrintStream(errStream));
  }
  
  @After
  public void reestablishStdOutStdErr(){
    System.setOut(outBackup);
    System.setErr(errBackup);
  }
  
  @Test
  public void testMain(){
    HelloWorld.main(new String[0]);
    String toTest = outStream.toString();
    toTest = removeNewline(toTest);
    assertEquals("Hello World", toTest);
  }
  
  private static String removeNewline(String str){
    return str.replace("\n", "").replace("\r", "");
  }
}