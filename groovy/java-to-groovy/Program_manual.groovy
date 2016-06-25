/*
  Automatically Converted from Java Source 
  
  by java2groovy v0.0.1   Copyright Jeremy Rayner 2007
  
  !! NOT FIT FOR ANY PURPOSE !! 
  'java2groovy' cannot be used to convert one working program into another  */

import java.io.*

class Program 
    {static void main(String[] args) throws IOException{
        String path
        if (args.length > 0) {
            path = args[0]
} else {
            path = "."
}


        File directory = new File(path)
        File[] files = directory.listFiles()
        System.out.println(" Directory of " + directory.getCanonicalFile().getAbsolutePath())
        for (int i = 0 ; i < files.length ; i++){
            if (files[i].isFile()) {
                File file = files[i]
                System.out.println(file.getName())
}
}
}
}
