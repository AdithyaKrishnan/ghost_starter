// Done By Abhinand (abhinand.po@gmail.com)and Adithya (k.adi.krish@gmail.com)
package com.google.engedu.ghost;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;
    int low=0, high=0 , mid=0;
    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)

                words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix)
    {

        if(prefix==null || prefix=="")
        {

            return words.get(new Random().nextInt(words.size()));
        }
        low=0;
        high=words.size()-1;
        while(low<=high)
        {
            mid = (low + high)/2;
            String stringmid=words.get(mid);
            if(stringmid.length()>=prefix.length())
            {

                if(stringmid.substring(0,prefix.length()).compareTo(prefix)==0)
                    return stringmid;
            }
            int val=prefix.compareTo(stringmid);

            if(val<0)
            {
                high=mid-1;

            }
            else
            {
                low=mid+1;
            }


        }


        return null;
    }

	/*      @Override
    public String getAnyWordStartingWith(String prefix, int l , int h)
    {
		low = l;
		high =h;
        if(prefix==null || prefix=="")
        {

            return words.get(new Random().nextInt(words.size()));
        }

        while(low<=high)
        {
            mid = (low + high)/2;
            String stringmid=words.get(mid);
            if(stringmid.length()>prefix.length())
            {

                if(stringmid.substring(0,prefix.length()).compareTo(prefix)==0)
                    return stringmid;
            }
            int val=prefix.compareTo(stringmid);

            if(val<0)
            {
                return getAnyWordStartingWith(prefix,low ,mid+1);

            }
            else
            {
                return getAnyWordStartingWith(prefix,mid+1 ,high);
            }


        }


        return null;
    }*/

    @Override
    public String getGoodWordStartingWith(String prefix) {
        return null;
    }
}
