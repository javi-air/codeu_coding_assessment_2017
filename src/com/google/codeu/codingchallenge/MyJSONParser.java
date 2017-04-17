// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.codingchallenge;

import java.io.IOException;

final class MyJSONParser implements JSONParser {

    //this is a member variable becase it is used outside of getValue()
    protected int thirdQuote;

    public MyJSONParser(){}
      /**
      * pre: "{" is in input in
      * post: returns the count of c up until the first "{"
      */
    public static int count(String in,String c){
	     int count = 0;
       in = in.substring(1);//ignores first "{"
	      for(int i =0; i < in.length(); i++){
	        if(in.substring(i,i+1).equals(c)){
		        count++;
	        }
          else if((in.substring(i,i+1).equals("{")))//if "{" is encountered{
            break;
          }
        return count;
	}
    /**
    * pre: in has quotes
    * post: returns first key
    */
    public static String getKey(String in){
      int firstQuote = in.indexOf("\"")+1;
      int secondQuote = in.indexOf("\"",firstQuote);
      String key = in.substring(firstQuote,secondQuote);
      return key;
    }
    /**
    * pre: in has quotes
    * post: returns first string value
    */
    public String getValue(String in){
      int firstQuote = in.indexOf("\"")+1;
      int secondQuote = in.indexOf("\"",firstQuote);
      thirdQuote = in.indexOf("\"",secondQuote+1);
      String value = in.substring(thirdQuote + 1, in.indexOf("\"",thirdQuote +1));
      return value;
    }
    /**
    * pre: in is proper JSON-lite format
    * for example: "{\"student\":{\"name\":{\"first\":\"sam\",\"last\":\"doe\",\"size\":{\"gloves\":\"m\"}}}}"
    * post: parses in
    */
  @Override
  public JSON parse(String in) throws IOException {
      final int count = count(in,":");//counts the number of ":" up until the first "{"
      MyJSON j = new MyJSON();
      int i;
      //if the value is a JSON-lite object
      boolean valIsJSON = in.substring(in.indexOf(":")+1,in.indexOf(":")+2).equals("{");
    if(count==0){ return j;}//if in is empty return empty MyJSON
	  if(!valIsJSON){//if value is not a json
      for(i = 0; i < count; i++){
        String key = getKey(in);
	      String value = getValue(in);
	      j.setString(key, value);
	      in = in.substring(in.indexOf(",", thirdQuote + 1)+1);
	    }
      if(in.substring(in.indexOf(":")+1,in.indexOf(":")+2).equals("{")){//if another value is JSON
        MyJSON j2 = (MyJSON)parse(in);
	      String key = getKey(in);
        j.setObject(key,j2);
        return j;
      }
      else{ return j;}
    }
	  else{// if value is a JSON from the start
        String key = getKey(in);
	      in = in.substring(in.indexOf(":")+1);
	      j = (MyJSON)parse(in);
	      j.setObject(key,j);
        return j;
	  }
  }
  //tester code
    public static void main(String args[]){
	MyJSONParser m = new MyJSONParser();
	MyJSON j;
	String in = "{ \"name\":\"sam doe\",\"idk\":\"lol\" }";
	String in2 = "{ \"name\":{\"first\":\"sam\", \"last\":\"doe\" }";//,\"school\",:\"williams\" }";
	String in3 = "{\"student\":{\"name\":{\"first\":\"mi\",\"last\":\"yu\",\"extra\":{\"status\":\"win\"}}}}";
	try{
	    j = (MyJSON)m.parse(in3);
	    System.out.println(j.getObject("student").getObject("name").getObject("extra").getString("status"));

	}catch(IOException e){
	    System.exit(0);
	    e.printStackTrace();
	}
    }
}
//{student:{first:name,last:{}}}
//"{student:{name:{first:mi,last:yu}}}"
