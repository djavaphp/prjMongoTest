/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package prjmongotest;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sagar
 */
public class AggregationDemo {

    public static void main(String[] args) {
        try {
            MongoClient client = new MongoClient("localhost", 27017);
            DB db = client.getDB("mydb");
            DBCollection collection = db.createCollection("expenses", null);
//            for (int empid = 1; empid <= 2; empid++) {
//                DBObject obj = new BasicDBObject("dept", "sales").append("empid",empid).append("type", "airfare").append("expense",empid*10);
//                collection.insert(obj);
//            }
//            for (int empid = 3; empid <= 4; empid++) {
//                DBObject obj = new BasicDBObject("dept", "hr").append("empid",empid).append("type", "airfare").append("expense",empid*10);
//                collection.insert(obj);
//            }
//            for (int empid = 4; empid <= 5; empid++) {
//                DBObject obj = new BasicDBObject("dept", "mktg").append("empid",empid).append("type", "airfare").append("expense",empid*10);
//                collection.insert(obj);
//            }            
            System.out.println(collection.count());
            DBCursor cursor = collection.find();
            while(cursor.hasNext()){
                System.out.println(cursor.next());
            }
            
            // aggregation function...
            DBObject match = new BasicDBObject("$match",new BasicDBObject("type", "airfare"));
            
            DBObject fields = new BasicDBObject("dept", 1);
            fields.put("expense", 1);
            fields.put("_id", 1);
            DBObject project = new BasicDBObject("$project",fields);            
            
            DBObject groupFields = new BasicDBObject("_id","$dept");
            groupFields.put("average", new BasicDBObject("$avg","$expense"));
            
            DBObject group = new BasicDBObject("$group",groupFields);
            
            AggregationOutput output = collection.aggregate(match, project,group);
            
            System.out.println(output);
            
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }



    }
}
