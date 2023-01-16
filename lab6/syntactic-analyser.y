%{
#include <stdio.h>
#include <string.h>
#include "attrib.h"
#include "codeASM.h"

char data_segment_buffer[10000];
char code_segment_buffer[10000];
char temp[1000];
int tempnr = 1;

void newTempName(char * s){
	sprintf(s, "temp%d", tempnr);
	tempnr++;
}

%}

%union
{
	char varname[10];
	attributes pairAttrib;
}

%token PC
%token PSVM
%token OPEN_COND CLOSE_COND
%token OPEN_BLOCK CLOSE_BLOCK
%token INSTR_DELIM
%token INT_TYPE
%token ASSIGN_OP
%left ADD SUB
%left MUL DIV
%token SCANNER
%token SC_NEXT_INT
%token SYS_OUT
%token <varname> ID
%token <varname> CONST
%type <pairAttrib> term
%type <pairAttrib> expr
%%

program:PC
	OPEN_BLOCK
	PSVM
	OPEN_BLOCK
	SCANNER
	INSTR_DELIM
	instr_list
	CLOSE_BLOCK
	CLOSE_BLOCK {
				printf("GOOD FILE FORMAT\n");
			}



instr_list: instr |
	    instr instr_list

instr : declaration |
	assignment |
	write |
	read


declaration: INT_TYPE ID INSTR_DELIM {
					sprintf(temp, DECLARE_INT_ASM_FORMAT, $2);
					strcat(data_segment_buffer, temp);
					}

assignment: ID ASSIGN_OP expr INSTR_DELIM {
				char temp2[100];
				sprintf(temp2, "[%s]", $1);
				sprintf(temp, ASSIGN_ASM_FORMAT, $3.varn, temp2);
				strcat(code_segment_buffer, temp);
				}

expr: term {
		strcpy($$.code, $1.code);
		strcpy($$.varn, $1.varn);
	}
	| expr ADD expr {
		newTempName($$.varn);
		sprintf(temp, DECLARE_INT_ASM_FORMAT, $$.varn);
		strcat(data_segment_buffer, temp);
		sprintf(temp, "[%s]", $$.varn);
		strcpy($$.varn, temp);
		sprintf($$.code, "%s\n%s\n", $1.code, $3.code);
		sprintf(temp, ADD_ASM_FORMAT, $1.varn, $3.varn, $$.varn);
		strcat(code_segment_buffer, temp);

	}
	| expr SUB expr {
			 newTempName($$.varn);
			 sprintf(temp, DECLARE_INT_ASM_FORMAT, $$.varn);
			 strcat(data_segment_buffer, temp);
			 sprintf(temp, "[%s]", $$.varn);
			 strcpy($$.varn, temp);
			 sprintf($$.code, "%s\n%s\n", $1.code, $3.code);
			 sprintf(temp, SUB_ASM_FORMAT, $1.varn, $3.varn, $$.varn);
			 strcat(code_segment_buffer, temp);
		     }
	| expr MUL expr {
			 newTempName($$.varn);
			 sprintf(temp, DECLARE_INT_ASM_FORMAT, $$.varn);
			 strcat(data_segment_buffer, temp);
			 sprintf(temp, "[%s]", $$.varn);
			 strcpy($$.varn, temp);
			 sprintf($$.code, "%s\n%s\n", $1.code, $3.code);
			 sprintf(temp, MUL_ASM_FORMAT, $1.varn, $3.varn, $$.varn);
			 strcat(code_segment_buffer, temp);
		     }
	| expr DIV expr {
			 newTempName($$.varn);
			 sprintf(temp, DECLARE_INT_ASM_FORMAT, $$.varn);
			 strcat(data_segment_buffer, temp);
			 sprintf(temp, "[%s]", $$.varn);
			 strcpy($$.varn, temp);
			 sprintf($$.code, "%s\n%s\n", $1.code, $3.code);
			 sprintf(temp, DIV_ASM_FORMAT, $1.varn, $3.varn, $$.varn);
			 strcat(code_segment_buffer, temp);
		     }

term : CONST {
	strcpy($$.code, "");
	strcpy($$.varn, $1);
		}
	| ID {
		strcpy($$.code, "");
		sprintf($$.varn, "[%s]", $1);
	}

read: ID ASSIGN_OP SC_NEXT_INT OPEN_COND CLOSE_COND INSTR_DELIM {
	sprintf(temp, "[%s]", $1);
	strcpy($1, temp);
	sprintf(temp, READ_ASM_FORMAT, $1);
	strcat(code_segment_buffer, temp);
}

write: SYS_OUT OPEN_COND ID CLOSE_COND INSTR_DELIM{
		sprintf(temp, "[%s]", $3);
		strcpy($3, temp);
		sprintf(temp, PRINT_ASM_FORMAT, $3);
		strcat(code_segment_buffer, temp);
}

%%
void print_asm_code(){
	FILE *fptr;
	fptr = fopen("E:\\LFTC\\Formal-Languages-and-Compiler-Design\\lab6\\program.asm", "w");
	fprintf(fptr, PROGRAM_HEADER_ASM_FORMAT);
	fprintf(fptr, DATA_SEGMENT, data_segment_buffer);
	fprintf(fptr, CODE_SEGMENT, code_segment_buffer);
	fclose(fptr);
}

int main(int argc, char** argv){
	yyparse();
	print_asm_code();
	return 0;

}