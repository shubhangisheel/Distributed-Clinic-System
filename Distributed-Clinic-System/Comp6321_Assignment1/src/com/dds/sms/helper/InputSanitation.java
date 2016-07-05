package com.dds.sms.helper;

public class InputSanitation {

	public boolean isString (String str){

		if(str.matches("[a-zA-Z]+")){
			return true;
		}
		else{
			return false;
		}

	}

} 

