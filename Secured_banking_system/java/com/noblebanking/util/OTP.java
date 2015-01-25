package com.noblebanking.util;

import java.util.Random;

	public class OTP
	{

	    /**
	    * Noble's proprietary OTP generator matrix. 
	    * Without this matrix, the OTP cannot be regenerated.
	    */
	    private final char[] OTP_MATRIX  = {
	            '4', 'J', 'Q', 'T', 'E', '4', 'n', 'L', '8', '1', 'K', '7', 'x', '1', 'g', 'S', '3', 'P', '7', 'o', 't', 'l', 'k', 'i', 'a', 
	            'T', 'V', 'r', 'b', 'j', '5', 'a', '1', 'S', '7', 'f', 'L', '7', '5', '6', 'E', '1', 'r', '3', 'c', 'i', 'g', '0', '7', 'X',
	            '1', 'n', 'M', 'q', 'a', 'X', '5', 'e', 'v', '3', 'A', '7', 'r', '4', 'p', '3', 'a', '3', '7', '5', '0', 'B', '6', '6', 'x',
	            'G', '5', '8', 'v', '2', 'D', '3', 'Y', 'U', 'r', 'H', 'X', '5', '4', 'u', '0', '0', 'L', 'x', 'R', 'Y', 'T', 'K', '8', 'K'
	                                      };

	    private Random generator;

	    private final int OTP_MAX_LENGTH = 6;

	    /**
	    * Set the seed at the instant the object is created.
	    */
	    public OTP()
	    {
	        this.generator = new Random(System.currentTimeMillis());
	    }

	    /**
	    * Generate a secure OTP. 
	    * Lets say the attacker has our matrix also. Then how to we provide security?
	    * The answer is in Operating System level unpredictability over here.
	    * We sleep for a very small time within this thread. However due to OS internals 
	    * we can never be sure when the processor will wake the thread up, leading to thousands of possibilities.
	    * Thus if we seed our generator using this seed even we will never be able to generate the same OTP except if
	    * we are really lucky, providing total privacy to the user.
	    */
	    public String generate()
	    {
	    String otp = "";
	    long start;
	    Random secure_generator;
	    int base;
	    int salt;
	    
	        for(int i=0;i<this.OTP_MAX_LENGTH;i++)
	        {
	            start = System.currentTimeMillis();

	                try
	                {Thread.sleep(1);} catch(Exception e){};
	                
	            base = generator.nextInt();
	            start = System.currentTimeMillis();
	            secure_generator = new Random(start);

	            salt = secure_generator.nextInt();
	            base = base + salt;

	            base = base < 0 ? -base : base;
	            otp += OTP_MATRIX[(int)(base)%this.OTP_MATRIX.length];
	        }

	    return otp;
	    }

//	    public static void main(String args[])
//	    {
//	        OTP test = new OTP();
//	        for(int i=0;i<10;i++)
//	        System.out.println(test.generate());
//	    }
	}



