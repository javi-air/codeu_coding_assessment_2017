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

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.Vector;

final class MyJSON implements JSON {
    
    //main data structure
    public HashMap<String,Token> map;
    
    public MyJSON(){
	map = new HashMap<String,Token>();
    }
    /**
     * pre: name is string
     * post: gets object value associated with name
     */
  @Override
  public JSON getObject(String name) {
      Asserts.isEqual(map.containsKey(name),true);
      if(map.get(name).isJSON()){
          return map.get(name).getJSON();
      }
      else{
          return null;
      }
  }
    /**
     * pre: value is JSON
     * post: if name in map then replace current name's value with value
     * else create new key:value pair.
     */
  @Override
  public JSON setObject(String name, JSON value) {
      Token valueToken = new Token(((MyJSON)value));
      if(!map.containsKey(name)){
	      map.put(name, valueToken);
      }
      else{
	  map.replace(name,valueToken);
      }
      return this;
  }
    /**
     * pre: name is string
     * post: gets string value associated with name
     */
  @Override
  public String getString(String name) {
      Asserts.isEqual(map.containsKey(name),true);
      if(map.get(name).isString()){
	  return map.get(name).getString();
      }
      else{
	  return null;
      }
  }
    /**
     * pre: value is string
     * post: if name in map then replace current name's value with value
     * else create new key:value pair.
     */
  @Override
  public MyJSON setString(String name, String value) {
      Token valueToken = new Token(value);
      if(!map.containsKey(name)){
	  map.put(name,valueToken);
      }
      else{
	  map.replace(name, valueToken);
      }
    return this;
  }
    /**
     * pre: key in map
     * post: recursively gets the string values inside object values and appends them to name
     */
    static private void getObjectsHelper(HashMap<String,Token> map, String key, Collection<String> names){
	Token values = map.get(key);
	if(values.isString()){
	    names.add(values.getString());
	}
	else{
	    HashMap<String, Token> vMap = ((MyJSON)values.getJSON()).map;
	    for(final Map.Entry<String, Token> entry: vMap.entrySet()){
		getObjectsHelper(vMap,entry.getKey(),names);
	    }
	}
    }

  @Override
  public void getObjects(Collection<String> names) {
      for(final Map.Entry<String, Token> entry : map.entrySet()){
	  if(entry.getValue().isJSON()){
	   getObjectsHelper(map, entry.getKey(), names);   
	  }
      }
  }
    /**
     * post: copies original string values to names
     */
  @Override
  public void getStrings(Collection<String> names) {
      for(final Map.Entry<String, Token> entry : map.entrySet()){
	  if(entry.getValue().isString()){
	      names.add(entry.getValue().getString());
	  }
      }
  }
    //tester code
    public static void main(String args[]){
	MyJSON j = new MyJSON();
	j.setString("sam","doe");
	MyJSON k = new MyJSON();
	k.setObject("name",j);
	Vector<String> v = new Vector();
	k.getObjects(v);
	System.out.println(v);
	MyJSONParser p = new MyJSONParser();
    }
}
