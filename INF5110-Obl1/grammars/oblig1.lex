package oblig1parser;
import java_cup.runtime.*;
%%

%class Lexer
%unicode
%cup
%line
%column
%public
%{
 StringBuffer string = new StringBuffer();

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }

%}
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]
Identifier = [:jletter:] [:jletterdigit:]*
%%
<YYINITIAL>{
        {WhiteSpace}                    {}
        "program"                       { return symbol(sym.PROGRAM); }
        "class"                         { return symbol(sym.CLASS); }
		"proc"							{ return symbol(sym.PROCEDURE); }
		"ref"							{ return symbol(sym.REFERENCE); }
        "{"                             { return symbol(sym.LBRACK); }
        "}"                             { return symbol(sym.RBRACK); }
        "("                             { return symbol(sym.LPAR); }
        ")"                             { return symbol(sym.RPAR); }
		":"								{ return symbol(sym.COLON); }
        ";"                             { return symbol(sym.SEMI); }
		","								{ return symbol(sym.COMMA); }
		":="							{ return symbol(sym.ASSIGN); }
		"return"						{ return symbol(sym.RETURN); }
		"var"							{ return symbol(sym.VARIABLE); }
		"bool"							{ return symbol(sym.BOOL); }
		"float"							{ return symbol(sym.FLOAT); }
		"int"							{ return symbol(sym.INT); }
		"string"						{ return symbol(sym.STRING); }
        {Identifier}                    { return symbol(sym.ID,yytext()); }
}

.                           { throw new Error("Illegal character '" + yytext() + "' at line " + yyline + ", column " + yycolumn + "."); }
