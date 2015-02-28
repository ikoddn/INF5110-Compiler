package oblig1parser;

import java_cup.runtime.*;
import oblig1parser.*;

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

LineTerminator	= \r|\n|\r\n
InputCharacter	= [^\r\n]
WhiteSpace		= {LineTerminator} | [ \t\f]
LineComment		= "//" {InputCharacter}* {LineTerminator}?
Identifier		= [:jletter:] [:jletterdigit:]*
IntLiteral		= 0 | [1-9][0-9]*

%%
<YYINITIAL> {
	{WhiteSpace}                    {}
	{LineComment}					{}
	"program"                       { return symbol(sym.PROGRAM); }
	"class"                         { return symbol(sym.CLASS); }
	"proc"							{ return symbol(sym.PROC); }
	"ref"							{ return symbol(sym.REF); }
	"{"                             { return symbol(sym.LBRACK); }
	"}"                             { return symbol(sym.RBRACK); }
	"("                             { return symbol(sym.LPAR); }
	")"                             { return symbol(sym.RPAR); }
	":"								{ return symbol(sym.COLON); }
	";"                             { return symbol(sym.SEMI); }
	"."								{ return symbol(sym.DOT); }
	","								{ return symbol(sym.COMMA); }
	":="							{ return symbol(sym.ASSIGN); }
	"return"						{ return symbol(sym.RETURN); }
	"new"							{ return symbol(sym.NEW); }
	"var"							{ return symbol(sym.VAR); }
	"bool"							{ return symbol(sym.BOOL); }
	"float"							{ return symbol(sym.FLOAT); }
	"int"							{ return symbol(sym.INT); }
	"string"						{ return symbol(sym.STRING); }
	{Identifier}                    { return symbol(sym.ID, yytext()); }
	{IntLiteral}					{ return symbol(sym.INT_LITERAL, new Integer(Integer.parseInt(yytext()))); }
}

.                           		{ throw new ScannerError("Illegal character '" + yytext() + "' at line " + yyline + ", column " + yycolumn + "."); }
