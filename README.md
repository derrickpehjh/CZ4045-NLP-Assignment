# Setup

### Downloads:
1) Java 
	 - https://www.java.com/en/download/
2) Eclipse IDE 
	 - https://www.eclipse.org/downloads/
3) Source files 
	- https://github.com/derrickpehjh/CZ4045-NLP-Assignment
4) Libraries
	- https://minhaskamal.github.io/DownGit/#/home?url=https://github.com/derrickpehjh/CZ4045-NLP-Assignment/tree/master/libraries
5) Datasets
	- https://minhaskamal.github.io/DownGit/#/home?url=https://github.com/derrickpehjh/CZ4045-NLP-Assignment/tree/master/dataset

### Import:
1) Create a new workspace in eclipse and import the project folder
2) Download the Libraries and Dataset in the link given above and place them in the libraries and dataset folder respectively
3) If libraries are not recognised by the IDE, import all the libraries in the libraries folder using Build Path -> Configure Build Path -> Add External JARs and include all files in the libraries folder
4) Run the program

# Run:
### Part 1
1) Json.java (Optional)
    - This program formats the dataset into a valid json file A processed file has already been included in the dataset folder
  
2) TopProductsAndReviews.java

3) SentenceSegmentation.java

4) TokenizeAndStemming.java
    - Toggle stemming and non stemming printout at the main method
  
5) PosTagging.java

### Part 2
1) nounPhase.java
    - If you want to run noun phrase summarizer on a specific product or a specific section of the data, add to nounPhraseSummarizer() method's input
   the product id or the start and end index of the desired section accordingly
    - If you want to see the already processed data without going through the whole processing, comment out the nounPhraseSummarizer() in the main method

### Part 3
1) Sentiment.java

### Part 4
1) NegationExpression.java

# Output:
### Part 1
1) Json.java
    - Creates a json formatted file of the raw dataset
  
2) TopProductsAndReviews.java
    - 1st half of the output prints the top products. 2nd half of the output prints the top reviewers
  
3) SentenceSegmentation.java
    - Prints the top 10 values for the number of sentences to the count. Also creates a sentenceCountSample.json which includes entries raw reviews and segmented sentences
  
4) TokenizeAndStemming.java
    - 1st half of the output prints the number of tokens in sentence to number of sentences with that x tokens. 2nd half of the output prints the top 20 most frequent words
  
5) PosTagging.java
    - 1st half of the output prints 5 original sentences. 2nd half of the output prints the 5 sentences with POS Tagging included

### Part 2
1) nounPhase.java
    - 1st half of the output prints the number of reviews that it has process, the number of unique phrases as well as the top 20 noun phrase for the overall processing. 2nd half of the output prints the top 20 noun phrase for 3 chosen products

### Part 3
1) Sentiment.java
    - 1st half of the output prints the top 20 positive words. 2nd half of the output prints the top 20 negative words

### Part 4
1) NegationExpression.java
    - Prints all sentences with negation expressions in it as well as the negation terms
