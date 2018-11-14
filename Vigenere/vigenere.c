//Brijesh Patel
// CIS 3360

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

int keyLength;
// takes content from file and stores it in an array after performing padding and punctutation cleaning
char *copyToArray(char *fileName, char *a, int flag)
{
	int i = 0, len, j = 0;
	char c, *temp = NULL;
	FILE *fp = fopen(fileName, "r");

	while(fscanf(fp, " %c", &c) != EOF)
	{
		if(!isalpha(c))
			continue;
		// only store lower case  alpabetic characters
		a[i++] = tolower(c);
	}
	// don't pad the key just return
	if(flag == 1)
		{
			fclose(fp);
			return NULL;
		}

	len = strlen(a);
	// pad with 'x's if neccesary 
	if(len < 512)
	{	
		temp = malloc(sizeof(char) * 512);
		for(i = 0; i < 512; i++)
		{
			if(i >= len)
			{
				//if(i % keyLength == 0)
					//break;
				temp[j++] = 'x';
				continue;
			}
			temp[j++] = a[i];
		}
		fclose(fp);
		return temp;
	}
	// trunctate characters that are beyond the 512 limit
	else if(len > 512)
	{
		temp = malloc(sizeof(char) * 512);

		for(i = 0; i < 512; i++)
			temp[i] = a[i];	

		fclose(fp);
		return temp;
	}
	fclose(fp);
	return a;
	
}
// encrypt plaintext array and output cipher array
char *vigenereCipher(char *p, char *key)
{
	int i, j = 0, len;
	char *temp = malloc(sizeof(char)* 512), tmp, tmp2;

	len = strlen(p);

	for(i = 0; i < len; i++)
	{	
		// restart key from beginning(reusing the key)
		if(j == keyLength)
			j = 0;

		tmp = key[j++] - 97;
		tmp2 = p[i] - 97;

		int val = ((tmp + tmp2) % 26);

		temp[i] = (char) (val + 97);
	}
	temp[i] = '\0';
	return temp;
}


int main(int argc, char **argv)
{
	int i = 0, len, j;
	char *keyFile = argv[1];
	char *plainTextFile= argv[2];
	char *key = malloc(sizeof(char) * 512);
	char *p = malloc(sizeof(char) * 512);
	char *cipherText = malloc(sizeof(char) * 512);

	printf("\n\nVigenere Key:\n\n");

	copyToArray(keyFile, key, 1);

	len  = strlen(key);
	keyLength = len;
	for(i = 0; i < len;)
	{
		for(j = 0; j < 80 && i < len; j++)
		{
			printf("%c", key[i++]);
		}
		printf("\n");
	}

	printf("\n\n");
	printf("Plaintext:\n\n");

	p = copyToArray(plainTextFile, p, 0);
	len = strlen(p);

	for(i = 0; i < len;)
	{
		for(j = 0; j < 80 && i < len; j++)
		{
			printf("%c", p[i++]);
		}
		printf("\n");
	}

	printf("\n\n");
	printf("Ciphertext:\n\n");

	cipherText = vigenereCipher(p, key);
	
	len = strlen(cipherText);
	for(i = 0; i < len;)
	{
		for(j = 0; j < 80 && i < len; j++)
		{	
			// prevents left over characters in the array from being printed
			if(cipherText[i] == '\0')
				break;
			printf("%c", cipherText[i++]);
		}
		printf("\n");
	}

	free(cipherText);
	free(p);
	free(key);

}
