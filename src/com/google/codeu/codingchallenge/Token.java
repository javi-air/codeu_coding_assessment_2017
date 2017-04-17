package com.google.codeu.codingchallenge;

public class Token{

    private boolean jsonType = false;
    private boolean stringType = false;
    private String str;
    private JSON _JSON;

    public Token(JSON value){
        this.jsonType = true;
        this._JSON = value;
    }
    public Token(String value){
        this.stringType = true;
        this.str = value;
    }
    public boolean isJSON(){
        return jsonType;
    }
    public boolean isString(){
      return stringType;
    }
    public JSON getJSON(){
	if(isJSON()){
        return _JSON;
      }
      else{
        throw new java.lang.Error("not a JSON");
      }
    }
    public String getString(){
	if(isString()){
        return str;
      }
      else{
        throw new java.lang.Error("not a String");
      }
    }
    public static void main(String args[]){
      Token token = new Token("Hello");
      System.out.println(token.getString());
    }
}
