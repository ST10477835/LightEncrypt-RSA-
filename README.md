# Encryption Library

A lightweight Java library for basic encryption and decryption using RSA-style public and private keys.  
Designed for educational purposes and small projects, this library makes it easy to generate keys, encrypt messages, and decrypt them in Java.

## Features

- Generate **PublicKey** and **PrivateKey** objects
- Encrypt integers using public keys
- Decrypt integers using private keys
- Simple API, easy to integrate
- Fully packaged as a reusable JAR for multiple projects

## Installation

1. Download `Encryption.jar` from the repository.
2. Add it to your project's **Libraries** (in NetBeans, Eclipse, or IntelliJ).
3. Import the classes:

```java
import encryption.PrivateKey;
import encryption.PublicKey;
import encryption.Encryptor;
import encryption.Decryptor;
```

## Usage Example

```java
PublicKey pk = new PublicKey(53, 1517);
int encrypted = Encryptor.encrypt(pk, 42);

PrivateKey sk = new PrivateKey(1277);
int decrypted = Decryptor.decrypt(sk, 1517, encrypted);

System.out.println("Encrypted: " + encrypted);
System.out.println("Decrypted: " + decrypted);
```

## License

MIT License. Free to use and modify.
