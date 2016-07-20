//Done by Abhinand p(abhinand.po@gmail.com) and Adithya Krishnan K(k.adi.krish@gmail.com)
package com.google.engedu.ghost;

import java.util.*;

class TrieNode
{
    private char value;
    private HashMap<Character,TrieNode> children;
    private boolean isWord;
    public TrieNode(char ch)
    {
        value = ch;
        children = new HashMap<>();
        isWord = false;
    }
    public HashMap<Character,TrieNode> getChildren()
    {
        return children;
    }
    public char getValue()
    {
        return value;
    }
    public void setIsWord(boolean val)
    {
        isWord = val;
    }
    public boolean isWord()
    {
        return isWord;
    }
}

class Trie
{
    private TrieNode root;
    private String result;

    // Constructor
    public Trie(TrieNode root )
    {
        this.root = root;
    }

    // Method to insert a new word to Trie
    public void add(String word)
    {
        int length = word.length();
        TrieNode crawl = root;

        // Traverse through all characters of given word
        for( int level = 0; level < length; level++)
        {
            HashMap<Character,TrieNode> child = crawl.getChildren();
            char ch = word.charAt(level);

            // If there is already a child for current character of given word
            if( child.containsKey(ch))
                crawl = child.get(ch);
            else   // Else create a child
            {
                TrieNode temp = new TrieNode(ch);
                child.put( ch, temp );
                crawl = temp;
            }
        }


        crawl.setIsWord(true);
    }

    public String getAnyWordStartingWith(String input)
    {
        result = ""; // Initialize resultant string
        int length = input.length();  // Find length of the input string

        // Initialize reference to traverse through Trie
        TrieNode crawl = root;
        int level=0;
        boolean condition1 = false;
        boolean condition2 = false;
        while(!(condition1 || condition2 ))
        {
            char ch = input.charAt(level);
            HashMap<Character,TrieNode> child = crawl.getChildren();
            if( child.containsKey(ch) )
            {
                result += ch;          //Update result
                crawl = child.get(ch); //Update crawl to move down in Trie
                level++;
                if( level  == length  )
                {
                    result = continueTillWord(crawl);
                    condition1 = true;
                }
            }
            else
            {
                condition2=true;
            }
        }
        if(condition2)
            return null;

        return result;
    }

    private  String continueTillWord(TrieNode crawl)
    {
        if(crawl.isWord())
            result=null;
        while(!crawl.isWord())
        {
            HashMap<Character,TrieNode> child = crawl.getChildren();
            Random random = new Random();
            List<Character> keys = new ArrayList<Character>(child.keySet());
            Character ch = keys.get(random.nextInt(keys.size()));

            result += ch;

            crawl = child.get(ch);
        }
        return result;
    }

    public boolean isWord(String input)
    {
        int length = input.length();  // Find length of the input string

        // Initialize reference to traverse through Trie
        TrieNode crawl = root;

        int level=0;

        while(level< length)
        {
            char ch = input.charAt(level);
            HashMap<Character,TrieNode> child = crawl.getChildren();

            if( child.containsKey(ch))
            {
                crawl = child.get(ch);
                level++;
            }
            else break;
        }

        return crawl.isWord();
    }

    public String getGoodWordStartingWith(String input)
    {
        result = ""; // Initialize resultant string
        int length = input.length();  // Find length of the input string

        // Initialize reference to traverse through Trie
        TrieNode crawl = root;

        int level=0;
        boolean condition1 = false;
        boolean condition2 = false;
        while(!(condition1 || condition2 ))
        {
            // Find current character of str
            char ch = input.charAt(level);

            // HashMap of current Trie node to traverse down
            HashMap<Character,TrieNode> child = crawl.getChildren();

            if( child.containsKey(ch) )
            {
                result += ch;          //Update result
                crawl = child.get(ch); //Update crawl to move down in Trie
                level++;

                if( level  == length  )
                {
                    result = continueTillWordImproved(crawl);
                    condition1 = true;
                }

            }
            else
            {
                condition2=true;
            }
        }

        if(condition2)
            return null;

        return result;
    }

    private  String continueTillWordImproved(TrieNode crawl)
    {

        if(crawl.isWord())
            result=null;

        while(!crawl.isWord())
        {
            HashMap<Character,TrieNode> child = crawl.getChildren();
            Random random = new Random();
            Character ch;
            List<Character> keys = new ArrayList<Character>(child.keySet());
            List<Character> valid = new ArrayList<Character>();
            for(Character c: keys)
            {
                if(!child.get(c).isWord())
                {
                    valid.add(c);
                }
            }
            if(valid.size() != 0)
            {
                ch = valid.get(random.nextInt(valid.size()));
            }
            else
                ch = keys.get(random.nextInt(keys.size()));

            result += ch;

            crawl = child.get(ch);
        }
        return result;
    }

}

