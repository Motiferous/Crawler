# Web-Crawler
## Introduction
This program goes through pages searching for links and counting keywords. I tried to make it as universal as possible, so it would be easier to repair and replace parts.
## Program parts
Now I will go through some parts of the program and explain how they work.

---
## Main
### Main -> GetConfig
It`s a pretty simple concept. The scanner goes through the text file line by line. When the program gets a line it splits the line into two parts (after "=" and before). Then those parts are put into a HashMap, where they can be accessed easily. The bonus of this concept is that you can easily add additional configurations without much trouble.
### Main -> GetInput
I want to mention this class just because of the variety of input. It accepts single links, links in array etc.
### Main -> GetResults
Pretty simple idea. Scanning links and adding them to the end of the array, and the array size is recalculated each time. Also, we are checking if the links are repeating or not.

---
## UI
I really like the concept of one program going on top of another, so that's how I chose to do my program. UI doesn't really have many operations because it uses the program Main to do almost everything. It is like a "coat" for the main program to make it more beautiful.
### Functions
The functions are pretty basic and simple. Nothing really to explain in depth.

---
## RESTAPI
RESTAPI uses the same concept as UI, just everything from files is moved to the database.

---

## How does the program work?
Now I will explain how use each program.
### Main
This is pretty simple. You have two files input.txt and config.txt. There is also an output file output.txt.
#### config.txt
I will explain what does each value do.
##### MAX_LINKS=10
Sets the amount of links to be parsed trough for one set of words.
##### MAX_DEPTH=8
Sets the maximum depth of links.
##### OUTPUT_SEPARATED_BY=,
Sets what value is output seperated by.
##### INPUT_SEPARATED_BY=,
Sets what value is input seperated by.
##### INPUT_NAME=input.txt
Sets input file name. **IMPORTANT!** .txt is **needed**.
##### OUTPUT_NAME=output.txt
Sets output file name. **IMPORTANT!** .txt is **needed**.
##### REPORT_FORM=default
Sets the results report form. You can either choose top_10 or default. top_10 is sorted in descending order.
#### input.txt
This file is in out/production/Documents. You input the words and links with a chosen separator. Every  odd line is always links and every even line is always words.
So for example input file "https://en.wikipedia.org/wiki/Nikola_Tesla, https://en.wikipedia.org/wiki/General_Conference_on_Weights_and_Measures\nNikola, America" would go through both links MAX_LINKS times (not each of the links 1000 times but both added up 1000 times) and count the appearances of words Nikola and America.
#### output.txt
You get the results either in descending order or in default order. Each line contains links searched trough and each of the words amounts counted (words numbers are in default order). At the end there is a sum of these numbers.
### UI
Usage is pretty much the same as in Main but there is an interface. You write the input like in .txt file, and you get the output like in .txt file.
Beware that when the button is pressed the program is working, but it doesn't have a loading circle.
### RESTAPI
Beware that the commands use config.txt separators. It's mandatory to set those to "," to use this program.
You can use the following commands:
#### /setlinks
```json
{
  "links":"https://en.wikipedia.org/wiki/Elon_Musk",
  "wordsAllOne":"Tesla,Musk,Gigafactory,Elon Mask,politics, America"
}
```
To get results and set everything to database.
#### /resultsdefault
Get output results sorted by default order.
#### /resultstop10
Get output results sorted by descending order.
#### /geteverything
Gets everything that is in database.
#### /deleteurl/id
Replace the id in command by the id of the link you want to remove

# CrawlerFinal
