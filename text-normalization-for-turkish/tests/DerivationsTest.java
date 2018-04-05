/**
 * Tests for DerivedWord class
 *
 * @author Mert Alp Taytak
 * @version prototype-1
 */

 package tests;

 // IMPORTS
 import normalizer.Word;
 import normalizer.DerivedWord;
 import java.util.ArrayList;
 import java.util.Scanner;
 import java.lang.Math;

 public class DerivationsTest
 {
    public static void main(String[] args)
    {
        // Scanner
        Scanner in = new Scanner(System.in);

        // Create a Word object to hold a root word
        String root;

        // List to hold derivations
        ArrayList<DerivedWord> derivations;

        // User inputs
        boolean userStop;
        boolean userAnalysis;
        boolean userSample;
        int userSampleAmount = 0; // Java cries if it is not initialized

        // Variable to hold total word count in detailed analysis
        int totalWordCount;

        // Variable to hold system time
        long startTime;
        long time;

        // Main loop
        do
        {
            // Get root word
            System.out.print("Please enter a word: ");
            root = in.next();

            // Ask what user wants
            System.out.println("Do you wish to do a detailed analysis?");
            System.out.print("yes/no: ");
            userAnalysis = in.next().equals("yes");

            System.out.println("Do you wish to see some sample derivations?");
            System.out.print("yes/no: ");
            userSample = in.next().equals("yes");

            if (userSample)
            {
                System.out.print("Enter sample size: ");
                userSampleAmount = in.nextInt();
            }


            System.out.println("Starting derivations...");

            if (userAnalysis)
            {
                totalWordCount = 0;

                System.out.println("Root word: " + root);
                System.out.println("Length: " + root.length() + " characters");

                // Deletions
                startTime = System.currentTimeMillis();
                time = startTime;
                derivations = DerivedWord.getDeletions(root);
                System.out.println("Deletions derived.");
                System.out.println("Execution time: " + (System.currentTimeMillis() - time) + " milliseconds");
                System.out.println("Number of words derived: " + derivations.size());
                totalWordCount += derivations.size();

                if (userSample)
                {
                    printSamples(derivations, userSampleAmount);
                }

                // Insertions
                time = System.currentTimeMillis();
                derivations = DerivedWord.getInsertions(root);
                System.out.println("Insertions derived.");
                System.out.println("Execution time: " + (System.currentTimeMillis() - time) + " milliseconds");
                System.out.println("Number of words derived: " + derivations.size());
                totalWordCount += derivations.size();

                if (userSample)
                {
                    printSamples(derivations, userSampleAmount);
                }

                // Replacements
                time = System.currentTimeMillis();
                derivations = DerivedWord.getReplacements(root);
                System.out.println("Replacements derived.");
                System.out.println("Execution time: " + (System.currentTimeMillis() - time) + " milliseconds");
                System.out.println("Number of words derived: " + derivations.size());
                totalWordCount += derivations.size();

                if (userSample)
                {
                    printSamples(derivations, userSampleAmount);
                }

                // Swaps
                time = System.currentTimeMillis();
                derivations = DerivedWord.getSwaps(root);
                System.out.println("Swaps derived.");
                System.out.println("Execution time: " + (System.currentTimeMillis() - time) + " milliseconds");
                System.out.println("Number of words derived: " + derivations.size());
                totalWordCount += derivations.size();

                if (userSample)
                {
                    printSamples(derivations, userSampleAmount);
                }

                // Print total analysis
                System.out.println("All derivations done.");
                System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + " milliseconds");
                System.out.println("Number of word derived: " + totalWordCount);
            }
            else
            {
                time = System.currentTimeMillis();
                derivations = DerivedWord.getDerivations(root);
                System.out.println("All derivations done.");
                System.out.println("Execution time: " + (System.currentTimeMillis() - time) + " milliseconds");
                System.out.println("Number of words derived: " + derivations.size());

                if (userSample)
                {
                    printSamples(derivations, userSampleAmount);
                }
            }

            // Ask if the user wants to stop
            System.out.println("Enter 'stop' to end the program. Anything else would repeat the program.");
            System.out.print("Input: ");
            userStop = in.next().equals("stop");

        } while (!userStop);
    }

    public static void printSamples(ArrayList<DerivedWord> list, int sampleSize)
    {
        for (int i = 0; i < Math.min(list.size(), sampleSize); i++)
        {
            System.out.println("Sample " + (i+1) + ", word: " + list.get(i));
        }
    }
 }
