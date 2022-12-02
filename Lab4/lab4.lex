%{
#include <stdio.h>
#include <math.h> 			/* -> atof()  */
#include <stdlib.h>
#include "parser.tab.c"
#include <string.h>
FILE *fptrFIP;          
int isOk = 1;
char atoms_table[100][100] = {
    "",
    "ID",
    "CONST",
    "public",
    "class",
    "static",
    "void",
    "(",
    ")",
    "String",
    "[",
    "]",
    "{",
    "}",
    ";",
    "=",
    "-",
    "+",
    "*",
    "/",
    "!=",
    "%",
    "Scanner",
    "Main",
    "sc.nextInt",
    "sc.nextDouble",
    "sc.nextLine",
    "System.out.println",
    "System.in",
    "new",
    "while",
    "if",
    "else",
    "double",
    "int",
    "String[]",
    "int[]",
    "main",
    "<",
    "<=",
    ">",
    ">=",
    "!=",
    "FIELD_ACCESS",
    "then",
    "endif"
};

int getAtomCode(char* atom){
    for(int i = 0; i < 46; i++){
        if(strcmp(atoms_table[i], atom) == 0)
            return i;
    }
    return -1;
   
}

typedef struct FIP_Row {
    char* token;
    int atomCode;
    int positionInTable;
};

char idTable[100][9];
int idTableLen = 0;
char constTable[100][100];
int constTableLen = 0;


int findOrAddPositionIdTable(char *token) {
    for(int i = 0; i < idTableLen; i++){
        if(strcmp(token, idTable[i]) == 0) 
            return i;
    }
    strcpy(idTable[idTableLen], token);
    idTableLen++;
    return idTableLen-1;
}

int findOrAddPositionConstTable(char *token) {
    for(int i = 0; i < constTableLen; i++){
        if(strcmp(token, constTable[i]) == 0) 
            return i;
    }
    strcpy(constTable[constTableLen], token);
    constTableLen++;
    return constTableLen-1;
}



%}
%option noyywrap
%option yylineno

acces_modifier public|private|protected
IOInstruction System.out.println|System.out.print|sc.nextInt|sc.nextLine
keyword if|else|while|class|static|void|then|endif
entryPointMethod main
ID [a-z]*
number_dec_hexa (([+-]?([1-9]?[0-9]*[lL]?)(\.[0-9]+)?)[fF]?)|((0[xX][0-9a-fA-F]+)[lL]?)|([-+]?[0-9]*\.?[0-9]+([eE][-+]?[0-9]+)?)
number_bin 0[bB][01]+[lL]?
number_oct [+-]?(0[1-7]*[lL]?)
array ([A-Z][a-z]+\[])|int\[]
logicalOperator (==|!=|<=|>=|<|>)
attributionOperator [=]
arithmeticOperator [-+*/%]
accessingField [a-z]{1,8}\.[a-z]{1,8}
%%
new {
    printf("lex new\n");
    return NEW;
}

int {
    printf("%s\n", yytext);
    return INT;
}

double {
    printf("%s\n", yytext);
    return DOUBLE;
}

String {
    printf("%s\n", yytext);
    return STRING;
}

Scanner {
    printf("scanner = %s\n", yytext);
    return SCANNER;
}
System.in {
    printf("%s\n", yytext);
    printf(" System.in");
    return SYSTEM_IN;
}

if {
    return IF;
}

else {
    return ELSE;
}

while {
    return WHILE;
}

System.out.println {
    return SOUT;
}

{arithmeticOperator} {

    return
}

"{"|"}"|"("|")"|"["|"]"|";" {
    printf("%s\n", yytext);
    return yytext[0];
}

"public class Main" {
    printf("%s\n", yytext);
    return PUBLIC_CLASS;
}

"public static void main(String[] args)" {
    printf("%s\n", yytext);
    return PUBLIC_MAIN;
}

{attributionOperator} {
   // printf("\n");
    return '=';
}

sc.nextDouble {
    return SC_DOUBLE;
}

sc.nextInt {
    return SC_INT;
}

{number_bin}|{number_dec_hexa}|{number_oct} {
 //   printf("%s\n", yytext);
    yylval.number = atoi(yytext);
    return CONST_NUMBER;
}

{keyword}|{acces_modifier}|{IOInstruction}|{array}|{logicalOperator} {

}

"{"[^}\n]*"}" 	{
                    /* eat up one-line comments */
 }

[ \t\n]+ 	{
	/* eat up whitespace */
}

{ID} {
    printf("%s\n", yytext);
    strcpy(yylval.text, yytext);
    return ID;
}

. {
    printf("Unrecognized character: %s\n", yytext);
    exit(0);
}


%%