//Ciel Park
//ccp7gcp
//10-18-21
//hashTable.h


#ifndef HASHTABLE_H
#define HASHTABLE_H

#include <iostream>
#include <stack>
#include <vector>
#include <list>

using namespace std;

class HashTable {
 public:
  HashTable(int x);
  int getNextPrime(unsigned int n);
  bool checkprime(unsigned int p);
  unsigned int hashFunction(string word);
  void insert(string word);
  bool find(string word);

 private:
  vector <list<string>> table;
  int size;
};

#endif
