/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package prjmongotest;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.Set;

/**
 *
 * @author sagar
 */
public class PrjMongoTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DBCursor cursor = null;
        try {
            // TODO code application logic here
            MongoClient client = new MongoClient("localhost", 27017);
            DB db = client.getDB("mydb");
            Set<String> list = db.getCollectionNames();
            for (String s : list) {
                System.out.println("Collection Name : " + s);
            }
            DBCollection collection = db.getCollection("testData");
//            for (int x = 0; x < 10; x++) {
//                for (int y = 0; y < 10; y++) {
//                    BasicDBObject doc = new BasicDBObject("db", "mongodb").append("type", "no-sql").append("x", x).append("y", y);
//                    collection.insert(doc);
//                }
//            }

            System.out.println(collection.getCount());
            cursor = collection.find();
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
//            BasicDBObject objToFind = new BasicDBObject("name", "paresh");
//              BasicDBObject objToFind = new BasicDBObject("info", new BasicDBObject("x",new BasicDBObject("$gt", 201)).append("y",new BasicDBObject("$gt", 101)));
//              BasicDBObject objToFind = new BasicDBObject("i", new BasicDBObject("$lte", 200));
            BasicDBObject objToFind = new BasicDBObject("$match", new BasicDBObject("db", "mongodb"));
            DBCursor cur = collection.find(objToFind);
            while (cur.hasNext()) {
                System.out.println("Obj Found " + cur.next());
            }
            // creating index on mongodb
            collection.createIndex(new BasicDBObject("info", 1));
            // aggregation function
            DBObject match = new BasicDBObject("$match",new BasicDBObject("db", "mongodb"));
           DBObject fields = new BasicDBObject("department",1);
            
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
