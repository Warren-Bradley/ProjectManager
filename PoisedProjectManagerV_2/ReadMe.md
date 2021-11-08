# Poised Project management

## Description:
Poise project management is designed to control and keep track of constuction projects within a conusction company. It keeps track of each project, tracking the details of the people of interest, the costs involved and the date the projects are due. Once th projects are complete, it generates an invice to provide to the customer.

## Installation:
In order to run this program on your computer, you are going to need to make sure you have SQL set up on your system. Once that is done a database called "PoisePMS" will need to be created and in that database, the following tables will need to be created.

Company
| Name   | Last_Proj_Num | Last_Person_Num|
| :-----------: | :-----------: |:-----------: |
| Posied    | 1000    | 0 |

Projects
| Proj_Num   | Name| Type |Address|ERF|Price|Paid|Deadline|Arch_ID|Contr_ID|Cust_ID|
| :----: | :----: |:----: |:----: |:-----------: |:----: |:----: |:----: |:----: |:----: |:----: |
|    |    |    |     |    |    |    |     |     |     |     |

People
| ID  | Name| Role |Phone|Email|Address|
| :----: | :----: |:----: |:----: |:-----------: |:----: |
|    |    |    |     |    |    |

## Usage:

On the first running of the program,there will be no projects in the system as the database will be empty. The program will not let you create a new project without populating the company's system with at least 1 architect and contractor as there will be no options to choose from when creating the project.

### User Inputs

When presented with a list of options of what to do, each option will have a number next to it. The only way to select the option you want is to type that number. The program will keep asking for a number till you give one of those numbers.

When asked to enter a date, the program will require you to enter it in the format of day month year where the day is two numbers, the month is the first 3 letters of the month and the year is four numbers.

eg. 25 Jul 2021
