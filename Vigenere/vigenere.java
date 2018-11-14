

import java.io.*;
import java.util.Scanner;
public class vigenere
{
	public static PrintWriter pw;
	public static int keyLen;
	public static int copyToArray(String fileName,char a[], int flag) throws IOException
	{
		File file = new File(fileName);
		String str = "";
		int j = 0, size = 0;
		Scanner scanner = new Scanner(file);
		char temp[] = new char[512];
		while(scanner.hasNext())
		{
			str = scanner.nextLine();
			str.replace("\0", "");
			
			for(int i = 0; i < str.length() && size < 512; i++)
				{	
					if(str.charAt(i) == '^' || str.charAt(i) == '_')
						continue;
					if(isLetter(str.charAt(i)))
					{
					temp[j++] = str.charAt(i); 
					size++;
					}	
				}
			
		}
		int tmp = size;
		size = 0;
		j = 0;
		//convert all characters in array to lowercase
		for(int i = 0; i < tmp; i++)
		{
			a[j++] = Character.toLowerCase(temp[i]);
			size++; 
			
		}
		// Don't pad key just return
		if(flag == 1)
		{
			a = temp;
			scanner.close();
			return size;
		}
		
		
		// need to pad 
		if(tmp < 512)
		{	
			tmp = size;
			size = 0;
			j = 0;
			temp = new char[512];
			for(int i = 0; i < 512;i++)
			{	
				if(i >= tmp)
				{	
					temp[j++] = 'x';
					size++;
				}
				if(i < tmp)
				{
					temp[j++] = a[i];
					size++;
				}
			 }
			j = 0;
			for(int i = 0; i < size; i++)
			{
				a[j++] = temp[i];
			}
			if(size < 512)
				a[size] = '\0';
		}
		
		scanner.close();
		return size;
	}
	// check for alphabetic characters only 
	private static boolean isLetter(char c) {
		
		if(c < 65 || c > 122)
			return false;
		
		return true;
	}

	public static void main(String args[]) throws IOException
	{
			
		String keyFile = args[0];
		String plainTextFile = args[1];
		char keyText[] = new char[512];
		char plainText[] = new char[512];
		char cipherText[] = new char[512];
		int keySize, plainSize, j = 0;

		pw = new PrintWriter(System.out);
		
		pw.printf("\n\nVigenere Key:\n\n");
		pw.flush();
		keySize = copyToArray(keyFile, keyText, 1);
		keyLen = keySize;
		//print key
		for(int i = 0; i < keySize;)
		{
			for(j = 0; j < 80 && i < keySize; j++)
			{
				pw.printf("%c", keyText[i++]);
				pw.flush();
			}
			
			System.out.println();
			
		}

		pw.printf("\n\nPlaintext:\n\n");
		pw.flush();
		
		// next capture the plain text
		plainSize = copyToArray(plainTextFile,plainText, 0);
		// print plain text
		for(int i = 0; i < plainSize;)
		{
			for(j = 0; j < 80 && i < plainSize;j++)
			{
				if(plainText[i] == '\0')
					break;
				pw.printf("%c", plainText[i++]);
				pw.flush();
			}
			pw.println();
		}

		pw.printf("\n\nCiphertext:\n\n");
		pw.flush();
		
		cipherText = vigenere(plainText, keyText, plainSize);
		//print ciphertext
		for(int i = 0; i < plainSize;)
		{
			for(j = 0; j < 80 && i < plainSize;j++)
			{
				if(plainText[i] == '\0')
					break;
				pw.printf("%c", cipherText[i++]);
				pw.flush();
			}
			pw.println();
		}
		System.out.println();
	
	}
	
	private static char[] vigenere(char []plain, char[]key, int len)
	{

			int i, j = 0;
			char []temp = new char[512];
			int tmp, tmp2;
			
			for(i = 0; i < len; i++)
			{	
				// restart key from beginning(reusing the key)
				if(j == keyLen)
					j = 0;
			
				tmp = key[j++] - 97;
				tmp2 = plain[i] - 97;
				
				int val = ((tmp + tmp2) % 26);

				temp[i] = (char) (val + 97);
			
			}
			if(i != 512)
				temp[i] = '\0';
			return temp;
	}

}

