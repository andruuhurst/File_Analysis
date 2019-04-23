# File_Analysis


Will read in a text file from a disk and display information about the words in the
file:

parseFile(): that takes a reference to a File object as its lone parameter. This method will handle any related
exception and will not throw any exceptions back to the calling method.

Opens and parses this plain text file, which contains a series of words parsed
by white space as well as common punctuation marks. Neither the white
space nor the punctuation will be part of the parsed words, with the exception
of an apostrophe (').

Displays the following information about the file's contents to
the screen and to a text file in this order:
  a. The total number of words contained in the file
  
  b. The total number of distinct words in the file
  
  c. All of the words in the file, arranged in descending order by the
frequency of occurrence (all words with the same frequency are listed
alphabetically). Displays the word and its frequency on the same line.

  d. All of the words in the file, arranged in (ascending) alphabetical order
with each word followed by its frequency in the file

Output goes to the screen
