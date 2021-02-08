/*
Addison Hoff
Creates a Word-based Markov Chain.
*/
import java.io.*;
import java.util.*;


public class main {

    public static boolean containsPunctuation(String s) {
        if(s.contains(".")) return true;
        if(s.contains("?")) return true;
        if(s.contains("!")) return true;
        if(s.contains(";")) return true;
        else return false;
    }

    public static void main(String[] args)throws Exception
    {
        Scanner userInput = new Scanner(System.in);
        Random random = new Random();


        //User Input
        System.out.println("Enter a text file");
        String fileName = userInput.nextLine();

        //Read Input txt file
        File inputFile = new File("/Users/addisonhoff/Desktop/ATComp/MarkovChain/Markov/src/" + fileName + ".txt");
        BufferedReader fileReader = new BufferedReader(new FileReader(inputFile));

        String line = "";
        String fileString = "";
        while ((line = fileReader.readLine()) != null)
            fileString += (" " + line);

        Hashtable<String, ArrayList<String>> dictionary = new Hashtable<String, ArrayList<String>>();



        System.out.println("Words to generate?");
        int numGen = userInput.nextInt();



        String[] removePunctuation = fileString.split(".?;!");

        for(int i = 0; i < removePunctuation.length; i++){
            String[] removeSpace = removePunctuation[i].split(" ");

            for(int x = 0; x < removeSpace.length; x++){
                ArrayList<String> holdString = new ArrayList<String>();

                if(!containsPunctuation(removeSpace[x])){

                    if(dictionary.containsKey(removeSpace[x])){
                        holdString = dictionary.get(removeSpace[x]);
                        holdString.add(removeSpace[x+1]);
                        dictionary.replace(removeSpace[x], holdString);
                    }
                    else{
                        holdString.add(removeSpace[x+1]);
                        dictionary.put(removeSpace[x], holdString);
                    }
                }
            }
        }

        String dictValues = new String();
        Enumeration d =  dictionary.keys();
        while (d.hasMoreElements()) {
            dictValues += d.nextElement() + " ";
        }

        String[] total = dictValues.split(" ");

        String randWord = total[random.nextInt(total.length)];
        while(randWord.equals(randWord.toLowerCase())){
            randWord = total[random.nextInt(total.length)];
        }

        String output = randWord;

        for(int i = 0; i < numGen-1; i++){
            if(!containsPunctuation(randWord)) {
                int randNum = random.nextInt(dictionary.get(randWord).size());
                randWord = dictionary.get(randWord).get(randNum);
                output += " " + randWord;
            }
            else{
                randWord = total[random.nextInt(total.length)];
                while(randWord.equals(randWord.toLowerCase())){
                    randWord = total[random.nextInt(total.length)];
                }
                output += " " + randWord;
            }
        }

        System.out.println(output);

        //Write 2 File
        try (Writer fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Markov" + fileName + ".txt"), "utf-8"))) {
            fileWriter.write(output);
        }

    }


}