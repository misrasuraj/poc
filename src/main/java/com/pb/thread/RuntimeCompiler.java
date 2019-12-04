package com.pb.thread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by AB001MI on 11/15/2016.
 */
public class RuntimeCompiler
{
  // Prepare source somehow.
  String source = "package test; public class Test { static { System.out.println(\"hello\"); } public Test() { System.out.println(\"world\"); } }";

  // Save source in .java file.
  File file1 = new File("test.java"); // On Windows running on C:\, this is C:\java.
  //File sourceFile = new File(root, "test/Test.java");
FileChannel fileChannel;

  {
    try (FileChannel rw = fileChannel = new RandomAccessFile(file1, "rw").getChannel())
    {
      MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 4096 * 8 * 8);
      buffer.put(source.getBytes());
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  // Compile source file.
  /*JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
compiler.run(null, null, null, sourceFile.getPath());

  // Load and instantiate compiled class.
  URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
  Class<?> cls = Class.forName("test.Test", true, classLoader); // Should print "hello".
  Object instance = cls.newInstance(); // Should print "world".
System.out.println(instance); // Should print "test.Test@hashcode".*/
}
