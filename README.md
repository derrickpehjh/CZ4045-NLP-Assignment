# Setup

## Downloads:
1) Java https://www.java.com/en/download/
2) Eclipse IDE https://www.eclipse.org/downloads/

Required Libraries are included in the libraries folder. You may need to configure build path and add the libraries if there are errors pertaining to it.

## Import:
1) Create a new workspace in eclipse
2) Import the source files as a java project
3) Run the program

# Run:
## Part 1
1) Json.java (Optional). This program formats the dataset into a valid json file. A processed file has already been included in the dataset folder
2) TopProductsAndReviews.java. 
3) SentenceSegmentation.java. 
4) TokenizeAndStemming.java. Toggle stemming and non stemming printout at the main method.
5) PosTagging.java.

## Part 2
1) nounPhase.java. 

## Part 3
1) Sentiment.java. 

## Part 4
1) NegationExpression.java. 

# Output:
## Part 1
1) Json.java. Creates a json formatted file of the raw dataset.
2) TopProductsAndReviews.java. 1st half of the output prints the top products. 2nd half of the output prints the top reviewers.
3) SentenceSegmentation.java. Prints the top 10 values for the number of sentences to the count. Also creates a sentenceCountSample.json which includes entries raw reviews and segmented sentences.
4) TokenizeAndStemming.java. 1st half of the output prints the number of tokens in sentence to number of sentences with that x tokens. 2nd half of the output prints the top 20 most frequent words.
5) PosTagging.java. 1st half of the output prints 5 original sentences. 2nd half of the output prints the 5 sentences with POS Tagging included.

## Part 2
1) nounPhase.java. 1st half of the output prints the top 10 noun phrase. 2nd half of the output prints the top 10 noun phrase for 3 chosen products.

## Part 3
1) Sentiment.java. 1st half of the output prints the top 20 positive words. 2nd half of the output prints the top 20 negative words.

## Part 4
1) NegationExpression.java. Prints all sentences with negation expressions in it as well as the negation terms.
