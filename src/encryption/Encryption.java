/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package encryption;

import java.math.BigInteger;
import java.util.Arrays;

/**
 *
 * @author Abulele
 */
public class Encryption {
    public static char[] map = new char[26];
    
    public record PrimeNumbers(int p, int q){};
    public record Modulus(int n){};
    public record EulersPhi(int phi){};
    public record PublicKey(int e, int modulus){};
    public record PrivateKey(int d){};
    
    public static PrimeNumbers primeNumbers;
    public static Modulus modulus;
    public static EulersPhi phi;
    public static PublicKey publicKey;
    public static PrivateKey privateKey;
    
    //testing
    /*public static void main(String[] args) {
        // TODO code application logic here
        setKeys();
        int[] original = {1,2,3,4,5};
        int[] cipher = encryptMessage(original);
        decryptMessage(cipher);
    }*/
    
    public static PrimeNumbers generatePrimeNumbers() {
        int bounds = 100;
        
        int p = 0;
        int q = 0;
        
        while (!(isPrime(p) && isPrime(q))) {
            p = (int) Math.floor(Math.random() * bounds) + 1;
            q = (int) Math.floor(Math.random() * bounds) + 1;
        }
        
        if(p==q) return generatePrimeNumbers();
        System.out.println(new PrimeNumbers(p, q));
        return new PrimeNumbers(p, q);
    }
    public static EulersPhi generateEulersPhi(PrimeNumbers pn){
        System.out.println(new EulersPhi((pn.p - 1) * (pn.q - 1)));
        return new EulersPhi((pn.p - 1) * (pn.q - 1));//phi(p*q) = (p - 1) * (q - 1)
    }
    public static Modulus generateModulus(PrimeNumbers pn){
        System.out.println(new Modulus(pn.p * pn.q));
        return new Modulus(pn.p * pn.q);
    }
    public static PublicKey generatePublicKey(int phi, int modulus){
        int e = (int) Math.floor(Math.random()*100)+1;
        
        if(!(e>1 && e<phi)) return generatePublicKey(phi, modulus);
        if(findGCD(e, phi)!=1) return generatePublicKey(phi, modulus);
        
        System.out.println(new PublicKey(e, modulus));
        return new PublicKey(e, modulus);
    }
    public static PrivateKey generatePrivateKey(int e, int phi){
    int i = 1; // start at 1 instead of 0
    while((i * e) % phi != 1){
        i++;
    }
    System.out.println(new PrivateKey(i));
    return new PrivateKey(i);
}
    
    public static int encrypt(PublicKey pk, int original) {
        int result = 1;
        int base = original % pk.modulus;
        int exponent = pk.e;

        while (exponent > 0) {
            if (exponent % 2 == 1) {  // if exponent is odd
                result = (result * base) % pk.modulus;
            }
            base = (base * base) % pk.modulus;
            exponent = exponent / 2;
        }

        System.out.println(result);
        return result;
    }
    public static int decrypt(PrivateKey pk,int modulus, int ciphertext){
        BigInteger cBig = BigInteger.valueOf(ciphertext);
        BigInteger nBig = BigInteger.valueOf(modulus);
        BigInteger dBig = BigInteger.valueOf(pk.d);

        BigInteger mBig = cBig.modPow(dBig, nBig); 
        System.out.println(mBig);
        return mBig.intValue();
    }
    
    public static int[] encryptMessage(int[] originalMessage){
        int[] cipherMessage = new int[originalMessage.length];
        for(int i = 0; i < originalMessage.length; i++){
            cipherMessage[i] = encrypt(publicKey, originalMessage[i]);
        }
        System.out.println("cipher: "+Arrays.toString(cipherMessage));
        return cipherMessage;
    }
    public static int[] decryptMessage(int[] cipherMessage){
        int[] originalMessage = new int[cipherMessage.length];
        for(int i = 0; i < cipherMessage.length; i++){
            originalMessage[i] = decrypt(privateKey, modulus.n, cipherMessage[i]);
        }
        System.out.println("original: "+Arrays.toString(originalMessage));
        return originalMessage;
    }
    
    public static void setKeys(){
        primeNumbers = generatePrimeNumbers();
        modulus = generateModulus(primeNumbers);
        phi = generateEulersPhi(primeNumbers);
        publicKey = generatePublicKey(phi.phi, modulus.n);
        privateKey = generatePrivateKey(publicKey.e, phi.phi);
    }
    public static int findGCD(int e, int phi){
        if (phi == 0) return e;
        return findGCD(phi, e % phi);
    }
    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0)return false;
        }
        return true;
    }

    
    public static void setupMap(){
        for (int i = 0; i < 26; i++) {
            map[i] = (char) ('a' + i);
        }
    }
}
