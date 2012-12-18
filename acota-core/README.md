acota-core

```
  Copyright 2012 WESO Research Group

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```

## What is it? ##
ACOTA (Automatic Collaborative Tagging). It is a Java-based library for suggesting 
tags in a collaborative and automatic way. It is based on the use of ontologies to 
manage the tags and provide advanced services of automatic learning, reasoning, etc. 
It is supposed to be a component to implement the knowledge management with folksonomies.


## Configuration example ##
Acota configuration files only could by written in Java properties (key=value), XML 
comming soon:

```
google.url = http://google.com/complete/search?output=toolbar&q= #Google Comple Url
google.encoding = ISO-8859-1 #Google Response Encoding
google.relevance = 3 #Google Relevance
		
lucene.term.relevance = 5 #Lucene Term Relevance
lucene.label.relevance = 10 #Lucene Label Relevance
		
opennlp.es.pos = /etc/acota/config/SpanishPOS.bin #OpenNLP Spanish Pos File
opennlp.es.sent = /etc/acota/config/SpanishSent.bin #OpenNLP Sent Spanish File
```

## How to use it? ##

```
RequestSuggestionTO request = new RequestSuggestionTO();
	
ResourceTO resource = new ResourceTO();
resource.setDescription("WESO is a multidisciplinary research group from the Department of Computer Science, Spanish Philology and Philosophy at the University of Oviedo, The group is involved in semantic web research, education and technology transfer.");
resource.setLabel("About Web Semantics Oviedo");
resource.setUri("http://www.weso.es");
request.setResource(resource);

EnhancerAdapter enhacer = new LuceneEnhancer();
enhacer.setSuccessor(new OpenNLPEnhancer());
enhacer.setSuccessor(new GoogleEnhancer());

SuggestionTO suggest = enhacer.enhance(request);
```
