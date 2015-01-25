package com.noblebanking.util;

import java.security.*;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.*;
import javax.crypto.spec.*;

import java.util.*;
import java.io.*;

import org.springframework.jdbc.core.JdbcTemplate;

public class PKI
{
  private PrivateKey privateKey;
  private PublicKey publicKey;
  private JdbcTemplate jdbcTemplate;

  public String[] generateKeys()
  {
	  String key_pairs[] = new String[2];

	   try
	   {
		   KeyPairGenerator keyGen = KeyPairGenerator.getInstance( "DSA" );
		   keyGen.initialize( 1024 );
		   KeyPair pair = keyGen.generateKeyPair();
		   this.privateKey = pair.getPrivate();
		   this.publicKey = pair.getPublic();

		   String private_key = getString(this.privateKey.getEncoded());
		   String public_key = getString(this.publicKey.getEncoded());
		   
		   key_pairs[0] = private_key;
		   key_pairs[1] = public_key;
		   
		   return key_pairs;
	   }
	   catch( Exception e )
	   {
	     e.printStackTrace();
	   }
   return null;
  }
  
  public PublicKey getPublic(String encoded_string)
  {
  	byte bytes[] = getBytes(encoded_string);
  	
  		try
  		{
		KeyFactory key_factory = KeyFactory.getInstance("DSA");
		KeySpec key_spec =new  X509EncodedKeySpec(bytes);
		PublicKey public_key = key_factory.generatePublic(key_spec);
		return public_key;
  		}
  		catch(Exception e)
  		{
  			return null;
  		}
  }

  public PrivateKey getPrivate(String encoded_string)
  {
  	byte bytes[] = getBytes(encoded_string);
  	assert(bytes != null);
    
  		try
  		{
  			KeyFactory key_factory = KeyFactory.getInstance("DSA");
  			KeySpec key_spec =new  PKCS8EncodedKeySpec(bytes);
  			PrivateKey private_key = key_factory.generatePrivate(key_spec);
  			
		return private_key;
  		}
  		catch(Exception e)
  		{
  			return null;
  		}
  }
  
  public String sign( String plaintext, PrivateKey privateKey )
  {
   try
   {
	  
     Signature dsa = Signature.getInstance( "SHA1withDSA" );
     dsa.initSign(privateKey);
     dsa.update( plaintext.getBytes() );
     byte[] signature = dsa.sign();
     return getString( signature );
   }
   catch( Exception e )
   {
     e.printStackTrace();
   }
   return null;
  }

  public boolean verifySignature( String plaintext, String signature, PublicKey publicKey )
  {
   try
   {
     Signature dsa = Signature.getInstance( "SHA1withDSA" );
     dsa.initVerify( publicKey );
     System.out.println("got the plain text as ......"+plaintext);
     System.out.println("got signature as............."+signature);
     System.out.println("got the pubkey as............."+publicKey);
     dsa.update( plaintext.getBytes() );
     boolean verifies = dsa.verify( getBytes( signature ) );
     System.out.println("signature verifies: " + verifies);
     return verifies;
   }
   catch( Exception e )
   {
     e.printStackTrace();
   }
   return false;
  }
  
  private String getString( byte[] bytes )
  {
   String result = "";

    for(int i=0;i<bytes.length-1;i++)
    	result += bytes[i] +",";
    	result += bytes[bytes.length-1];

   return result;
  }

  public byte[] getBytes( String str )
  {
    String sections[] = str.split(",");
    byte bytes[] = new byte[sections.length];

    for(int i=0;i<sections.length;i++)
    bytes[i] = Byte.parseByte(sections[i]);
    
	  return bytes;
  }
  
}

