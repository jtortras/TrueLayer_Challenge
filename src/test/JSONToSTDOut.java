/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author Jordi Tortras
 */
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser; 
import java.io.FileReader;
import org.apache.commons.validator.routines.UrlValidator;

public class JSONToSTDOut {
    
    final static int MAX_LENGTH = 256;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            //Read JSOn file and save it on a JSONArray
            JSONArray jsonnews = (JSONArray)  parser.parse(new FileReader("HackerNews.json"));
            //Will store data into ArrayList since it allows to store complex objects
            ArrayList<HackerNew> javanews = new ArrayList<>();
            //Go accross the JSONArray to obtain data
            for (Object obj:jsonnews) {
                JSONObject post = (JSONObject) obj;
                //Get title. Check not null & max size.
                String title = (String)post.get("title");
                if(title==null)
                    throw new Exception();
                if(title.length()>MAX_LENGTH)
                    title = title.substring(0,MAX_LENGTH-1); 
                //Get URI. Check validity
                String uri = (String)post.get("uri");
                UrlValidator url = new UrlValidator();
                if(!url.isValid(uri))
                    throw new Exception();
                //Get author. Check not null & max size
                String author = (String)post.get("author");
                if(author==null)
                    throw new Exception();
                if(author.length()>MAX_LENGTH)
                    author = author.substring(0,MAX_LENGTH-1);
                //Get points. Get cooments. Get rank
                int points = (int)(long)post.get("points");
                int comments = (int)(long)post.get("comments");
                int rank = (int)(long)post.get("rank");
                //Add values into ArrayList
                javanews.add(new HackerNew (title, uri, author, points, comments, rank));
                //STDOut
                System.out.println("POST "+javanews.size()+"\nTitle: "+title+
                        "\nURI: "+uri+"\nAuthor: "+author+"\nPoints: "+points+
                        "\nComments: "+comments+"\nRank: "+rank+"\n");
                System.out.println("Number of posts: "+javanews.size());
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
}
