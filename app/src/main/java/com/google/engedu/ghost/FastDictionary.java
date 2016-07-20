//Done by Abhinand p(abhinand.po@gmail.com) and Adithya Krishnan K(k.adi.krish@gmail.com)
package com.google.engedu.ghost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class FastDictionary implements GhostDictionary
{
    private TrieNode root;
    private Trie t;

    public FastDictionary(InputStream wordListStream) throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        root = new TrieNode((char)0);
        t = new Trie(root);
        String line = null;
        while((line = in.readLine()) != null)
        {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
                t.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word)
    {
        return t.isWord(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix)
    {
        if(prefix==null || prefix=="")
        {
            HashMap<Character,TrieNode> child = root.getChildren();
            Random random = new Random();
            List<Character> keys = new ArrayList<Character>(child.keySet());
            Character ch = keys.get(random.nextInt(keys.size()));
            return t.getAnyWordStartingWith(ch.toString());
        }
        return t.getAnyWordStartingWith(prefix);
    }

    //@Override
    public String getGoodWordStartingWith(String prefix)
    {
        if(prefix==null || prefix=="")
        {
            HashMap<Character,TrieNode> child = root.getChildren();
            Random random = new Random();
            List<Character> keys = new ArrayList<Character>(child.keySet());
            Character ch = keys.get(random.nextInt(keys.size()));
            return t.getGoodWordStartingWith(ch.toString());
        }
        return t.getGoodWordStartingWith(prefix);
    }
}