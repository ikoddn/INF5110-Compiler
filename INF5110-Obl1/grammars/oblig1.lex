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
IntLiteral		= [0-9]+
FloatLiteral	= {IntLiteral}.{IntLiteral}

%state STRING

%%
<YYINITIAL> {
	{WhiteSpace}                    {}
	{LineComment}					{}

	/* symbols */
	"{"                             { return symbol(sym.LBRACK); }
	"}"                             { return symbol(sym.RBRACK); }
	"("                             { return symbol(sym.LPAR); }
	")"                             { return symbol(sym.RPAR); }
	"."								{ return symbol(sym.DOT); }
	","								{ return symbol(sym.COMMA); }
	":"								{ return symbol(sym.COLON); }
	";"                             { return symbol(sym.SEMICOLON); }
	
	/* operators */
	"*"								{ return symbol(sym.ASTERISK); }
	":="							{ return symbol(sym.COLON_EQUAL); }
	"&&"							{ return symbol(sym.DOUBLE_AMP); }
	"||"							{ return symbol(sym.DOUBLE_VBAR); }
	"="								{ return symbol(sym.EQUAL); }
	">"								{ return symbol(sym.GREATER); }
	">="							{ return symbol(sym.GREATER_EQUAL); }
	"#"								{ return symbol(sym.HASH); }
	"<"								{ return symbol(sym.LESS); }
	"<="							{ return symbol(sym.LESS_EQUAL); }
	"<>"							{ return symbol(sym.LESS_GREATER); }
	"-"								{ return symbol(sym.MINUS); }
	"+"								{ return symbol(sym.PLUS); }
	"/"								{ return symbol(sym.SLASH); }

	/* keywords */
	"bool"							{ return symbol(sym.BOOL); }
	"class"                         { return symbol(sym.CLASS); }
	"do"							{ return symbol(sym.DO); }
	"else"							{ return symbol(sym.ELSE); }
	"false"							{ return symbol(sym.FALSE); }
	"float"							{ return symbol(sym.FLOAT); }
	"if"							{ return symbol(sym.IF); }
	"int"							{ return symbol(sym.INT); }
	"new"							{ return symbol(sym.NEW); }
	"not"							{ return symbol(sym.NOT); }
	"null"							{ return symbol(sym.NULL); }
	"proc"							{ return symbol(sym.PROC); }
	"program"                       { return symbol(sym.PROGRAM); }
	"ref"							{ return symbol(sym.REF); }
	"return"						{ return symbol(sym.RETURN); }
	"string"						{ return symbol(sym.STRING); }
	"then"							{ return symbol(sym.THEN); }
	"true"							{ return symbol(sym.TRUE); }
	"var"							{ return symbol(sym.VAR); }
	"while"							{ return symbol(sym.WHILE); }

	/* identifiers */
	{Identifier}                    { return symbol(sym.ID, yytext()); }
	
	/* literals */
	{IntLiteral}					{ return symbol(sym.INT_LITERAL, new Integer(Integer.parseInt(yytext()))); }
	{FloatLiteral}					{ return symbol(sym.FLOAT_LITERAL, new Float(Float.parseFloat(yytext()))); }
	\"								{ string.setLength(0); yybegin(STRING); }
}

<STRING> {
	\"								{ yybegin(YYINITIAL); return symbol(sym.STRING_LITERAL, string.toString()); }
	[^\n\r\"\\]+					{ string.append(yytext()); }
	\\t								{ string.append('\t'); }
	\\\"							{ string.append('\"'); }
	\\								{ string.append('\\'); }
}

.                           		{ throw new ScannerError("Illegal character '" + yytext() + "' at line " + yyline + ", column " + yycolumn + "."); }
