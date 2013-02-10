// (c) me & GPL3
// Dictionary Magic is just a play around with various java collections

import java.io.File;
import java.util.Scanner;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Collections;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;


public class DMagic {
   public static List<String> symetricCharacters = new ArrayList<String>();
   public static Map<String, String> symetricPairs = new HashMap<String, String>();

   public static void main(String[] args) {
      File wordFileA = new File("en-common.wl");
      File wordFileB = new File("dictionary.txt");
      Random rng = new Random();
      rng.setSeed(666);

//non-symetric letters: ersfgjklzcn
//possibly symetric letters: wtyuioahxvm       pairs: qpdb
      symetricCharacters.add( "w" );
      symetricCharacters.add( "t" );
      symetricCharacters.add( "y" );
      symetricCharacters.add( "u" );
      symetricCharacters.add( "i" );
      symetricCharacters.add( "o" );
      symetricCharacters.add( "a" );
      symetricCharacters.add( "h" );
      symetricCharacters.add( "x" );
      symetricCharacters.add( "v" );
      symetricCharacters.add( "m" );
      symetricPairs.put("p","q");
      symetricPairs.put("q","p");
      symetricPairs.put("b","d");
      symetricPairs.put("d","b");



      List<String> resultList = new ArrayList<String>();
      
      HashSet<String> wordHashSet = new HashSet<String>();
      TreeSet<String> wordTreeSet = new TreeSet<String>();
      LinkedHashSet<String> wordLHSet = new LinkedHashSet<String>();
      List<String> arrayList = new ArrayList<String>();
      List<String> linkedList = new LinkedList<String>();

      int wordCount = 0;
      wordCount = readWordList(wordFileB, wordHashSet);
      wordCount = readWordList(wordFileB, wordTreeSet);
      wordCount = readWordList(wordFileB, wordLHSet);
      wordCount = readWordList(wordFileB, arrayList);
      wordCount = readWordList(wordFileB, linkedList);


      resultList = firstTenSearch(wordHashSet);
      printResultList(resultList, "First Ten Search using Hash Set");
      resultList = palendromeSearch(wordHashSet);
      resultList = trimShortWords( resultList, 5 );
      printResultList(resultList, "Palendrome Search using Hash Set");

      resultList = firstTenSearch(wordTreeSet);
      printResultList(resultList, "First Ten Search using Tree Set");
      resultList = lastTenSearch(wordTreeSet);
      printResultList(resultList, "Last Ten Search using Tree Set");

      resultList = firstTenSearch(wordLHSet);
      printResultList(resultList, "First Ten Search using L-H Set");

      resultList = firstTenSearch(arrayList);
      printResultList(resultList, "First Ten Search using Array List");
      resultList = randomTenSearch(arrayList, rng);
      printResultList(resultList, "Random Ten Search using Array List");

      resultList = firstTenSearch(linkedList);
      printResultList(resultList, "First Ten Search using Linked List");
      System.out.println(" Longest Word: " + longestLine(resultList));
      resultList = randomTenSearch(linkedList, rng);
      printResultList(resultList, "Random Ten Search using Linked List");
      resultList = palendromeSearch(linkedList);
      resultList = trimShortWords( resultList, 5 );
      printResultList(resultList, "Palendrome Search using Linked List");
      System.out.println(" Longest Palendrome: " + longestLine(resultList));



      //anagram groups
      Map<String, List<String>> anagramMap = new HashMap<String, List<String>>();
      groupAnagrams(linkedList, anagramMap);
      System.out.println("Anagram Groups of 7 or more words: ");
      for ( List<String> currentEntries : anagramMap.values() ) {
          if (currentEntries.size() > 6) {
              System.out.print("Group of size " + currentEntries.size() + ":");
              System.out.print("  [ ");
              for( String thisEntry : currentEntries ) {
                  System.out.print(thisEntry + " ");
              }
              System.out.print("]\n");
          }
      }

      List<String> notShortList = trimShortWords( linkedList, 2 );
      notShortList = dropFromList( notShortList, 90 );
      Map<String, String> palendromePairs = new HashMap<String, String>();
      findPalendromePairs(notShortList, palendromePairs);
      printPairResultList(palendromePairs, "Palendromic Pairs (over 5% sub-dictionary):");
      palendromePairs = new HashMap<String, String>();
      findSymetricPairs(notShortList, palendromePairs);
      printPairResultList(palendromePairs, "Symetric palendromic Pairs (sub-dictionary):");

//      System.out.println("symetricCharacters");
//      for( String s : symetricCharacters ) {
//         System.out.println("- " + s);
//      }
//      System.out.println("is o in sym: " + symetricCharacters.contains("o") );
//      System.out.println("is o in sym: " + symetricCharacters.contains("o".charAt(0)) );
//      System.out.println("is o in sym: " + symetricCharacters.contains(Character.toString("o".charAt(0))) );

      System.out.println("The magic is over :(");
   } //main


   private static List<String> dropFromList ( List<String> inputList, int dropPercent ) {
       ArrayList<String> returnList = new ArrayList<String>();
       int i = 0;
       for ( String thisWord : inputList ) {
           if( i > dropPercent ) {
               returnList.add(thisWord);
           }
           i = (i+1)%100;
       }
       return returnList;
   } //notshort

   private static List<String> trimShortWords ( Collection<String> inputList, int minLength ) {
       ArrayList<String> returnList = new ArrayList<String>();
       for ( String thisWord : inputList ) {
           if( thisWord.length() > minLength ) {
               returnList.add(thisWord);
           } 
       }
       return returnList;
   } //notshort


   private static void findPalendromePairs(List<String> wordList, Map<String, String> anagramPairs ) {
      for ( String thisWord : wordList ) {
          String reverseWord = new StringBuffer(thisWord).reverse().toString();
          for ( String otherWord : wordList ) {
             if( reverseWord.equals(otherWord) ) {
                anagramPairs.put(thisWord, otherWord);
             }
          }//for other word
      } //for this word
   } //findAnagramPairs

   private static void findSymetricPairs(List<String> wordList, Map<String, String> symetricPairs ) {
      for ( String thisWord : wordList ) {
          String reverseWord = new StringBuffer(thisWord).reverse().toString();
          for ( String otherWord : wordList ) {
             //if( reverseWord.equals(otherWord) ) {
             //   anagramPairs.put(thisWord, otherWord);
             //}
             if( thisWord.length() == otherWord.length() ) {
                boolean isSymetricPair = true;
                for( int i = 0; isSymetricPair && i < thisWord.length(); i++ ) {
                    //System.out.print(" >" + i + "/" + thisWord.length());
//                    if(thisWord.equals("otto")){System.out.println("i:"+i+"  char:"+ thisWord.charAt(i) +"  symetric:"+ symetricCharacters.contains( thisWord.charAt(i)));};
                    if(symetricCharacters.contains( Character.toString(thisWord.charAt(i)) ) ) {
                       if(thisWord.charAt(i) == otherWord.charAt(otherWord.length()-(i+1))) {
                         //nop
                       } else {
                           isSymetricPair = false;
                       }
                    } else if ( symetricPairs.containsKey( Character.toString(thisWord.charAt(i)) ) ) {
                       if( thisWord.charAt(i) == symetricPairs.get(otherWord.charAt(otherWord.length()-(i+1))).charAt(0) ) {
                       } else {
                           isSymetricPair = false;
                       }
                    } else {
                        isSymetricPair = false;
                    }
                }
                if (isSymetricPair) {
                    symetricPairs.put(thisWord, otherWord);
                }
             }//if equal length
          }//for other word
      } //for this word
   } //findAnagramPairs


   private static void groupAnagrams(List<String> wordList, Map<String, List<String>> anagramGroups) {
      for ( String thisWord : wordList ) {
          char[] chars = thisWord.toCharArray();
          Arrays.sort ( chars );
          String alphabetizedWord = new String ( chars );
          List<String> currentEntries = anagramGroups.get ( alphabetizedWord );
          if ( currentEntries == null ) {
              currentEntries = new ArrayList<String>();
              anagramGroups.put ( alphabetizedWord, currentEntries );
          }
          if( ! currentEntries.contains ( thisWord ) ) {
              currentEntries.add ( thisWord );
          }
       }//for
   } //groupAnagrams


   private static void printResultList(List<String> printList, String title) {
      System.out.println(" == "+title+ " == ");
      System.out.print(" ");
      for (int i = 0; i < title.length()+6; i++) {
          System.out.print("=");
      }
      System.out.print("\n");
      for( String s : printList ) {
         System.out.println("-"+s);
      }
   }

   private static void printPairResultList(Map<String, String> printList, String title) {
      System.out.println(" == "+title+ " == ");
      System.out.print(" ");
      for (int i = 0; i < title.length()+6; i++) {
          System.out.print("=");
      }
      System.out.print("\n");
      int paddingCount = longestLine(printList.keySet()).length();
      for( String s : printList.keySet() ) {
         int padding = paddingCount - s.length();
         for (int i = -2; i < padding; i++) {
            System.out.print(" ");
         }
         System.out.println( s+"<=>"+printList.get(s) );
      }
   }


   private static List<String> lastTenSearch(TreeSet<String> wordHashSet) {
      ArrayList<String> returnList = new ArrayList<String>();
      for(int i = 0; i < 10; i++) {
         returnList.add(wordHashSet.pollLast());
      }
      return returnList;
   } //lastTenSearch

   private static List<String> firstTenSearch(Collection<String> wordHashSet) {
      Iterator iter = wordHashSet.iterator();
      ArrayList<String> returnList = new ArrayList<String>();
      for(int i = 0; i < 10; i++) {
         returnList.add(iter.next().toString());
      }
      return returnList;
   } //firstTenSearch

   private static List<String> randomTenSearch(List<String> wordHashSet, Random rng) {
      ArrayList<String> returnList = new ArrayList<String>();
      for(int i = 0; i < 10; i++) {
         returnList.add(wordHashSet.get(rng.nextInt(wordHashSet.size())));
      }
      return returnList;
   } //randomTenSearch

   private static List<String> palendromeSearch(Collection<String> wordHashSet) {
      Iterator iter = wordHashSet.iterator();
      ArrayList<String> returnList = new ArrayList<String>();
      while(iter.hasNext()) {
         String thisText = iter.next().toString();
         if(isPalendrome(thisText) && thisText.length() > 2) {
            returnList.add(thisText);
         }
      }
      return returnList;
   } //palendrome

   private static String longestLine(Collection<String> wordList) {
      Iterator iter = wordList.iterator();
      String longestLine = "";
      int lineLength = 0;
      while(iter.hasNext()) {
         String thisText = iter.next().toString();
         if(thisText.length() > lineLength) {
            lineLength = thisText.length();
            longestLine = thisText;
         }
      }
      return longestLine;
   } //longest len


   private static boolean isPalendrome( String inputString ) {
      boolean returnValue = false;
      int splitPoint = inputString.length()/2;
      int splitOverflow = inputString.length()%2;
      if(splitPoint > 0) {
         String firstHalf;
         String secondHalf;
         firstHalf = inputString.substring(0,splitPoint);
         secondHalf = inputString.substring(splitPoint+splitOverflow, inputString.length());
         returnValue = true;
         while (splitPoint > 0) {
            splitPoint--;
            if( !(firstHalf.charAt(splitPoint) == secondHalf.charAt(secondHalf.length()-(splitPoint+1))) ) {
               returnValue = false;
            }
         }
      }
      return returnValue;
   }


   private static int readWordList(File wordFile, HashSet<String> wordHashSet) {
      int wordCount = 0;
      try {
         Scanner wordScanner = new Scanner(wordFile);
         while(wordScanner.hasNext()) {
            String inputWord = wordScanner.next().toLowerCase();
            if( wordHashSet.add(inputWord) ) {
               wordCount++;
            } else {
//               System.out.print("  DUPE: " + inputWord);
            }
         } //while
      } catch (Exception e) {
         System.out.println("Exception creating scanner: " + e);
      }
      return wordCount;
   } //readWordList

   private static int readWordList(File wordFile, TreeSet<String> wordHashSet) {
      int wordCount = 0;
      try {
         Scanner wordScanner = new Scanner(wordFile);
         while(wordScanner.hasNext()) {
            String inputWord = wordScanner.next().toLowerCase();
            if( wordHashSet.add(inputWord) ) {
               wordCount++;
            }
         } //while
      } catch (Exception e) {
         System.out.println("Exception creating scanner: " + e);
      }
      return wordCount;
   } //readWordList

   private static int readWordList(File wordFile, LinkedHashSet<String> wordHashSet) {
      int wordCount = 0;
      try {
         Scanner wordScanner = new Scanner(wordFile);
         while(wordScanner.hasNext()) {
            String inputWord = wordScanner.next().toLowerCase();
            if( wordHashSet.add(inputWord) ) {
               wordCount++;
            }
         } //while
      } catch (Exception e) {
         System.out.println("Exception creating scanner: " + e);
      }
      return wordCount;
   } //readWordList

   private static int readWordList(File wordFile, List<String> wordHashSet) {
      int wordCount = 0;
      try {
         Scanner wordScanner = new Scanner(wordFile);
         while(wordScanner.hasNext()) {
            String inputWord = wordScanner.next().toLowerCase();
            if( wordHashSet.add(inputWord) ) {
               wordCount++;
            }
         } //while
      } catch (Exception e) {
         System.out.println("Exception creating scanner: " + e);
      }
      return wordCount;
   } //readWordList

   private static int readWordList(File wordFile, LinkedList<String> wordHashSet) {
      int wordCount = 0;
      try {
         Scanner wordScanner = new Scanner(wordFile);
         while(wordScanner.hasNext()) {
            String inputWord = wordScanner.next().toLowerCase();
            if( wordHashSet.add(inputWord) ) {
               wordCount++;
            }
         } //while
      } catch (Exception e) {
         System.out.println("Exception creating scanner: " + e);
      }
      return wordCount;
   } //readWordList


} //class
