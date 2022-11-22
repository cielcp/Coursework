//Ciel Park
//ccp7gcp
//10-18-21
//hashTable.cpp


#include "hashTable.h"
#include <math.h>

using namespace std;

HashTable::HashTable(int x){
  size = getNextPrime(x);
  table.resize(size);
}


int HashTable::getNextPrime (unsigned int n) {
  while ( !checkprime(++n) );
  return n; // all your primes are belong to us
}

bool HashTable::checkprime(unsigned int p) {
  if ( p <= 1 ) // 0 and 1 are not primes; the are both special cases
    return false;
  if ( p == 2 ) // 2 is prime
    return true;
  if ( p % 2 == 0 ) // even numbers other than 2 are not prime
    return false;
  for ( int i = 3; i*i <= p; i += 2 ) // only go up to the sqrt of p
    if ( p % i == 0 )
      return false;
  return true;
}

unsigned int HashTable::hashFunction(string word) {
	unsigned int num = 0;
	for (int i = 0; i < word.length(); i++) {
		num += word[i] * pow(37, i);
	}
	return num % size;
}

void HashTable::insert(string word) {
  int index = hashFunction(word);
  table[index].push_back(word);
}

bool HashTable::find(string word) {
  int index = hashFunction(word);
  for (string s : table[index]) {
    if (s == word) {
      return true;
    }	
  }
  return false; 
}

