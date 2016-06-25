package org.groovy.cookbook;

import java.util.List;


public class StringUtil {

    public String concat(List<String> strings, String separator) {

        if (strings==null) return "";
        if (separator.length()!=1) {
          throw new IllegalArgumentException("The separator must be one char long");
        }
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for(String s: strings) {
            sb.append(sep).append(s);
            sep = separator;
        }
        return sb.toString();


    }

}