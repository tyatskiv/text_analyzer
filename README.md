# Text Analyzer

**Usage**: java -jar TextAnalyzer.jar  
-g, --globaloffset &nbsp;&nbsp;&nbsp; Global char offset  
-in, --input <arg> &nbsp;&nbsp;&nbsp; Input file name  
-m, --matches <arg> &nbsp;&nbsp;&nbsp; CSV string of matches to analyze  
-out, --output <arg> &nbsp;&nbsp;&nbsp; Output file name, if not specified - result will be printed to console  
-s, --skipempty &nbsp;&nbsp;&nbsp; Skip the empty lines

**Example command run:**

java -jar TextAnalyzer.jar -in big.txt -out output.txt -g -m James,John,Robert,Michael,William,David,Richard,Charles,Joseph,Thomas,Christopher,Daniel,Paul,Mark,Donald,George,Kenneth,Steven,Edward,Brian,Ronald,Anthony,Kevin,Jason,Matthew,Gary,Timothy,Jose,Larry,Jeffrey,Frank,Scott,Eric,Stephen,Andrew,Raymond,Gregory,Joshua,Jerry,Dennis,Walter,Patrick,Peter,Harold,Douglas,Henry,Carl,Arthur,Ryan,Roger
