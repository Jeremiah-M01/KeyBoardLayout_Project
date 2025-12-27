# KeyBoardLayout_Project

### Description:
 In my Algorithms and Data Structures class we developed our internal representation of how we could create a program to be able to tell the distance your finger traveled when typing words using the hunt and peck technique. Our main goal in doing so was to get the keyboard into the program in some data structure which we did in a couple of different ways, by first using a graph of all the keys we were using, where the edges were connected to the neighboring keys. We then created a hashmap to store all of the keys and their respective adjacent neighboring keys which will be useful in calculating the distances by creating the adjacency matrix. Most of the methods implemented use the adjacency matrix and the keyboard map to complie a distance matrix where the values are looked up and summed for each key that is typed (The distance between each neighboring key is assumed to be 1cm). The program also allows for different keyboard layouts to be tested like Dvorack and Colemack utilizing a permutation.

### Visualization:
The image below is the main graph representing the keyboard layout we implemented

