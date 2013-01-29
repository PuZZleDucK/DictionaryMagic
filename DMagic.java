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

   public static void main(String[] args) {
      File wordFile = new File("en-common.wl");
      Random rng = new Random();
      rng.setSeed(666);
      List<String> resultList = new ArrayList<String>();
      
      HashSet<String> wordHashSet = new HashSet<String>();
      TreeSet<String> wordTreeSet = new TreeSet<String>();
      LinkedHashSet<String> wordLHSet = new LinkedHashSet<String>();
      List<String> arrayList = new ArrayList<String>();
      List<String> linkedList = new LinkedList<String>();

      int wordCount = 0;
      wordCount = readWordList(wordFile, wordHashSet);
      wordCount = readWordList(wordFile, wordTreeSet);
      wordCount = readWordList(wordFile, wordLHSet);
      wordCount = readWordList(wordFile, arrayList);
      wordCount = readWordList(wordFile, linkedList);


      resultList = firstTenSearch(wordHashSet);
      printResultList(resultList, "First Ten Search using Hash Set");
      resultList = palendromeSearch(wordHashSet);
      printResultList(resultList, "Palendrome Search using Hash Set");

//         Iterator iter = wordHashSet.iterator();
//         System.out.println("First 10 (of "+ wordHashSet.size()
//            +") hash set entries:");
//         for ( int i = 0; i < 10; i++ ) {
//           System.out.println(i+": "+iter.next());
//         }

      resultList = firstTenSearch(wordTreeSet);
      printResultList(resultList, "First Ten Search using Tree Set");
      resultList = lastTenSearch(wordTreeSet);
      printResultList(resultList, "Last Ten Search using Tree Set");


//         Iterator iter = wordTreeSet.iterator();
//         System.out.println("First and last 10 (of "+ wordTreeSet.size()
//            +") tree set entries:");
//         System.out.println(" :  pollfirst\t:  polllast");
//         for ( int i = 0; i < 10; i++ ) {
//           System.out.println(i+": " 
//                + wordTreeSet.pollFirst()
 //              + "    \t: " +  );
//         }

      resultList = firstTenSearch(wordLHSet);
      printResultList(resultList, "First Ten Search using L-H Set");

//         Iterator iter = wordLHSet.iterator();
//         System.out.println("First 10 linked-hash entries:");
//         for ( int i = 0; i < 10; i++ ) {
//           System.out.println(i+": "+iter.next());
//         }

      resultList = firstTenSearch(arrayList);
      printResultList(resultList, "First Ten Search using Array List");
      resultList = randomTenSearch(arrayList, rng);
      printResultList(resultList, "Random Ten Search using Array List");


//         Iterator iter = arrayList.iterator();
//         System.out.println("First and random 10 (of "
//                + arrayList.size() + ") array list entries:");
//         System.out.println("  first  \t random");
//         for ( int i = 0; i < 10; i++ ) {
//           System.out.println(i+": "+iter.next() +" \t\t: "
//                + arrayList.get(rng.nextInt(arrayList.size())));
//         }
      resultList = firstTenSearch(linkedList);
      printResultList(resultList, "First Ten Search using Linked List");
      System.out.println(" Longest Word: " + longestLine(resultList));
      resultList = randomTenSearch(linkedList, rng);
      printResultList(resultList, "Random Ten Search using Linked List");
      resultList = palendromeSearch(linkedList);
//      printResultList(resultList, "Palendrome Search using Linked List");
      System.out.println(" Longest Palendrome: " + longestLine(resultList));

//         Iterator iter = linkedList.iterator();
//         System.out.println("First 10 linked list entries:");
//         for ( int i = 0; i < 10; i++ ) {
//           System.out.println(i+": "+iter.next());
//         }

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


      System.out.println("The magic is over :(");
   } //main


   private static void groupAnagrams(List<String> wordList, Map<String, List<String>> anagramGroups) {
      for( String thisWord : wordList ) {
          char[] chars = thisWord.toCharArray();
          Arrays.sort( chars );
          String alphabetizedWord = new String(chars);
//          System.out.println("a: " + thisWord + "   sorted: " + output + "");
          List<String> currentEntries = anagramGroups.get(alphabetizedWord);
          if ( currentEntries == null ) {
              currentEntries = new ArrayList<String>();
              anagramGroups.put(alphabetizedWord, currentEntries);
          }
          if( ! currentEntries.contains(thisWord) ) {
              currentEntries.add(thisWord);
          }
      }//for

//      for ( List<String> currentEntries : anagramGroups.values() ) {
//          System.out.println("group: " + currentEntries.get(0) + " - (" + currentEntries.size() + ")" );
//      }

   } //groupAnagrams


   private static void printResultList(List<String> printList, String title) {
      System.out.println(" == "+title+ " == ");
      System.out.print(" ");
      for (int i = 0; i < title.length()+6; i++) {
          System.out.print("=");
      }
      System.out.print("\n");
//      System.out.println(" ================================================= ");
      for( String s : printList ) {
         System.out.println("-"+s);
      }
   }


   private static List<String> lastTenSearch(TreeSet<String> wordHashSet) {
      //Iterator iter = wordHashSet.iterator();
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
//      Iterator iter = wordHashSet.iterator();
      ArrayList<String> returnList = new ArrayList<String>();
      for(int i = 0; i < 10; i++) {
         //iter.next().toString());
         returnList.add(wordHashSet.get(rng.nextInt(wordHashSet.size())));
      }
      return returnList;
   } //firstTenSearch

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

   private static String longestLine(List<String> wordList) {
      Iterator iter = wordList.iterator();
//      ArrayList<String> returnList = new ArrayList<String>();
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
   } //longest palen


   private static boolean isPalendrome( String inputString ) {
      //inputString
      boolean returnValue = false;
      int splitPoint = inputString.length()/2;
      int splitOverflow = inputString.length()%2;
//      System.out.println(inputString +": " + inputString.length()
//            +" split: " + splitPoint);
      if(splitPoint > 0) {
         String firstHalf;
         String secondHalf;
         firstHalf = inputString.substring(0,splitPoint);
         secondHalf = inputString.substring(splitPoint+splitOverflow, inputString.length());
//         System.out.println(inputString +": " + firstHalf
//            +" / " + secondHalf);
         returnValue = true;
         while (splitPoint > 0) {
            splitPoint--;
//            System.out.println("char: " + firstHalf.charAt(splitPoint) +
//                            " : " + secondHalf.charAt(secondHalf.length()-(splitPoint+1))  );
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
//         wordHashSet.add("aaaaaaaaaxxzxzzxzxxzxicle");
//         int wordCount = 0;
         while(wordScanner.hasNext()) {
            String inputWord = wordScanner.next().toLowerCase();
            if( wordHashSet.add(inputWord) ) {
              // System.out.print("  "+wordCount+": " + inputWord);
               wordCount++;
            } else {
//               System.out.print("  DUPE: " + inputWord);
            }
            //wordTreeSet.add(inputWord);
            //wordLHSet.add(inputWord);
            //arrayList.add(inputWord);
            //linkedList.add(inputWord);
         } //while
         //System.out.println("");
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
