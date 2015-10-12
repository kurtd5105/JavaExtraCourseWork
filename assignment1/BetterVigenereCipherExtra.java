/**
 * @author Kurt Dorflinger
 * @description This is an alternate implementation of Vigenere.java, instead of using int arrays
 *              it uses char arrays, reducing the amount of methods needed and also lines of code needed.
 */

public class BetterVigenereCipherExtra implements Cipher{
	private char[] key;

	public BetterVigenereCipherExtra(String key){
		this.key = key.toCharArray();
	}

	/**
	 * A function used for debugging that prints out the contents of an int array all on 
	 * one line, separated by commas.
	 * @param array An int array that is to be printed.
	 * @param text String that is the name of the array.
	*/
	private void dumpArray(char[] array, String text){
		System.out.print(text + ' ');
		for(int i = 0; i < array.length - 1; i++){
			System.out.print(array[i] + ", ");
		}
		System.out.println(array[array.length - 1]);
	}

	/**
	 * Sums the ith elements of 2 arrays together provided they are the same length or the short array is
	 * actually larger. Otherwise it sums the ith element of the long array with the jth element of the
	 * short array, looping j around to 0 to stay in bounds.
	 * @param longArray char array of the longer array, whose length will determine the length of the 
	 *        sum array.
	 * @param shortArray char array of the "shorter" array.
	 * @output sum char array that holds the sum of the ith element of the two arrays. 
	*/
	private char[] sumUnevenArrays(char[] longArray, char[] shortArray){
		//Create the sum array with the length of the long array
		char[] sum = new char[longArray.length];
		int j = 0;
		int tempSum = 0;

		//Sum longArray to the shortArray, making sure each element in longArray gets summed to a shortArray element
		for(int i = 0; i < longArray.length; i++){
			//Loop j back to 0 to stop it from going out of bounds
			if(j > shortArray.length - 1){
				j = 0;
			}

			//Cast each char to an int and store it temporarily. Use short array as an offset from the first letter
			tempSum = (int)longArray[i] + ((int)shortArray[j++] - 32);

			//Cast the sum back to a char, and if needs to loop the offset around
			if(tempSum > 126){
				sum[i] = (char)(tempSum - 95);
			} else {
				sum[i] = (char)tempSum;
			}
		}

		return sum;
	}

	/**
	 * Subtracts the ith elements of 2 arrays together provided they are the same length or the short array is
	 * actually larger. Otherwise it subtracts the ith element of the long array with the jth element of the
	 * short array, looping j around to 0 to stay in bounds.
	 * @param longArray char array of the longer array, whose length will determine the length of the 
	 *        diff array.
	 * @param shortArray char array of the "shorter" array.
	 * @output diff char array that holds the difference of the ith element of the two arrays. 
	*/
	private char[] diffUnevenArrays(char[] longArray, char[] shortArray){
		//Create the difference array with the length of the long array
		char[] diff = new char[longArray.length];
		int j = 0;
		int tempDiff = 0;
		
		//Subtract longArray to the shortArray, making sure each element in longArray gets a number subtracted from a shortArray element
		for(int i = 0; i < longArray.length; i++){
			//Loop j back to 0 to stop it from going out of bounds
			if(j > shortArray.length - 1){
				j = 0;
			}

			//Cast each char to an int and store it temporarily. Use short array as an offset from the first letter
			tempDiff = (int)longArray[i] - ((int)shortArray[j++] - 32);

			//Cast the difference back to a char, and if needs to loop the offset around
			if(tempDiff < 32){
				diff[i] = (char)(tempDiff + 95);
			} else {
				diff[i] = (char)tempDiff;
			}
		}

		return diff;
	}

	/**
	 * Encrypts the given text using the stored key.
	 * @param plaintext String to be encrypted.
	 * @output anonymous String that is the encrypted text.
	*/
	public String encrypt(String plaintext){
		//Takes plaintext and converts it to a char array. Using arithUnevenArrays it adds the key to the plaintext.
		//Then it copies the encrypted char array into a new string object.
		return new String().copyValueOf( sumUnevenArrays(plaintext.toCharArray(), key) );
	}

	/**
	 * Decrypts the given text using the stored key.
	 * @param ciphertext String to be decrypted.
	 * @output anonymous String that is the decrypted text.
	*/
	public String decrypt(String ciphertext){
		//Takes ciphertext and converts it to a char array. Using arithUnevenArrays it subtracts the key from the ciphertext.
		//Then it copies the decoded char array into a new string object.
		return new String().copyValueOf( diffUnevenArrays(ciphertext.toCharArray(), key) );
	}

	public void setKey(String key){
		this.key = key.toCharArray();
	}
}