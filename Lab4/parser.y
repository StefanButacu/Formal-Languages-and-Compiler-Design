%{
#include <stdio.h>
int yylex(void);


void yyerror(char const *s){
printf("%s\n", s);
}
%}
%union{
char text[300];
double number;
}

%token ID
%token CONST_NUMBER
%token INT
%token DOUBLE
%token STRING
%token IF
%token ELSE
%token WHILE
%token NEW
%token SYSTEM_IN
%token SCANNER
%token PUBLIC_CLASS
%token PUBLIC_MAIN
%token SOUT
%token SC_INT
%token SC_DOUBLE
%token OPERATOR_NOT_EQ
%token ID_TIP_CLASA
%%

program : PUBLIC_CLASS '{' first_line '{' lista_instr '}' '}'
 	{ printf("GOOD FILE FORMAT\n"); }
;

first_line: PUBLIC_MAIN
;

lista_instr : instr
	| instr lista_instr
;

instr: instr_declarare
	| instr_io
	| instr_ciclare
	| instr_attr
	| instr_cond
;

instr_io : SOUT '(' ID ')' ';'
	   { }
;

instr_attr : ID '=' expr ';' {}
	 | ID '=' scanner_read  '(' ')' ';'  { }
;

scanner_read : SC_INT
	| SC_DOUBLE { }
;

instr_declarare : tip ID ';' { }
	| tip ID '=' CONST_NUMBER ';'
	| tip ID '=' NEW tip '(' ')' ';'
	| tip ID '=' NEW tip '(' SYSTEM_IN ')' ';'
;


tip : INT
    | DOUBLE {  }
    | STRING
    | SCANNER {}

{}
;


instr_cond : IF '(' cond ')' '{' lista_instr '}' |
	IF '(' cond ')' '{' lista_instr '}' ELSE '{' lista_instr '}'
	{}

;
cond : expr rel expr
	{}

;

expr : operand
    |  operand operatie expr
    	{}

;


operand : ID
    | ID '.' ID
    | CONST_NUMBER
        	{}

;

operatie : '+'
    | '-'
    | '*'
    | '/'
;

rel :   '<' |
	'>' |
	OPERATOR_NOT_EQ
;
instr_ciclare : WHILE '(' cond ')' '{' lista_instr '}'
;

%%

int main(){
	yyparse();
	return 0;
}