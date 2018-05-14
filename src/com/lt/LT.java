package com.lt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.lt.utils.MongoDb;
import com.mongodb.client.result.UpdateResult;
 

public class LT {

	public static void main(String[] args) throws Exception {
		
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("111", "aaa");
		map.put("222", "bbb");
		
		for(Entry<String,String> entry:map.entrySet()){
			System.out.println(entry.getKey()+":"+entry.getValue());
		}
		
		
		before();
		//testInsert();
		
		testFindBy();
		//testDeleteMany();
		//testUpdateOne();
		//testFindAll();
	}
	
	
	public static void before(){  
        MongoDb.connect("ltDb", "ltColl", "192.168.1.144", 27017);  
    }  
      
	  
    public static void testInsert(){  
        Document document = new Document();  
        document.append("name", "wang").append("sex", "female").append("age", 33);  
        MongoDb.insert(document);  
    }  
      
    public static void testFindAll(){  
        List<Document> results = MongoDb.findAll();  
        for(Document doc : results){  
            System.out.println(doc.toJson());  
        }  
    }  
      
    public static void testFindBy(){  
    	
   
    	
    	 
    	
        Document filter = new Document();  
        //filter.append("$or", Arrays.asList(new Document("name", "liu"),new Document("age", new Document("$gt",18)))); 
        //filter.append("name",new Document("$in",Arrays.asList("liu","wang")) ); 
        //filter.append("age", new Document("$gt",12));  
      //完全匹配
        //Pattern pattern = Pattern.compile("^name$", Pattern.CASE_INSENSITIVE);
        //右匹配
        //Pattern pattern = Pattern.compile("^.*name$", Pattern.CASE_INSENSITIVE);
        //左匹配
        //Pattern pattern = Pattern.compile("^name.*$", Pattern.CASE_INSENSITIVE);
        //模糊匹配
        Pattern pattern = Pattern.compile("^.*an.*$", Pattern.CASE_INSENSITIVE);
        filter.append("name", new Document("$regex", pattern)); 
        //filter.append("_id", new ObjectId("5af2b62d7eb4d02aa4c43f6b"));  
        //filter.append("age", new Document("$lt",44)) ;
        List<Document> results = MongoDb.findBy(filter,1,10,"name");  
        for(Document doc : results){  
            System.out.println(doc.toJson());  
        }  
    }  
    public static void testUpdateOne(){  
        Document filter = new Document();  
        filter.append("_id", new ObjectId("5af399d2da6f1615a0b6f68e"));  
          
        //注意update文档里要包含"$set"字段  
        Document update = new Document();  
        update.append("$set", new Document("sex", "male").append("age", 18));   
        UpdateResult result = MongoDb.updateOne(filter, update);  
        System.out.println("matched count = " + result.getMatchedCount());  
    }  
      
    public void testUpdateMany(){  
        Document filter = new Document();  
        filter.append("gender", "female");  
          
        //注意update文档里要包含"$set"字段  
        Document update = new Document();  
        update.append("$set", new Document("gender", "male"));  
        UpdateResult result = MongoDb.updateMany(filter, update);  
        System.out.println("matched count = " + result.getMatchedCount());  
    }  
      
    public void testReplace(){  
        Document filter = new Document();  
        filter.append("name", "zhang");  
          
        //注意：更新文档时，不需要使用"$set"  
        Document replacement = new Document();  
        replacement.append("value", 123);  
        MongoDb.replace(filter, replacement);  
    }  
      
    public void testDeleteOne(){  
        Document filter = new Document();  
        filter.append("name", "li");  
        MongoDb.deleteOne(filter);  
    }  
      
    public static void testDeleteMany(){  
        Document filter = new Document();  
        filter.append("gender", "female");  
        MongoDb.deleteMany(filter);  
    }  
    
    
}
