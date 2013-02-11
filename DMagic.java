
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
import java.lang.Runtime;

/** 
  * Dictionary Macic (<code>DMagic</code>) is a set of utilities for manipulating a
  * dictionary list of words and performing search and sort operations
  * on them.
  * <p>
  * DMagic can also handle the loading of dictionary lists to and from
  * various file formats.
  * 
  * @see Collections File
  */
public class DMagic {
   public static List<String> symetricCharacters = new ArrayList<String>();
   public static Map<String, String> symetricPairs = new HashMap<String, String>();

   public static void main(String[] args) {

//      run javadoc:
     // com.sun.tools.javadoc.Main.execute(new String[] {"-d", "docs", "-sourcepath", "/home/puzzleduck/java-docs", "p1", "p2"});
      //Runtime r = new Runtime();
      //r.exec("javadoc Dmagic");


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
      resultList = trimShortWords( resultList, 6 );
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
      resultList = trimShortWords( resultList, 6 );
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

      List<String> notShortList = trimShortWords( linkedList, 3 );
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


/** 
  * dropFromList will create a new <code>List</code> with only (100-propPercent) out
  * of every 100 words included in the <code>List</code>.
  * 
  * @param inputList   The origional list to create the sub-list with.
  * @param dropPercent The number out of 100 of items to not include in the new sub-list.
  * @return            A sub-list containing (100-dropPercent)% of the inputList
  * @see               List String
  */
   public static List<String> dropFromList ( List<String> inputList, int dropPercent ) {
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

/** 
  * trimShortWords will create a new <code>List</code> with only the words from the
  * origional <code>Collection</code> that have a length of at least minLength.
  * 
  * @param inputList The origional list to create the sub-list with.
  * @param minLength The minimum length of a string to be included in the new sub-list.
  * @return          A sub-list containing Strings from inputList of minLength length or longer.
  * @see             List Collection String
  */
   public static List<String> trimShortWords ( Collection<String> inputList, int minLength ) {
       ArrayList<String> returnList = new ArrayList<String>();
       for ( String thisWord : inputList ) {
           if( thisWord.length() >= minLength ) {
               returnList.add(thisWord);
           } 
       }
       return returnList;
   } //notshort


/** 
  * findPalendromePairs will append to the supplied <code>Map</code> pairs of words
  * from the origional <code>List</code> that form a pair of words which form a
  * palendrome when concatenated together, for example "dog" and "god".
  * 
  * @param wordList     The origional list to create the Map with.
  * @param anagramPairs This is the Map into which the found pairs will be added, it must be initialized, but does not have to be empty.
  */
   public static void findPalendromePairs(List<String> wordList, Map<String, String> anagramPairs ) {
      for ( String thisWord : wordList ) {
          String reverseWord = new StringBuffer(thisWord).reverse().toString();
          for ( String otherWord : wordList ) {
             if( reverseWord.equals(otherWord) ) {
                anagramPairs.put(thisWord, otherWord);
             }
          }//for other word
      } //for this word
   } //findAnagramPairs

/** 
  * findSymetricPairs will append to the supplied <code>Map</code> pairs of words
  * from the origional <code>List</code> that form a pair of words which form a visual
  * palendrome when concatenated together, for example "bob" and "dod".
  * 
  * @param wordList      The origional list to create the Map with.
  * @param symetricPairs This is the Map into which the found pairs will be added, it must be initialized, but does not have to be empty.
  */
   public static void findSymetricPairs(List<String> wordList, Map<String, String> symetricPairs ) {
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


/** 
  * groupAnagrams will append to the supplied <code>Map<code> groups of words from
  * the origional <code>List</code> which form a group of angrams, the <code>Map</code> is keyed 
  * with the alphabetical ordering of the letters. eg. "cat", and "tac"
  * would both be placed in the same group and keyed with "act".
  * 
  * @param wordList      The origional list to create the Map and groups with.
  * @param anagramGroups This is the Map into which the found groups will be added, it must be initialized, but does not have to be empty.
  */
   public static void groupAnagrams(List<String> wordList, Map<String, List<String>> anagramGroups) {
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


/** 
  * printResultList is a convinience method to simply print the  
  * supplied <code>List</code> with a given heading.
  * 
  * @param printList The list to be formatted and displayed.
  * @param title     The title to be included in the output.
  */
   public static void printResultList(List<String> printList, String title) {
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

/** 
  * printResultList is a convinience method to simply print the  
  * supplied <code>Map</code> with a given heading.
  * 
  * @param printList The map to be formatted and displayed.
  * @param title     The title to be included in the output.
  */
   public static void printPairResultList(Map<String, String> printList, String title) {
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


/** 
  * lastTenSearch creates a <code>List</code> of the final 10 elements in a <code>TreeSet</code>.
  * 
  * @param wordHashSet The TreeSet to be searched.
  * @return            A List of the final 10 elements.
  */
   public static List<String> lastTenSearch(TreeSet<String> wordHashSet) {
      ArrayList<String> returnList = new ArrayList<String>();
      for(int i = 0; i < 10; i++) {
         returnList.add(wordHashSet.pollLast());
      }
      return returnList;
   } //lastTenSearch

/** 
  * firstTenSearch creates a <code>List</code> of the first 10 elements in a <code>Collection</code>.
  * 
  * @param wordHashSet The Collection to be searched.
  * @return            A List of the first 10 elements.
  */
   public static List<String> firstTenSearch(Collection<String> wordHashSet) {
      Iterator iter = wordHashSet.iterator();
      ArrayList<String> returnList = new ArrayList<String>();
      for(int i = 0; i < 10; i++) {
         returnList.add(iter.next().toString());
      }
      return returnList;
   } //firstTenSearch

/** 
  * randomTenSearch creates a <code>List</code> of 10 random elements from a <code>List</code>.
  * 
  * @param wordHashSet The List to be searched.
  * @param rng         The random number generator to use.
  * @return            A List of 10 random elements.
  */
   public static List<String> randomTenSearch(List<String> wordHashSet, Random rng) {
      ArrayList<String> returnList = new ArrayList<String>();
      for(int i = 0; i < 10; i++) {
         returnList.add(wordHashSet.get(rng.nextInt(wordHashSet.size())));
      }
      return returnList;
   } //randomTenSearch

/** 
  * palendromeSearch will create a new <code>List</code> of words from the
  * origional <code>Collection</code> that are palendromes.
  * 
  * @param wordHashSet  The origional Collection to create the List with.
  * @return             A list of the palendromes from the origional Collection.
  */
   public static List<String> palendromeSearch(Collection<String> wordHashSet) {
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

/** 
  * longestLine will search a <code>Collection</code> of <code>Strings</code> and return the longest
  * <code>String</code> it finds.
  * 
  * @param wordList The origional Collection to search.
  * @return         The longest String found in the Collection.
  */
   public static String longestLine(Collection<String> wordList) {
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


/** 
  * isPalendrome will check if the input <code>String</code> is a palendrome and returns 
  * true if it is and false otherwise.
  * 
  * @param inputString The String to check.
  * @return            True if the input is a palendrome and false otherwise.
  */
   public static boolean isPalendrome( String inputString ) {
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


/** 
  * readWordList takes a <code>File</code> and a <code>HashSet</code> as paramaters and reads each word  
  * from the <code>File</code> and inserts it into the <code>HashSet</code>.
  * <p>
  * Duplicate words are not inserted, only lower case words are inserted.
  * @param wordFile    The file to read the word list from.
  * @param wordHashSet The HashSet to write words into.
  * @return            A count of how many words were entered.
  */
   public static int readWordList(File wordFile, HashSet<String> wordHashSet) {
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

/** 
  * readWordList takes a <code>File</code> and a <code>TreeSet</code> as paramaters and reads each word  
  * from the <code>File</code> and inserts it into the <code>TreeSet</code>.
  * <p>
  * Duplicate words are not inserted, only lower case words are inserted.
  * @param wordFile    The file to read the word list from.
  * @param wordHashSet The TreeSet to write words into.
  * @return            A count of how many words were entered.
  */
   public static int readWordList(File wordFile, TreeSet<String> wordHashSet) {
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

/** 
  * readWordList takes a <code>File</code> and a <code>LinkedHashSet</code> as paramaters and reads each word  
  * from the <code>File</code> and inserts it into the LinkedHashSet.
  * <p>
  * Duplicate words are not inserted, only lower case words are inserted.
  * @param wordFile    The file to read the word list from.
  * @param wordHashSet The LinkedHashSet to write words into.
  * @return            A count of how many words were entered.
  */
   public static int readWordList(File wordFile, LinkedHashSet<String> wordHashSet) {
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

/** 
  * readWordList takes a <code>File</code> and a <code>List</code> as paramaters and reads each word  
  * from the <code>File</code> and inserts it into the List.
  * <p>
  * Duplicate words are not inserted, only lower case words are inserted.
  * @param wordFile    The file to read the word list from.
  * @param wordHashSet The List to write words into.
  * @return            A count of how many words were entered.
  */
   public static int readWordList(File wordFile, List<String> wordHashSet) {
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

/** 
  * readWordList takes a <code>File</code> and a <code>LinkedList</code> as paramaters and reads each word  
  * from the <code>File</code> and inserts it into the <code>LinkedList</code>.
  * <p>
  * Duplicate words are not inserted, only lower case words are inserted.
  * @param wordFile    The file to read the word list from.
  * @param wordHashSet The LinkedList to write words into.
  * @return            A count of how many words were entered.
  */
   public static int readWordList(File wordFile, LinkedList<String> wordHashSet) {
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
