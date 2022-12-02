yacc -d parser.y
flex .\lab4.lex
gcc -std=c99 .\lex.yy.c .\parser.tab.h -o exe