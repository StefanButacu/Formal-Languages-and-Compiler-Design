%{
#include <stdio.h>
#include <string.h>
#include "attrib.h"
#include "syntactic-analyser.tab.h"
%}



%option noyywrap
%option yylineno


int_type (int)
open_cond (\()
close_cond (\))
open_block (\{)
close_block (\})
intr_delim (;)
assign_op (=)
add_op (\+)
sub_op (\-)
mul_op (\*)
div_op (\/)
pc (public[ ]+class[ ]+Main[ ]*)
psvm (public[ ]+static[ ]+void[ ]+main[ ]*\([ ]*String\[][ ]+args[ ]*\))
scanner (Scanner[]+sc[ ]+=[ ]+new[ ]+Scanner[ ]*\([ ]*System\.in[ ]*\))
identifier [a-z]*
constant (([+-]?([1-9]?[0-9]*[lL]?)(\.[0-9]+)?)[fF]?)
white_space [ \t\r\n]+

%%
{white_space}


{pc} {
    return PC;
}

{psvm} {
    return PSVM;
}
{open_cond} {
                return OPEN_COND;
            }

{close_cond} {
                return CLOSE_COND;
            }
{open_block} {
        return OPEN_BLOCK;
}

{close_block} {
        return CLOSE_BLOCK;
}

{intr_delim} {
               return INSTR_DELIM;
            }


{add_op}    {
                return ADD;
            }

{sub_op}    {
                return SUB;
            }

{mul_op}    {
                return MUL;
            }

{div_op}    {
                return DIV;
            }
{assign_op} {
                return ASSIGN_OP;
            }

{scanner} {
    return SCANNER;

}

sc.nextInt {
    return SC_NEXT_INT;
}


{int_type} {
    return INT_TYPE;
    }


{identifier} {
    strcpy(yylval.varname, yytext);
    return ID;
}
{constant} {
    strcpy(yylval.varname, yytext);
    return CONST;
}
System.out.println {
    return SYS_OUT;
}
.        {
         yyerror("Lexical error");
        }

%%
void yyerror(char *s){
    fprintf(stderr, "error: %s:%d\n", s, yylineno);
    exit(-1);
}