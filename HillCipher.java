
public class HillCipher{
	static String PLAIN_TEXT = "Today is your practical";  
	static String CIPHER_KEY = "IBEX";			

	// Function converts string into matrix
	public static int[][] textToMatrix(String text, int row, int col){

		int matrix[][] = new int[row][col];
		int index=-1;
		for(int i=0; i<row; i++)
			for(int j=0; j<col; j++)
			matrix[i][j] = text.charAt(++index) - 65 ;
		
		return matrix;
	}

	// This function multiplies returns the product of 2 matrices
	public static int [][] multiplyMatrix(int firstMatrix[][], int secondMatrix[][]){

		int row1 = firstMatrix.length;	int col1 = firstMatrix[0].length;
		int row2 = secondMatrix.length;	int col2 = secondMatrix[0].length;

		int product[][] = new int[row1][col2];

		for(int i = 0; i < row1; i++)
            for (int j = 0; j < col2; j++)
                for (int k = 0; k < col1; k++)
                    product[i][j] = product[i][j] + firstMatrix[i][k] * secondMatrix[k][j];

        return product;
	}

	// Converts numbers into corresponding character acc. to ASCII value.
	public static String convertCharacter(int [][]matrix){

		String result="";
		int row = matrix.length;
		int col = matrix[0].length;

		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				char c = (char) ((matrix[i][j]%26+ 65));
				result +=c;
			}
		}
		return result;
	}

	// Displays the content of matrices
	public static void displayResult(int matrix[][], String type){

		int row = matrix.length;
		int col = matrix[0].length;
		 System.out.println("\n"+type+"\n-------");
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++)
				System.out.print(String.format("%02d", matrix[i][j]%26)+"  ");	
			System.out.println();
		}
	}

	// Core function perform encryption
	public static int[][] encrypt(int textMatrix[][], int keyMatrix[][]){

		System.out.println("Given text : "+ PLAIN_TEXT);
		System.out.println("Choosen key : "+CIPHER_KEY);
		int product[][] = multiplyMatrix(textMatrix, keyMatrix);

		displayResult(textMatrix, "Plain Text");
		displayResult(keyMatrix, "Key");
		displayResult(product, "Product");
		System.out.println("Equivalent text : "+convertCharacter(product));
		return product;
	}

	// Core function perform decryption
	public static int[][] decrypt(int textMatrix[][], int keyMatrix[][]){

		int determinant = keyMatrix[0][0]*keyMatrix[1][1] - keyMatrix[0][1]*keyMatrix[1][0];
		int adjoint[][] = new int[2][2];
		adjoint[0][0]=keyMatrix[1][1];
		adjoint[0][1]=-keyMatrix[0][1];
		adjoint[1][0]=-keyMatrix[1][0];
		adjoint[1][1]= keyMatrix[0][0];

		displayResult(textMatrix, "Cipher Text");
		System.out.println("\nInverse of key matrix\n-------------");
			for(int i[]:adjoint){
				for(int j : i){
					System.out.print(j+"/"+determinant+"  ");	
				}
				System.out.println();
			}
			System.out.println("\n\nMultiplying cipher text with inverse of key...");
		int product[][]= multiplyMatrix(textMatrix , adjoint);
		 for(int i=0; i<product.length; i++){
		 	for(int j=0; j<product[0].length; j++){
		 		product[i][j] /= determinant;
		 	}
		 }

		 displayResult(product, "Decrypted/Plain Text");
		 System.out.println("Equivalent text : "+convertCharacter(product));
		return product;
	}

	
	public static void main(String arg[]){

		String cipherText="";
		String decryptedText="";
		
		PLAIN_TEXT = PLAIN_TEXT.toUpperCase();			
		PLAIN_TEXT = PLAIN_TEXT.replaceAll("\\s", "");	
		if(PLAIN_TEXT.length()%2 != 0)			
			PLAIN_TEXT += 'Z';

		int n = PLAIN_TEXT.length();
		int keyMatrix[][] = textToMatrix(CIPHER_KEY,2,2);	
		int textMatrix[][] = textToMatrix(PLAIN_TEXT,(n+1)/2, 2);
	
		System.out.println("INITIALIZING ENCTYPTION...");
		int cipherTextMatrix[][] = encrypt(textMatrix, keyMatrix);
		cipherText = convertCharacter(cipherTextMatrix);
		


		System.out.println("\nINITIALIZING DECTYPTION...");
		int decryptedTextMatrix[][] = decrypt(cipherTextMatrix, keyMatrix);
		decryptedText = convertCharacter(decryptedTextMatrix);



		System.out.println("\n=====================================");
		System.out.println("PLAIN TEXT : "+PLAIN_TEXT);
		System.out.println("CIPHER TEXT : "+cipherText);
		System.out.println("DECRYPTED TEXT : "+ decryptedText);				
		System.out.println("=====================================");
		
	}
}