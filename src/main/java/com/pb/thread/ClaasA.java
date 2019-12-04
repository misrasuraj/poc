package com.pb.thread;

import org.apache.commons.text.similarity.CosineDistance;
import org.apache.commons.text.similarity.FuzzyScore;
import org.apache.commons.text.similarity.HammingDistance;
import org.apache.commons.text.similarity.JaccardDistance;
import org.apache.commons.text.similarity.JaccardSimilarity;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.apache.commons.text.similarity.LevenshteinDetailedDistance;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.commons.text.similarity.LongestCommonSubsequence;
import org.apache.commons.text.similarity.LongestCommonSubsequenceDistance;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by AB001MI on 8/30/2016.
 */
public class ClaasA
{
  public static void main(String[] args) throws UnsupportedEncodingException
  {

   List<String> values = new ArrayList<>();
    values.add("abc");
    values.add("pqr");
    StringBuilder sb = new StringBuilder();
    for(String value : values)
    {
      sb.append(value);
      sb.append("\"");
      sb.append(",");
      sb.append("\"");
    }
    String listValues = sb.substring(0,sb.lastIndexOf("\""));
    String listvaluesUpdated = listValues.substring(0,listValues.lastIndexOf("\""));

    System.out.println("................................."+listValues);
    System.out.println("................................."+listvaluesUpdated);
    /*String[] stArray = st.split("\\.");
    Map<String, Object> map1 = new HashMap<>();
    Map<String, Object> map3 = new HashMap<>();
    map3.put("lastmodified", "value1");
    map1.put("properties", map3);
    System.out.println("map1......" + map1);

    Map<String, Object> map2 = new HashMap<>();
    Map<String, Object> map4 = new HashMap<>();
    Map<String, Object> map5 = new HashMap<>();
    map5.put("currentStatus", "value5");
    map5.put("error", "valueError");
    map4.put("discoveryStatus", map5);
    map2.put("properties", map4);
    System.out.println("map2......" + map2);
    //method1(map1, map2);
    //System.out.println("method1 map1 and 2......" + map1);

    method2(map1, map2);
    System.out.println("method2 map1 and 2......" + map1);

    Map<String, Object> map6 = new HashMap<>();
    Map<String, Object> map7 = new HashMap<>();
    Map<String, Object> map8 = new HashMap<>();
    map8.put("error", "value5");
    map7.put("discoveryStatus", map8);
   // map7.put("lastmodified", "value2");
    map6.put("properties", map7);
    //method1(map6, map1);
    //System.out.println("method1 map6 and 2......" + map6);
    System.out.println("map1......" + map1);
    System.out.println("map6......" + map6);
    method2(map1, map6);

    System.out.println("method2 map6 and 1......" + map1);*/

    FuzzyScore fz = new FuzzyScore(Locale.ENGLISH);
    CosineDistance cd = new CosineDistance();
    //CosineSimilarity cs = new CosineSimilarity();
    HammingDistance hd = new HammingDistance();
    JaccardDistance jd = new JaccardDistance();
    JaccardSimilarity js = new JaccardSimilarity(); //similarity algo
    JaroWinklerDistance jw = new JaroWinklerDistance();  //similarity algo
    LevenshteinDetailedDistance ldd = new LevenshteinDetailedDistance();
    LevenshteinDistance ld = new LevenshteinDistance();
    LongestCommonSubsequence lcs = new LongestCommonSubsequence(); //similarity algo
    LongestCommonSubsequenceDistance lcsd = new LongestCommonSubsequenceDistance();
    String to = "state";
    String from = "Lastname1";
    System.out.println("fuzzy :" + fz.fuzzyScore(from, from));
    //System.out.println("CosineDistance :"+cd.apply("nameasasas","nam"));
    //System.out.println(cs.("nameasasas","nam"));
    //System.out.println("HammingDistance :"+hd.apply("nameasasas","nameasasas"));

    /*System.out.println("JaccardDistance :" + jd.apply(from, to));
    System.out.println("JaccardSimilarity :" + js.apply(from, to));
    System.out.println("JaroWinklerDistance (similarity algo):" + jw.apply(from, to));
    System.out.println("LevenshteinDetailedDistance :" + ldd.apply(from, to));
    System.out.println("LevenshteinDistance :" + ld.apply(from, to));
    System.out.println("LongestCommonSubsequence (similarity algo):" + lcs.apply(from, to));
    System.out.println("LongestCommonSubsequenceDistance :" + lcsd.apply(from, to));*/

    String QUERY_STRING = "(( %s OR *%s* ) OR ( %s~1))";
    String searchTerm ="xyz";
    String updatedSearchTerm = String.format(QUERY_STRING,searchTerm,searchTerm,searchTerm);
    System.out.println("-----------------------------------------------------------");
    System.out.println(updatedSearchTerm);

/*    Map<String,String> filterMap = new HashMap<>();
    filterMap.put("type","column");
    filterMap.put("properties.spectrumDataType","string");

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("{\n"
            + "                  \"className\": \"aggregateFilter\",\n"
            + "                  \"filterCriteriaList\": [\n");
    for(Map.Entry<String,String> entry : filterMap.entrySet())
    {
      stringBuilder.append(
              "                    {\n"
                      + "                      \"className\": \"simpleFilter\",\n"
                      + "                      \"primaryOperand\": {\n"
                      + "                        \"name\": \""+entry.getKey()+"\"\n"
                      + "                      },\n"
                      + "                      \"secondaryOperand\": {\n"
                      + "                        \"values\": [\n"
                      + "                          \""+entry.getValue()+"\"\n"
                      + "                        ]\n"
                      + "                      },\n"
                      + "                      \"operator\": \"EQ\"\n"
                      + "                    }\n,");
    }
    stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
    stringBuilder.append( "                  ]\n"
            + "                }");

    System.out.println(stringBuilder.toString());
    System.out.println("------------------------------------------------------");


    System.out.println("{\n"
            + "                  \"className\": \"aggregateFilter\",\n"
            + "                  \"filterCriteriaList\": [\n"
            + "                    {\n"
            + "                      \"className\": \"simpleFilter\",\n"
            + "                      \"primaryOperand\": {\n"
            + "                        \"name\": \"type\"\n"
            + "                      },\n"
            + "                      \"secondaryOperand\": {\n"
            + "                        \"values\": [\n"
            + "                          \"column\"\n"
            + "                        ]\n"
            + "                      },\n"
            + "                      \"operator\": \"EQ\"\n"
            + "                    }\n,"
            + "                     {\n"
            + "                      \"className\": \"simpleFilter\",\n"
            + "                      \"primaryOperand\": {\n"
            + "                        \"name\": \"properties.spectrumDataType\"\n"
            + "                      },\n"
            + "                      \"secondaryOperand\": {\n"
            + "                        \"values\": [\n"
            + "                           \"abc\"\n"
            + "                        ]\n"
            + "                      },\n"
            + "                      \"operator\": \"EQ\"\n"
            + "                    }\n"
            + "                  ]\n"
            + "                }");*/
  }

  static Map method1(Map<String, Object> map1, Map<String, Object> map2)
  {
    Map.Entry<String, Object> entry1 = null;

    for (Map.Entry entry : map1.entrySet())
    {
      entry1 = entry;
    }

    Map.Entry<String, Object> entry2 = null;

    for (Map.Entry entry : map2.entrySet())
    {
      entry2 = entry;
    }

    if (entry1.getKey().equals(entry2.getKey()))
    {
      return method1((Map)map1.get(entry1.getKey()), (Map)map2.get(entry2.getKey()));
    }
    else
    {
      map1.putAll(map2);
      return map1;
    }

  }

  static Map method2(Map<String, Object> map1, Map<String, Object> map2)
  {
    Map.Entry<String, Object> entry1 = null;
    Map.Entry<String, Object> entry2 = null;

    for (Map.Entry entry : map1.entrySet())
    {
      entry1 = entry;
      int i = 0;
      for (Map.Entry entryy : map2.entrySet())
      {
        i++;
        entry2 = entryy;
        if (entry1.getKey().equals(entry2.getKey()) && !(entry1.getValue() instanceof String || entry1.getValue() instanceof String))
        {
          return method2((Map)map1.get(entry1.getKey()), (Map)map2.get(entry2.getKey()));
        }
        else if (map1.entrySet().size() == map2.entrySet().size() && i == map2.entrySet().size())
        {
          map1.putAll(map2);
          return map1;
        }
      }
    }
    return map2;
  }

}
