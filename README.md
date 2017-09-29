Simple xml reproducer. Dublicates non-root content of input xml and substitutes values in format ${name=startValue} or 
just ${name}

Examples: 
1. <start><tag></tag></start> => <start><tag></tag><tag></tag></start> 
2. <start><tag>${var1=1} ${var2=2}</tag></start> => <start><tag>1 2</tag><tag>2 3</tag></start> 